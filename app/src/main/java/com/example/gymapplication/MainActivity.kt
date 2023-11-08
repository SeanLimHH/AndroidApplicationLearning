@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)

package com.example.gymapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEvent
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
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

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    GymApplicationTheme {
        Greeting("Android")
    }
}












private fun getNotesDataListDummy() = List(30) { i -> Notes(i, "Description # $i", i) }

@Composable
fun noteSectionProgramme(
    list: List<Notes> = remember { getNotesDataListDummy() }
    ) {

    Column(modifier = Modifier,
    ){
        noteLabelProgramme(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp))

        noteProgramme(text = "Test", modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp))
        addNoteProgramme(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp))
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
    var currentText: String = text
    val keyboardController: SoftwareKeyboardController? = LocalSoftwareKeyboardController.current

    OutlinedTextField(
        value = currentText,
        onValueChange = {
            currentText = it
        },
        shape = RoundedCornerShape(8.dp),
//        keyboardActions = KeyboardActions(
//            onDone = {keyboardController?.hide()}),
        modifier = modifier
    )
}


@Composable
fun addNoteProgramme(modifier: Modifier) {

    Button(
        onClick = {

        },
        modifier = modifier) {
        Text("Add a new note")
    }

}