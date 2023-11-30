package com.example.myapplication

import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class NotesViewModel(
    // Eventually we will pass in the dao here
) : ViewModel() {

    // StateFlow for the list of input text fields
    private val _dummyInputTextFieldsForNotes = MutableStateFlow<List<NoteUIState>>(emptyList())
    val dummyInputTextFieldsForNotes: StateFlow<List<NoteUIState>> get() = _dummyInputTextFieldsForNotes

    private var noteIdCounter = 1
//
//    // Dummy data for the example
//    private val initialTextFields = listOf(
//        "ThisWouldBeNoteUIDataClassObject1",
//        "ThisWouldBeNoteUIDataClassObject2",
//        "ThisWouldBeNoteUIDataClassObject3",
//        "ThisWouldBeNoteUIDataClassObject4",
//        "ThisWouldBeNoteUIDataClassObject5",
//        "ThisWouldBeNoteUIDataClassObject6",
//        "ThisWouldBeNoteUIDataClassObject7"
//    )
//
//    // StateFlow for the list of input text fields
//    private val _dummyInputTextFieldsForNotes = MutableStateFlow(initialTextFields)
//    val dummyInputTextFieldsForNotes: StateFlow<List<String>> get() = _dummyInputTextFieldsForNotes

    // Dummy StateFlow for the example
    val dummyStateFlow = MutableStateFlow(listOf("Item 1", "Item 2", "Item 3", "Item 4", "Item 5"))


    fun updateInputTextFieldAtIndex(index: Int, newText: String) {
        val updatedList = _dummyInputTextFieldsForNotes.value.toMutableList()
        updatedList[index] = updatedList[index].copy(description = newText)
        _dummyInputTextFieldsForNotes.value = updatedList
    }

    fun clearInputTextFieldAtIndex(index: Int) {
        val updatedList = _dummyInputTextFieldsForNotes.value.toMutableList()
        if (updatedList[index].description.isEmpty()) {
            // If the description is empty, remove the item from the list
            updatedList.removeAt(index)
        } else {
            // If the description is not empty, just clear the description
            updatedList[index] = updatedList[index].copy(description = "")
        }
        _dummyInputTextFieldsForNotes.value = updatedList
    }

    // Function to update the list of input text fields
    fun addNote() {
        val newList = _dummyInputTextFieldsForNotes.value.toMutableList()
        newList.add(
            NoteUIState(
                id = noteIdCounter++
            )
        )
        _dummyInputTextFieldsForNotes.value = newList
    }

}


// Temporary
data class NoteUIState(
    val id: Int,
    val label: String = "Description",
    val description: String = "",
    val isEditing: Boolean = false
) { // This section is for derived property
    // Derived property to compute sortIndex based on the index in the list
    val sortIndex: Int
        get() = id // Or use any other logic to compute the sortIndex based on id or another property
}