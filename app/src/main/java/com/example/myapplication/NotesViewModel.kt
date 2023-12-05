package com.example.myapplication

import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class NotesViewModel(
    private val noteRepository: NoteRepository
) : ViewModel() {

    //.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())
    private var _currentNotes =
        noteRepository.readAllNotes().stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())
    val currentNotesUI: StateFlow<List<NoteUIState>> = _currentNotes
        .map { notes ->
            notes.mapIndexed { index, note ->
                NoteUIState(
                    id = note.id, // Use the index as the id
                    label = "Description",
                    description = note.description, // Assuming your Note has a 'content' property
                    isEditing = false,
                    sortIndex = index
                )
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun createNote(description: String) {
        viewModelScope.launch {
            noteRepository.createNote(description)
        }
    }
    fun updateNote(noteID: Long, newDescription: String) {
        viewModelScope.launch {
            noteRepository.updateNote(noteID, newDescription)
        }
    }

    fun deleteNote(noteID: Long) {
        viewModelScope.launch {
            noteRepository.deleteNote(noteID)
        }
    }

//
//
//    private var currentNotesUI = MutableStateFlow(NoteUIState())
//
//    val fas =
//        combine(currentNotesUI, currentNotes) {currentNoteUI, currentNote ->
//            currentNoteUI.copy(
//                = currentNote
//            )
//        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), currentNotesUI())
//
//    private var noteIdCounter = 1
//
//
//    suspend fun updateNoteAtIndex(index: Int, newText: String) {
//        viewModelScope.launch {
//            val existingNoteToUpdate = currentNotes.value.get(index)
//            NoteModel()
//            noteDAO.update(existingNoteToUpdate)
//        }
//    }




//    fun updateInputTextFieldAtIndex(index: Int, newText: String) {
//        val updatedList = _currentNotes.value.toMutableList()
//        updatedList[index] = updatedList[index].copy(description = newText)
//        _currentNotes.value = updatedList
//    }
//
//    fun clearInputTextFieldAtIndex(index: Int) {
//        val updatedList = _dummyInputTextFieldsForNotes.value.toMutableList()
//        if (updatedList[index].description.isEmpty()) {
//            // If the description is empty, remove the item from the list
//            updatedList.removeAt(index)
//        } else {
//            // If the description is not empty, just clear the description
//            updatedList[index] = updatedList[index].copy(description = "")
//        }
//        _dummyInputTextFieldsForNotes.value = updatedList
//    }
//
//    // Function to update the list of input text fields
//    fun addNote() {
//        val newList = _dummyInputTextFieldsForNotes.value.toMutableList()
//        newList.add(
//            NoteUIState(
//                id = noteIdCounter++
//            )
//        )
//        _dummyInputTextFieldsForNotes.value = newList
//    }

}


// Temporary
data class NoteUIState(
    val id: Long = 0,
    val label: String = "Description",
    val description: String = "",
    val isEditing: Boolean = false,
    val sortIndex: Int = 0
)