package com.reshma.notesapp.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.reshma.notesapp.R
import com.reshma.notesapp.databinding.ActivityAddNoteBinding
import com.reshma.notesapp.databinding.ActivityMainBinding
import com.reshma.notesapp.db.Note
import com.reshma.notesapp.db.NoteRoomDatabase
import com.reshma.notesapp.repository.NoteRepository
import com.reshma.notesapp.ui.util.AppConstants.NOTE_DESC
import com.reshma.notesapp.ui.util.AppConstants.NOTE_ID
import com.reshma.notesapp.ui.util.AppConstants.NOTE_TITLE
import com.reshma.notesapp.ui.util.AppConstants.NOTE_TYPE
import com.reshma.notesapp.viewmodel.NotViewModel
import com.reshma.notesapp.viewmodel.NotViewModelFactory
import com.reshma.notesapp.viewmodel.NotesViewModel
import com.reshma.notesapp.viewmodel.NotesViewModelFactory
import java.text.SimpleDateFormat
import java.util.*

class AddNoteActivity : AppCompatActivity() {

    private var activityAddNoteBinding: ActivityAddNoteBinding? =null
    lateinit var noteViewModel: NotesViewModel
    lateinit var notViewModel: NotViewModel

    var noteID = -1;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityAddNoteBinding = DataBindingUtil.setContentView(this, R.layout.activity_add_note)

        // viewmodel initalization method 1
        val database by lazy { NoteRoomDatabase.getDatabase(application) }
        val noteRepository by lazy { NoteRepository(database.getNotesDao()) }
//        val notesDao = NoteRoomDatabase.getDatabase(application).getNotesDao()
//        val noteRepository = NoteRepository(notesDao)
        val notViewModelFactory = NotViewModelFactory(noteRepository)
        notViewModel = ViewModelProvider(this,notViewModelFactory).get(NotViewModel::class.java)

//        // method 2
//        noteViewModel = ViewModelProvider(
//            this,
//            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
//        ).get(NotesViewModel::class.java)

        // method 3
//        val notesViewModelFactory = NotesViewModelFactory(application)
//        noteViewModel = ViewModelProvider(this,notesViewModelFactory).get(NotesViewModel::class.java)


        // on below line we are getting data passed via an intent.
        val noteType = intent.getStringExtra(NOTE_TYPE)
        if (noteType.equals("Edit")) {
            // on below line we are setting data to edit text.
            val noteTitle = intent.getStringExtra(NOTE_TITLE)
            val noteDescription = intent.getStringExtra(NOTE_DESC)
            noteID = intent.getIntExtra(NOTE_ID, -1)
            activityAddNoteBinding!!.btnAdd.setText("Update Note")
            activityAddNoteBinding!!.etTitle.setText(noteTitle)
            activityAddNoteBinding!!.etDescription.setText(noteDescription)
        } else {
            activityAddNoteBinding!!.btnAdd.setText("Save Note")
        }


        // click listener to our save button.
        activityAddNoteBinding!!.btnAdd.setOnClickListener {
            // title and desc from edit text.
            val noteTitle = activityAddNoteBinding!!.etTitle.text.toString()
            val noteDescription = activityAddNoteBinding!!.etDescription.text.toString()
            // on below line we are checking the type
            // and then saving or updating the data.
            if (noteType.equals("Edit")) {
                if (noteTitle.isNotEmpty() && noteDescription.isNotEmpty()) {
                    val sdf = SimpleDateFormat("dd MMM, yyyy - HH:mm")
                    val currentDateAndTime: String = sdf.format(Date())
                    var updatedNote = Note(noteTitle, noteDescription, currentDateAndTime)
                    updatedNote.id = noteID
                    notViewModel.updateNote(updatedNote)
                    Toast.makeText(this, "Note Updated..", Toast.LENGTH_LONG).show()
                }
            } else {
                if (noteTitle.isNotEmpty() && noteDescription.isNotEmpty()) {
                    val sdf = SimpleDateFormat("dd MMM, yyyy - HH:mm")
                    val currentDateAndTime: String = sdf.format(Date())
                    // if the string is not empty we are calling a
                    // add note method to add data to our room database.
                    notViewModel.addNote(Note(noteTitle, noteDescription, currentDateAndTime))
                    Toast.makeText(this, "$noteTitle Added", Toast.LENGTH_LONG).show()
                }
            }
            // opening the new activity on below line
            startActivity(Intent(applicationContext, MainActivity::class.java))
            this.finish()
        }

    }
}
