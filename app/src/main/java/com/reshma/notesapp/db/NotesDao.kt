package com.reshma.notesapp.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.IGNORE
import androidx.room.Query
import androidx.room.Update

@Dao
interface NotesDao {

    @Insert(onConflict = IGNORE)
    suspend fun onInsertNote(note: Note)

    @Delete
    suspend fun onDeleteNote(note: Note)

    @Update
    suspend fun onUpdateNote(note: Note)

    @Query("SELECT * FROM notes_table ORDER BY id ASC")
    fun onGetAllNotes(): LiveData<List<Note>>
}
