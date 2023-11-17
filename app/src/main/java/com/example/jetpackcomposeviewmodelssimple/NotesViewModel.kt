package com.example.jetpackcomposeviewmodelssimple

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel


class NotesViewModel : ViewModel() {

    // Observe the below are grouped together
    var viewModelTextualData by mutableStateOf("Initial value. When you edit this you will change this text") // This would be a default value
        private set // Cannot set; only privately. Can only be set by this class
    fun changeTheTextualDataInViewModel() {
        viewModelTextualData = "Yes i just changed the text"
    }

}
