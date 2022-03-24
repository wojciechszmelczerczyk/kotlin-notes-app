package com.company.notes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class RecyclerAdapter: RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    // for now arbitrary array
    private var titles = arrayOf("Note 1", "Note 2", "Note 3", "Note 4", "Note 5", "Note 6", "Note 7", "Note 8", "Note 9", "Note 10", "Note 11", "Note 12", "Note 13", "Note 14", "Note 15", "Note 16", "Note 17", "Note 18", "Note 19", "Note 20", "Note 21", "Note 22", "Note 23", "Note 24", "Note 25", "Note 26", "Note 27")

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.note_layout, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: RecyclerAdapter.ViewHolder, position: Int) {
        // populate note layouts with note title
        holder.noteTitle.text=titles[position]
    }

    override fun getItemCount(): Int {
        return titles.size
    }

    inner class ViewHolder(noteView: View): RecyclerView.ViewHolder(noteView){
        var noteTitle: TextView

        init {
            noteTitle = noteView.findViewById(R.id.note_title)
            noteTitle.setOnClickListener{
                val position: Int = absoluteAdapterPosition
                Toast.makeText(noteView.context, "you clicked on ${titles[position]}", Toast.LENGTH_LONG).show()
            }


        }
    }
}