package com.reshma.notesapp.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes_table")
class Note(
    @ColumnInfo(name = "title") val title:String,
    @ColumnInfo(name = "description") val description:String,
    @ColumnInfo(name = "time_stamp") val timeStamp:String
) {
    @PrimaryKey(autoGenerate = true) var id = 0
}
