package com.example.myapplication

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

/* NoteDAO is the lowest level above database; it is an interface.
* NoteRepository shall thus implement the interface functions
* */
class NoteRepository(private val noteDAO: NoteDAO) {

    fun readAllNotes(): Flow<List<NoteModel>> = noteDAO.readAll()

    private suspend fun refreshSortIndicesOfNotes() {
        // Retrieve all notes from the database
        val allNotes = noteDAO.readAll().first()

        // Update the sortIndex for each note
        allNotes.forEachIndexed { index, note ->
            val updatedNote = note.copy(sortIndex = index)
            noteDAO.updateOne(note, updatedNote)
        }
    }

    suspend fun createNote(description:String): Unit {

        debugLogAllNotesAtStartOfFunction("createNote")

        val currentNotesList = noteDAO.readAll().first()
        val noteToCreate = NoteModel(description = description, sortIndex = currentNotesList.size)
        noteDAO.createOne(noteToCreate)
        refreshSortIndicesOfNotes()

        debugLogAllNotesAtEndOfFunction("createNote")

    }

    suspend fun updateNote(noteID: Long, newDescription: String): Unit {
        debugLogAllNotesAtStartOfFunction("updateNote")

        noteDAO.updateOneByID(noteID, newDescription)
        refreshSortIndicesOfNotes()

        debugLogAllNotesAtEndOfFunction("updateNote")
    }

    suspend fun deleteNote(noteID: Long): Unit {
        debugLogAllNotesAtStartOfFunction("deleteNote")

        noteDAO.deleteOneByID(noteID)
        refreshSortIndicesOfNotes()

        debugLogAllNotesAtEndOfFunction("deleteNote")
    }

    suspend fun debugLogAllNotesAtStartOfFunction(functionName: String): Unit { // FOR DEBUGGING
        val beforeUpdate = noteDAO.readAll().first()
        Log.d("Note.Repository", "$functionName START: $beforeUpdate")
    }

    suspend fun debugLogAllNotesAtEndOfFunction(functionName: String): Unit { // FOR DEBUGGING
        val afterUpdate = noteDAO.readAll().first()
        Log.d("Note.Repository", "$functionName END: $afterUpdate")
    }
}