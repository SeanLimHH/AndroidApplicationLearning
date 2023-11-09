@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class,
    ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class
)

package com.example.gymapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
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


private fun getNotesDataListDummy() = List(10) { i -> Notes(i, "Description # $i", i) }

@Composable
fun noteSectionProgramme() {
    var notesList by rememberSaveable { mutableStateOf(getNotesDataListDummy()) }

    Column(modifier = Modifier.verticalScroll(rememberScrollState())
    ){
        noteLabelProgramme(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp))
        Column(modifier = Modifier
        ) {
            notesList.forEach() {
                note -> noteProgramme(text = note.noteDescription, modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp))}
        }
        addNoteProgramme(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            val newNote = Notes(notesList.size, "Added note!", notesList.size)
            notesList = notesList + listOf(newNote)
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
fun noteProgramme(text: String, modifier: Modifier) {
    var text by rememberSaveable { mutableStateOf(text) }
    var isFocused by remember { mutableStateOf(false) }
    val isVisible by remember {
        derivedStateOf {
            text.isNotBlank() && isFocused
        }
    }

    OutlinedTextField(
        value = text,
        onValueChange = {
            text = it
        },
        trailingIcon = {
            if (isVisible) {
                IconButton(
                    onClick = { text = "" }
                ) {
                    Icon(
                        imageVector = Icons.Default.Clear,
                        contentDescription = "Clear"
                    )
                }
            }
        },
        modifier = modifier.onFocusChanged {
            if (it.isFocused) {
                isFocused = true

            } else {isFocused = false}
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
