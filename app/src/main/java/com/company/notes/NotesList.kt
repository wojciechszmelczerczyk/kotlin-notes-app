package com.company.notes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
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
    private lateinit var tmpArrayList: ArrayList<Note>


    override fun onCreate(savedInstanceState: Bundle?)  {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notes_list)

        setSupportActionBar(findViewById(R.id.toolbar))

        noteRecyclerView = findViewById(R.id.noteList)

        noteRecyclerView.layoutManager= GridLayoutManager(this,2)

        noteRecyclerView.setHasFixedSize(true)

        noteArrayList = arrayListOf<Note>()
        tmpArrayList = arrayListOf<Note>()

        getNoteData()

    }
    private fun getNoteData(){
        dbref = Firebase.database.getReference("notes")

        dbref.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                noteArrayList.clear()
                tmpArrayList.clear()
                if(snapshot.exists()){
                    for(noteSnapshot in snapshot.children){
                        val note = noteSnapshot.getValue(Note::class.java)
                         noteArrayList.add(note!!)
                    }
                    tmpArrayList.addAll(noteArrayList)

                    noteRecyclerView.adapter = RecyclerAdapter(tmpArrayList)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater

        inflater.inflate(R.menu.list_notes_menu, menu)

        val search = menu?.findItem(R.id.search_note)

        val searchView = search?.actionView as SearchView


        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                tmpArrayList.clear()
                val searchText = p0!!
                if(searchText.isNotEmpty()){
                    noteArrayList.forEach{
                        if(it.tag.toString().contains(searchText)){
                            tmpArrayList.add(it)
                        }
                    }
                    noteRecyclerView.adapter!!.notifyDataSetChanged()
                } else {
                    tmpArrayList.clear()
                    tmpArrayList.addAll(noteArrayList)
                    noteRecyclerView.adapter!!.notifyDataSetChanged()
                }
                return false
            }

        })

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.new_note -> {
                val intent = Intent(this@NotesList, NewNote::class.java)
                startActivity(intent)
                return true

            }
        }
        return super.onOptionsItemSelected(item)
    }
}