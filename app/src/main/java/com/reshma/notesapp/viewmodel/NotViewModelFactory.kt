package com.reshma.notesapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.reshma.notesapp.repository.NoteRepository

@Suppress("UNCHECKED_CAST")
class NotViewModelFactory
    (private val noteRepository: NoteRepository): ViewModelProvider.Factory{

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NotViewModel(noteRepository) as T
    }
}
