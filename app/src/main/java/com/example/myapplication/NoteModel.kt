package com.example.myapplication

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "NoteModel")
data class NoteModel(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0, // Default set to 0

    @ColumnInfo(name = "description")
    val description: String,

    @ColumnInfo(name = "sortIndex")
    val sortIndex: Int
)