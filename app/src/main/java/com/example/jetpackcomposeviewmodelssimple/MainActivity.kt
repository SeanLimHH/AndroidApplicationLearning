@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.jetpackcomposeviewmodelssimple

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.jetpackcomposeviewmodelssimple.ui.theme.JetpackComposeViewModelsSimpleTheme

class MainActivity : ComponentActivity() {

    private val notesViewModel = NotesViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetpackComposeViewModelsSimpleTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    OutlinedTextField(value = notesViewModel.viewModelTextualData, onValueChange = {notesViewModel.changeTheTextualDataInViewModel()})

                }
            }
        }
    }
}
