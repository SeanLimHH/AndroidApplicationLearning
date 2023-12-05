@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)

package com.example.myapplication

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.flow.StateFlow

@Composable
fun NotesSection(
    viewModelNotes: StateFlow<List<NoteUIState>>,
    onCreateNote: (String) -> Unit,
    onUpdateNote: (Long, String) -> Unit,
    onDeleteNote: (Long) -> Unit
) {

    val programmeDescription = "Programme Description Text"

    val noteList = viewModelNotes.collectAsState()

    val focusManager = LocalFocusManager.current

    val notesSectionContext = LocalContext.current

    Column(modifier = Modifier
        .padding(all = 18.dp)
        .pointerInput(Unit) {
            detectTapGestures(onTap = {
                focusManager.clearFocus()
            })
        }
    ) {


        LazyColumn() {
            item {
                Text(
                    text = "Workout Programme",
                    style = MaterialTheme.typography.headlineLarge.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    color = Color.Black,
                    fontSize = 24.sp,
                    modifier = Modifier.padding(vertical = 16.dp) // Adjust the value as needed
                )
            }
            item {
                Text(
                    text = programmeDescription, // Convert your item to a string or customize as needed
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.Black,
                    fontSize = 18.sp,
                )
            }
            item {
                Text(
                    text = "Notes",
                    style = MaterialTheme.typography.headlineLarge.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    color = Color.Black,
                    fontSize = 24.sp,
                    modifier = Modifier.padding(vertical = 16.dp) // Adjust the value as needed
                )
            }

            items(items = noteList.value) { note ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Display your text field here
                    Log.d("noteUIStateItem", note.toString())
                    Log.d("noteUIStateItem index which is a derived state", note.sortIndex.toString()) // Conceptually, what is going on here is that you are calling the custom property
                    OutlinedTextFieldNoteDescription(onUpdateNote, onDeleteNote, notesSectionContext, note)
                }

            }

            item {
                Button(
                    onClick = {
                        // Handle button click
                        Toast.makeText(notesSectionContext, "Implement the adding of new note feature HERE!", Toast.LENGTH_SHORT).show()
                        // Conceptually, to implement this, we would want to promote unidirectional data flow. This means, here should have a callback
                        // to the eventually-implemented view model to update its data source in the database.
                        // Then the database shall thus be read from the view model to update this entire file. To populate the new Notes section here

                        // but for now, we will make do with a simple updating of the parent local StateFlow List<String>: The following is not proper! But for now it is ok

                        onCreateNote("Test description")


                    },
                    modifier = Modifier
                        .fillMaxWidth() // Make the Button span the parent width,
                        .padding(vertical = 16.dp) // Adjust the value as needed
                ) {
                    Text(text = "+ Add Note")
                }
            }

            item {
                Text(
                    text = "Details",
                    style = MaterialTheme.typography.headlineLarge.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    color = Color.Black,
                    fontSize = 24.sp
                )
            }
        }





    }

}

@Composable
fun OutlinedTextFieldNoteDescription( // This is like a wrapper for each note item.
    onUpdateNote: (Long, String) -> Unit,
    onDeleteNote: (Long) -> Unit,
    context: Context,
    note: NoteUIState,
) {
    OutlinedTextField(
        value = note.description,
        onValueChange = { //viewModel.updateInputTextFieldAtIndex(index, it)
            onUpdateNote(note.id, it)

        },
        label = {Text(note.label)},
        modifier = Modifier.fillMaxWidth(),
        trailingIcon = {
            // Add a clear icon with an onClick event
            IconButton(
                onClick = {
                    // Call the clear function with the corresponding index
                    // Toast.makeText(context, "Clear icon clicked! Pass callback to update view model to make this input text field empty", Toast.LENGTH_SHORT).show()
                    onDeleteNote(note.id)
                    //viewModel.clearInputTextFieldAtIndex(index)
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Clear,
                    contentDescription = "Clear"
                )
            }
        },


    )
}

