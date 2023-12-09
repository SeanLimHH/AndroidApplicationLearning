package com.example.myapplication

import android.provider.ContactsContract.CommonDataKinds.Note
import android.util.Log
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.ImageVector
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

    private val _currentNotesFromDatabase: StateFlow<List<NoteModel>> =
        noteRepository.readAllNotes().stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    // Expose only a read-only version
    val currentNotesFromDatabase: StateFlow<List<NoteModel>>
        get() = _currentNotesFromDatabase


    private val _noteUIState = MutableStateFlow(NoteUIState())
    val noteUIState: StateFlow<NoteUIState> = combine(currentNotesFromDatabase, _noteUIState) { currentNotes, noteUIState ->
        noteUIState.copy(notesList = currentNotes)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), NoteUIState())

    fun createNote(description: String) {
        viewModelScope.launch {
            noteRepository.createNote(description)
        }
    }

    fun updateNoteDescription(noteID: Long, newDescription: String) {
        viewModelScope.launch {
            noteRepository.updateNote(noteID, newDescription)
        }
    }

    fun deleteNote(noteID: Long) {
        viewModelScope.launch {
            noteRepository.deleteNote(noteID)
        }
    }

}

data class NoteUIState(
    val notesList: List<NoteModel> = emptyList(),
    val label: String = "Description"
)


