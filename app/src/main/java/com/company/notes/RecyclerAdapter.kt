package com.company.notes

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.util.*
import kotlin.collections.ArrayList

class RecyclerAdapter(private val noteList: ArrayList<Note>)  : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.note_layout, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: RecyclerAdapter.ViewHolder, position: Int) {
        // populate note layouts with note title
        val currentItem = noteList[position]
        holder.noteTitle.text = currentItem.name
        holder.noteContent.text = currentItem.content
        holder.noteId.text = currentItem.id
        holder.noteTag.text = "#${currentItem.tag}"

    }

    override fun getItemCount(): Int {
        return noteList.size
    }

    inner class ViewHolder(noteView: View): RecyclerView.ViewHolder(noteView) {
        var noteTitle: TextView = itemView.findViewById(R.id.note_title)
        var noteContent: TextView = itemView.findViewById(R.id.note_content)
        var noteId: TextView = itemView.findViewById(R.id.note_id)
        var noteTag: TextView = itemView.findViewById(R.id.note_tag)

        init {
            noteView.setOnClickListener {

                val intent = Intent(it.context, NoteDetails::class.java)

                // get name of clicked view
                intent.putExtra("docReference", noteId.text)

                // pass to new activity
                it.context.startActivity(intent)
            }

            noteView.setOnLongClickListener {
                Firebase.database.getReference("notes").child(noteId.text as String).removeValue()
                Toast.makeText(it.context, "Note ${noteTitle.text} deleted successfully", Toast.LENGTH_LONG).show()
                true
            }
        }

    }
    }