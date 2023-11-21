package com.reshma.notesapp.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.reshma.notesapp.R
import com.reshma.notesapp.databinding.ActivityMainBinding
import com.reshma.notesapp.db.Note
import com.reshma.notesapp.db.NoteRoomDatabase
import com.reshma.notesapp.repository.NoteRepository
import com.reshma.notesapp.ui.adapter.NotesAdapter
import com.reshma.notesapp.ui.listeners.NoteListener
import com.reshma.notesapp.ui.util.AppConstants
import com.reshma.notesapp.ui.util.AppConstants.NOTE_DESC
import com.reshma.notesapp.ui.util.AppConstants.NOTE_ID
import com.reshma.notesapp.ui.util.AppConstants.NOTE_TITLE
import com.reshma.notesapp.ui.util.AppConstants.NOTE_TYPE
import com.reshma.notesapp.viewmodel.NotViewModel
import com.reshma.notesapp.viewmodel.NotViewModelFactory
import com.reshma.notesapp.viewmodel.NotesViewModel
import com.reshma.notesapp.viewmodel.NotesViewModelFactory

class MainActivity : AppCompatActivity(), NoteListener {
    private var activityMainBinding: ActivityMainBinding? = null
    private var notesAdapter: NotesAdapter? = null
    lateinit var noteViewModel: NotesViewModel
    lateinit var notViewModel: NotViewModel



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)


        // viewmodel initalization method 1
        val database by lazy { NoteRoomDatabase.getDatabase(application) }
        val noteRepository by lazy { NoteRepository(database.getNotesDao()) }
        // viewmodel initalization method 1
//        val notesDao = NoteRoomDatabase.getDatabase(application).getNotesDao()
//        val noteRepository = NoteRepository(notesDao)
        val notViewModelFactory = NotViewModelFactory(noteRepository)
         notViewModel = ViewModelProvider(this,notViewModelFactory).get(NotViewModel::class.java)


        // viewmodel initalization method 2
//        noteViewModel = ViewModelProvider(
//            this,
//            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
//        ).get(NotesViewModel::class.java)

        // // viewmodel initalization method 3
//        val notesViewModelFactory = NotesViewModelFactory(application)
//        noteViewModel = ViewModelProvider(this,notesViewModelFactory).get(NotesViewModel::class.java)

        onSetRecyclerView()

        onAddNewNote()
    }


    private fun onAddNewNote() {
        activityMainBinding!!.fabAddNote.setOnClickListener {
            val intent = Intent(this@MainActivity, AddNoteActivity::class.java)
            startActivity(intent)
            this.finish()

        }
    }

    private fun onSetRecyclerView() {
        activityMainBinding!!.rvNotes.layoutManager = LinearLayoutManager(this)
        notesAdapter = NotesAdapter(this, this)
        activityMainBinding!!.rvNotes.adapter = notesAdapter


        // on below line we are calling all notes method
        // from our view model class to observer the changes on list.
        notViewModel.allNotes.observe(this, Observer { list ->
            list?.let {
                // on below line we are updating our list.
                notesAdapter!!.updateList(it)
            }
        })
    }

    override fun onViewNote(note: Note) {

        // opening a new intent and passing a data to it.
        val intent = Intent(this@MainActivity, AddNoteActivity::class.java)
        intent.putExtra(NOTE_TYPE, "Edit")
        intent.putExtra(NOTE_TITLE, note.title)
        intent.putExtra(NOTE_DESC, note.description)
        intent.putExtra(NOTE_ID, note.id)
        startActivity(intent)
        this.finish()
    }

    override fun onDeleteNote(note: Note) {
        notViewModel.deleteNote(note)
        // displaying a toast message
        Toast.makeText(this, "${note.title} Deleted", Toast.LENGTH_LONG).show()
    }
}
