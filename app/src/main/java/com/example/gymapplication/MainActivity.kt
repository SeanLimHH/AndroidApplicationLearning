@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class,
    ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class,
    ExperimentalComposeUiApi::class
)

package com.example.gymapplication

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import com.example.gymapplication.ui.theme.GymApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GymApplicationTheme {
                // A surface container using the 'background' color from the theme

                val keyboardController = LocalSoftwareKeyboardController.current

                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .pointerInput(Unit) {
                            detectTapGestures(
                                onPress = { keyboardController?.hide() }
                            )
                        },
                    color = MaterialTheme.colorScheme.background
                ) {
                    noteSectionProgramme()
                }
            }
        }
    }
}


private fun getNotesDataListDummy() = mutableStateListOf<Notes>()
// Hardcoded dummy notes list for testing
val dummyNotesList = listOf<Notes>(
    Notes(noteID = 0, noteDescription = "Note 0", sortOrder = 0),
    Notes(noteID = 1, noteDescription = "Note 1", sortOrder = 1),
    Notes(noteID = 2, noteDescription = "Note 2", sortOrder = 2),
    Notes(noteID = 3, noteDescription = "Note 3", sortOrder = 3),
    Notes(noteID = 4, noteDescription = "Note 4", sortOrder = 4)
)

@Composable
fun noteSectionProgramme() {
    val notesList = remember { mutableStateListOf(*dummyNotesList.toTypedArray()) }

    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
        noteLabelProgramme(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        )

        Column {
            notesList.forEachIndexed { index, note ->
                noteProgramme(
                    note = note,
                    removeNote = { notesList.removeAt(index) },
                    updateDescription = { updatedNoteDescription ->
                        notesList[index] = notesList[index].copy(noteDescription = updatedNoteDescription)

                        // Log the new value
                        Log.d("NoteProgramme", "Note item: ${notesList[index]}")
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                )
            }
        }

        addNoteProgramme(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            val newNote = Notes(notesList.size, "Added note!", notesList.size)
            notesList.add(newNote)
        }
    }
}



@Composable
fun noteLabelProgramme(modifier: Modifier) {
    Text(
        text = "Notes",
        style = MaterialTheme.typography.headlineLarge,
        modifier = modifier
    )
}

@Composable
fun noteProgramme(
    note: Notes,
    removeNote: () -> Unit,
    updateDescription: (String) -> Unit,
    modifier: Modifier
) {
    var isFocused by rememberSaveable { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current

    OutlinedTextField(
        value = note.noteDescription,
        onValueChange = { newDescription ->
            updateDescription(newDescription)

        },
        trailingIcon = {
            if (isFocused) {
                IconButton(
                    onClick = {
                        if (!(note.noteDescription === "")) {
                            updateDescription("")
                        } else {
                            focusManager.clearFocus()
                            removeNote()
                            isFocused = false
                        }

                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Clear,
                        contentDescription = "Clear"
                    )
                }
            }
        },
        modifier = modifier
            .onFocusChanged { focusState ->
                isFocused = focusState.isFocused
                if (!focusState.isFocused) {

                }
            }
    )
}


@Composable
fun addNoteProgramme(
    modifier: Modifier,
    onAddNoteClick: () -> Unit = {}
) {
    Button(
        onClick = {
            onAddNoteClick.invoke()
        },
        modifier = modifier
    ) {
        Text("Add new note")
    }
}
