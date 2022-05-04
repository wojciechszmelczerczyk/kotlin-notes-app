package com.company.notes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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

        val uid = ""

        val titleField = findViewById<EditText>(R.id.edit_text_title)
        val contentField = findViewById<EditText>(R.id.edit_text_content)
        val tagField = findViewById<EditText>(R.id.edit_text_tag)

        val backBtn = findViewById<Button>(R.id.back_button)
        val addBtn = findViewById<Button>(R.id.add_note_button)

        // add new note
        addBtn.setOnClickListener {

            dbref = Firebase.database.getReference("notes")

            if(titleField.text.toString().isNotEmpty() && contentField.text.toString().isNotEmpty()){
                val note = Note(titleField.text.toString(), contentField.text.toString())
                dbref.child(uid!!).push().setValue(note)
                val intent = Intent(this@NewNote, NotesList::class.java)
                startActivity(intent)
            } else {
                // prompt about empty fields
                Toast.makeText(applicationContext,
                    "Note title and content cannot be empty", Toast.LENGTH_SHORT)
                    .show()

            }

        }

        // go back to notes list
        backBtn.setOnClickListener {
            val intent = Intent(this@NewNote, NotesList::class.java)
            startActivity(intent)
        }

    }
}