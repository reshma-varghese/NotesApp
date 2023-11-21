package com.reshma.notesapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.reshma.notesapp.db.Note
import com.reshma.notesapp.db.NoteRoomDatabase
import com.reshma.notesapp.repository.NoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class NotesViewModel(application: Application)  :AndroidViewModel(application) {

    // on below line we are creating a variable
    // for our all notes list and repository
    val allNotes : LiveData<List<Note>>
    val noteRepository : NoteRepository

    // on below line we are initializing
    // our dao, repository and all notes
    init {
        val dao = NoteRoomDatabase.getDatabase(application).getNotesDao()
        noteRepository = NoteRepository(dao)
        allNotes = noteRepository.allNotes
    }

    // on below line we are creating a new method for deleting a note. In this we are
    // calling a delete method from our repository to delete our note.
    fun deleteNote (note: Note) = viewModelScope.launch(Dispatchers.IO) {
        noteRepository.onDeleteNote(note)
    }

    // on below line we are creating a new method for updating a note. In this we are
    // calling a update method from our repository to update our note.
    fun updateNote(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        noteRepository.onUpdateNote(note)
    }

    // on below line we are creating a new method for adding a new note to our database
    // we are calling a method from our repository to add a new note.
    fun addNote(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        noteRepository.onInsertNote(note)
    }
}
