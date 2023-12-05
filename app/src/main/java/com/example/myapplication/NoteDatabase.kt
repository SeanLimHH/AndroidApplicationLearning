package com.example.myapplication

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [NoteModel::class],
    version = 1
)
abstract class NoteDatabase: RoomDatabase(){
    abstract val dao: NoteDAO
}