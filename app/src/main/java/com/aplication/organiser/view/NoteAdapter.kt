package com.aplication.organiser.view

import android.os.Parcel
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.aplication.organiser.R
import com.aplication.organiser.database.NoteDbModel

open class NoteAdapter(private val listener: OnItemClickListener) : ListAdapter<NoteDbModel, NoteAdapter.NoteHolder>(DiffCallback()) {

    inner class NoteHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.note_title)
        val description: TextView = itemView.findViewById(R.id.note_description)
        val priority: TextView = itemView.findViewById(R.id.note_priority)

        init {
            itemView.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    listener.onItemClick(getItem(adapterPosition))
                }

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(R.layout.note_item, parent, false)

        return NoteHolder(itemView)
    }

    override fun onBindViewHolder(holder: NoteHolder, position: Int) {
        val currentNote = getItem(position)

        holder.title.text = currentNote.title
        holder.description.text = currentNote.description
        holder.priority.text = currentNote.priority.toString()
    }

    fun getNoteAt(position: Int): NoteDbModel {
        return getItem(position)
    }

    interface OnItemClickListener {
        fun onItemClick(note: NoteDbModel)
    }

    class DiffCallback : DiffUtil.ItemCallback<NoteDbModel>() {
        override fun areItemsTheSame(oldItem: NoteDbModel, newItem: NoteDbModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: NoteDbModel, newItem: NoteDbModel): Boolean {
            return oldItem == newItem
        }
    }

}