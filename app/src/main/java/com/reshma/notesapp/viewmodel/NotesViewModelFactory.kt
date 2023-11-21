package com.reshma.notesapp.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.reshma.notesapp.repository.NoteRepository

//class NotesViewModelFactory(private val noteRepository: NoteRepository) : ViewModelProvider.Factory {
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        if (modelClass.isAssignableFrom(NotesViewModel::class.java)) {
//            @Suppress("UNCHECKED_CAST")
//            return NotesViewModel(noteRepository) as T
//        }
//        throw IllegalArgumentException("Unknown ViewModel class")
//    }
//}

@Suppress("UNCHECKED_CAST")
class NotesViewModelFactory
    (private val application: Application): ViewModelProvider.Factory{

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NotesViewModel(application) as T
    }
}
