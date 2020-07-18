package com.aplication.organiser.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.aplication.organiser.R
import com.aplication.organiser.database.NoteDbModel

open class NoteAdapter(private var notes: List<NoteDbModel> = ArrayList()) : RecyclerView.Adapter<NoteAdapter.NoteHolder>() {

    inner class NoteHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.note_title)
        val description: TextView = itemView.findViewById(R.id.note_description)
        val priority: TextView = itemView.findViewById(R.id.note_priority)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(R.layout.note_item, parent, false)

        return NoteHolder(itemView)
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    override fun onBindViewHolder(holder: NoteHolder, position: Int) {
        val currentNote = notes[position]

        holder.title.text = currentNote.title
        holder.description.text = currentNote.description
        holder.priority.text = currentNote.priority.toString()
    }

    fun setNotes(notes: List<NoteDbModel>) {
        this.notes = notes
        notifyDataSetChanged()
    }

    fun getNoteAt(position: Int): NoteDbModel {
        return notes[position]
    }

}