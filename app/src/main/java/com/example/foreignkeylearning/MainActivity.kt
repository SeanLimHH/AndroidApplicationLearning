package com.example.foreignkeylearning

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.example.foreignkeylearning.ui.theme.ForeignKeyLearningTheme

class MainActivity : ComponentActivity() {

    private val parentAndChildDatabase by lazy {
        Room.databaseBuilder(
            applicationContext,
            ParentAndChildDatabase::class.java,
            "parentChild.db"
        ).build()
    }
    fun getViewModelFactory(): ViewModelProvider.Factory {
        return object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return combinedParentChildViewModel(parentAndChildDatabase.CombinedParentChildDAO) as T
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            this.deleteDatabase("parentChild.db")
            ForeignKeyLearningTheme {

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    val viewModelFactory = getViewModelFactory()
                    val viewModel = ViewModelProvider(this, viewModelFactory)
                        .get(combinedParentChildViewModel::class.java)
                    BasicAddParentChildComposable(
                        viewModel
                    )

                }
            }
        }
    }
}

@Composable
fun BasicAddParentChildComposable(
    viewModel: combinedParentChildViewModel,
) {

    val currentData by viewModel.allParentsAndChildren.collectAsState()


    val isListNotEmpty = currentData.isNotEmpty()

    val lastElement = if (isListNotEmpty) currentData.last() else null



    Column {

        Button(onClick = {
            viewModel.addParentAndChild()

        }) {
            Text("Add Parent and Child")
        }

        LazyColumn {
            items(currentData) { combinedModel ->
                Text("Parent: ${combinedModel.parentModel.parentColumnOne}")

                combinedModel.childModels.forEach { child ->
                    Text("Child: ${child.childColumnOne}, ParentId: ${child.parentID}")
                }
            }
        }

        Button(onClick = {
            if (lastElement != null) {
                viewModel.deleteParent(lastElement.parentModel.parentID)
            }

        }) {
            Text("Delete the following Parent and its associated Child")
        }

        if (isListNotEmpty) {
            Text(lastElement.toString())
        }



        Button(onClick = {
            if (lastElement != null) {
                viewModel.deleteChildOnly(lastElement.childModels[1].childID)
            }
        }) {
            Text("Delete the following Child Only")
        }

        if (isListNotEmpty) {
            Text(lastElement.toString())
        }

    }
}