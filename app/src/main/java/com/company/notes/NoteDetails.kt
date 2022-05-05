package com.company.notes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
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

        val titleField = findViewById<EditText>(R.id.edit_text_title_d)
        val contentField = findViewById<EditText>(R.id.edit_text_content_d)
        val tagField = findViewById<EditText>(R.id.edit_text_tag_d)

        val bundle: Bundle? = intent.extras
        val id = bundle?.getString("docReference") ?: ""

        Log.v("WHATWHATHWHAT", "$id")

        // find doc by id
        dbref = Firebase.database.getReference("notes").child(id)

        dbref.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for(noteSnapshot in snapshot.children){
                    Log.v("PROPERTIES", "${noteSnapshot.key}")
                    // if prop title then
                    if(noteSnapshot.key == "name"){
                        titleField.setText("${noteSnapshot.value}")
                    }
                    // if prop content then
                    else if (noteSnapshot.key == "content"){
                        contentField.setText("${noteSnapshot.value}")
                    }
                    // if prop tag then
//                    tagField.setText("${noteSnapshot.value}")
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }


        })



    }
}