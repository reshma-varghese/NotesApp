package com.reshma.notesapp.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.reshma.notesapp.databinding.ItemNotesBinding
import com.reshma.notesapp.db.Note
import com.reshma.notesapp.ui.listeners.NoteListener
import com.reshma.notesapp.ui.viewholder.NotesVH

class NotesAdapter(var context: Context, var noteListener: NoteListener) :
    RecyclerView.Adapter<NotesVH>() {

    // creating a  variable for our all notes list.
    private val notesList = ArrayList<Note>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesVH {
        val itemNotesBinding: ItemNotesBinding =
            ItemNotesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NotesVH(itemNotesBinding)
    }

    override fun onBindViewHolder(holder: NotesVH, position: Int) {
        val note = notesList[position]
        holder.itemNotesBinding.note = note
        holder.itemNotesBinding.noteListener = noteListener
        holder.itemNotesBinding.executePendingBindings()
    }

    override fun getItemCount(): Int {
        return notesList.size
    }

    // below method is use to update our list of notes.
    @SuppressLint("NotifyDataSetChanged")
    fun updateList(newList: List<Note>) {
        // on below line we are clearing
        // our notes array list
        notesList.clear()
        // on below line we are adding a
        // new list to our all notes list.
        notesList.addAll(newList)
        // on below line we are calling notify data
        // change method to notify our adapter.
        notifyDataSetChanged()
    }
}
