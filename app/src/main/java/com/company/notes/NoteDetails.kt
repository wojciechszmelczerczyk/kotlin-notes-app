package com.company.notes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class NoteDetails : AppCompatActivity() {
    private lateinit var dbref: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_details)

        val mToolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar)

        setSupportActionBar(mToolbar)

        val titleField = findViewById<EditText>(R.id.edit_text_title_d)
        val contentField = findViewById<EditText>(R.id.edit_text_content_d)
        val tagField = findViewById<EditText>(R.id.edit_text_tag_d)

        val editBtn = findViewById<Button>(R.id.edit_note_button)

        val bundle: Bundle? = intent.extras
        val id = bundle?.getString("docReference") ?: ""


        // find doc by id
        dbref = Firebase.database.getReference("notes").child(id)

        dbref.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for(noteSnapshot in snapshot.children){
                    // if prop title then
                    if(noteSnapshot.key == "name"){
                        titleField.setText("${noteSnapshot.value}")
                    }
                    // if prop content then
                    else if (noteSnapshot.key == "content"){
                        contentField.setText("${noteSnapshot.value}")
                    }

                    else if(noteSnapshot.key == "tag"){
                        tagField.setText("${noteSnapshot.value}")
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

        // update note details
        editBtn.setOnClickListener{
            val note = Note(id=id, titleField.text.toString(), contentField.text.toString(), tagField.text.toString())

            Firebase.database.getReference("notes").child(id).setValue(note)

            val intent = Intent(this@NoteDetails, NotesList::class.java)

            startActivity(intent)

            Toast.makeText(applicationContext, "Note updated successfully", Toast.LENGTH_LONG).show()
        }
    }



    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.modify_note_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.back -> {
                val intent = Intent(this@NoteDetails, NotesList::class.java)
                startActivity(intent)
                return true

            }
        }
        return super.onOptionsItemSelected(item)
    }
}