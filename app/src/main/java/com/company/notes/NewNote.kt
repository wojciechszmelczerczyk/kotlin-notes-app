package com.company.notes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class NewNote : AppCompatActivity() {

    private lateinit var dbref: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_note)

        setSupportActionBar(findViewById(R.id.toolbar))

        val uid = ""

        val titleField = findViewById<EditText>(R.id.edit_text_title)
        val contentField = findViewById<EditText>(R.id.edit_text_content)
        val tagField = findViewById<EditText>(R.id.edit_text_tag)

        val addBtn = findViewById<Button>(R.id.add_note_button)

        // add new note
        addBtn.setOnClickListener {

            dbref = Firebase.database.getReference("notes")

            if(titleField.text.toString().isNotEmpty() && contentField.text.toString().isNotEmpty() && tagField.text.toString().isNotEmpty()){


                // create new note, generate id
                val key = dbref.child(uid!!).push().key

                val note = Note(id=key, titleField.text.toString(), contentField.text.toString(), tagField.text.toString())

                dbref.child(key!!).setValue(note)

                val intent = Intent(this@NewNote, NotesList::class.java)
                startActivity(intent)
                Toast.makeText(applicationContext, "New note ${note.name} created successfully", Toast.LENGTH_LONG).show()
            } else {

                if(titleField.text.toString().isEmpty()){
                    // prompt about empty fields
                    Toast.makeText(applicationContext,
                        "Note title cannot be empty", Toast.LENGTH_SHORT)
                        .show()

                } else if (contentField.text.toString().isEmpty()){
                    Toast.makeText(applicationContext,
                        "Note content cannot be empty", Toast.LENGTH_SHORT)
                        .show()

                } else if (tagField.text.toString().isEmpty()){
                    Toast.makeText(applicationContext,
                        "Note tag cannot be empty", Toast.LENGTH_SHORT)
                        .show()
                }
            }
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
                val intent = Intent(this@NewNote, NotesList::class.java)
                startActivity(intent)
                return true

            }
        }
        return super.onOptionsItemSelected(item)
    }
}