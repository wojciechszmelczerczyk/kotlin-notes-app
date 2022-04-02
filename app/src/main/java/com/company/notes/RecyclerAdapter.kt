package com.company.notes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.util.*

class RecyclerAdapter(private val noteList: ArrayList<Note>)  : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.note_layout, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: RecyclerAdapter.ViewHolder, position: Int) {
        // populate note layouts with note title
        val currentItem = noteList[position]
        holder.noteTitle.text = currentItem.name
        holder.noteContent.text = currentItem.content
    }

    override fun getItemCount(): Int {
        return noteList.size
    }

    inner class ViewHolder(noteView: View): RecyclerView.ViewHolder(noteView){
        var noteTitle: TextView = itemView.findViewById(R.id.note_title)
        var noteContent: TextView = itemView.findViewById(R.id.note_content)
    }
}