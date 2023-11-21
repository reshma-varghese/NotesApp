package com.reshma.notesapp.ui.listeners

import com.reshma.notesapp.db.Note

interface NoteListener {

    fun onViewNote(note: Note)
    fun onDeleteNote(note: Note)
}
