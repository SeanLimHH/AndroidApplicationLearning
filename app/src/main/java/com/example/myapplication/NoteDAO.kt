package com.example.myapplication

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

/* NoteDAO is the lowest level above database; it is an interface.
* If this interface is implemented properly, it should not change as long as model does not change
* */
@Dao
interface NoteDAO {
    @Query("SELECT * FROM NoteModel")
    fun readAll(): Flow<List<NoteModel>>

    @Query("SELECT * FROM NoteModel WHERE id = :noteID")
    suspend fun readOneByID(noteID: Long): NoteModel?

    @Query("UPDATE NoteModel SET description = :newDescription WHERE id = :noteID")
    suspend fun updateOneByID(noteID: Long, newDescription: String): Unit

    @Query("DELETE FROM NoteModel WHERE id = :noteID")
    suspend fun deleteOneByID(noteID: Long): Unit

    @Insert
    suspend fun createOne(note: NoteModel): Unit

    @Update
    suspend fun updateOne(oldNote: NoteModel, newNote: NoteModel): Unit

    @Delete
    suspend fun deleteOne(note: NoteModel): Unit
}