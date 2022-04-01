package com.company.notes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class NotesList : AppCompatActivity() {
    private lateinit var dbref: DatabaseReference
    private lateinit var noteRecyclerView: RecyclerView
    private lateinit var noteArrayList: ArrayList<Note>

//    private var layoutManager: RecyclerView.LayoutManager? = null
//    private var adapter: RecyclerView.Adapter<RecyclerAdapter.ViewHolder>? = null

    override fun onCreate(savedInstanceState: Bundle?)  {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notes_list)

        noteRecyclerView = findViewById(R.id.noteList)

        noteRecyclerView.layoutManager= GridLayoutManager(this,2)

        noteRecyclerView.setHasFixedSize(true)

        noteArrayList = arrayListOf<Note>()
        getNoteData()


    }
    private fun getNoteData(){
        dbref = Firebase.database.getReference("notes")

        dbref.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for(noteSnapshot in snapshot.children){
                        val note = noteSnapshot.getValue(Note::class.java)
                        noteArrayList.add(note!!)
                    }
                    noteRecyclerView.adapter = RecyclerAdapter(noteArrayList)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}