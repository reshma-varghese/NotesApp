package com.reshma.notesapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.reshma.notesapp.db.Note
import com.reshma.notesapp.repository.NoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NotViewModel(private val noteRepository: NoteRepository):ViewModel() {

    val allNotes : LiveData<List<Note>> = noteRepository.allNotes

    fun deleteNote (note: Note) = viewModelScope.launch(Dispatchers.IO) {
        noteRepository.onDeleteNote(note)
    }


    fun updateNote(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        noteRepository.onUpdateNote(note)
    }

    fun addNote(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        noteRepository.onInsertNote(note)
    }
}
