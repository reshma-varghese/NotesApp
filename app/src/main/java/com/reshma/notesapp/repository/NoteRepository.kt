package com.reshma.notesapp.repository

import androidx.lifecycle.LiveData
import com.reshma.notesapp.db.Note
import com.reshma.notesapp.db.NotesDao

class NoteRepository(private val notesDao: NotesDao) {

    // to get all the notes from our DAO class.
    val allNotes: LiveData<List<Note>> = notesDao.onGetAllNotes()

    // for adding the note to our database.
    suspend fun onInsertNote(note: Note) {
        notesDao.onInsertNote(note)
    }

    // for deleting our note from database.
    suspend fun onDeleteNote(note: Note){
        notesDao.onDeleteNote(note)
    }

    // updating our note from database.
    suspend fun onUpdateNote(note: Note){
        notesDao.onUpdateNote(note)
    }
}
