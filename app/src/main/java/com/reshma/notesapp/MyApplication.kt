package com.reshma.notesapp

import android.app.Application
import com.reshma.notesapp.db.NoteRoomDatabase
import com.reshma.notesapp.repository.NoteRepository

class MyApplication : Application() {

    // Using by lazy so the database and the repository are only created when they're needed
    // rather than when the application starts
    val database by lazy { NoteRoomDatabase.getDatabase(this) }
    val noteRepository by lazy { NoteRepository(database.getNotesDao()) }
}
