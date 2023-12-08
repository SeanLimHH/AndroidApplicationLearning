package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.Face
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.myapplication.ui.theme.MyApplicationTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {


    private val noteDatabase by lazy {
        Room.databaseBuilder(
            applicationContext,
            NoteDatabase::class.java,
            "notes.db"
        ).build()
    }

    private val noteRepository by lazy {
        NoteRepository(noteDatabase.dao)
    }

    private val viewModel by viewModels<NotesViewModel>(
        factoryProducer = {
            object: ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return NotesViewModel(noteRepository) as T
                }
            }
        }
    )

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            MyApplicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = "Notes") {

                        composable("Notes") { NotesSection(
                            viewModel.noteUIState,
                            viewModel::createNote,
                            viewModel::updateNoteDescription,
                            viewModel::deleteNote)
                        }
                        /*...*/
                    }


                    val dummyNavigationIcons = listOf(
                        NavigationItemModel(
                            "Destination1",
                            Icons.Outlined.Face,
                            { Text("Fake Navigation Destination 1") }
                        ),
                        NavigationItemModel(
                            "Destination2",
                            Icons.Outlined.Settings,
                            { Text("Fake Navigation Destination 2") }
                        )
                    )
                    val selectedItemIndex by rememberSaveable {
                        mutableStateOf(0)
                    }

                    val coroutineScope = rememberCoroutineScope()
                    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

                    ModalNavigationDrawer(
                        drawerContent = {
                            ModalDrawerSheet {
                                dummyNavigationIcons.forEachIndexed { index, item ->
                                    NavigationDrawerItem(
                                        label = item.label,
                                        selected = index == selectedItemIndex,
                                        onClick = { /*TODO*/ },
                                        icon = {
                                            Icon(
                                                imageVector = item.icon,
                                                contentDescription = null,
                                                modifier = Modifier.size(24.dp) // Adjust size as needed
                                            )
                                        }
                                    )
                                }
                            }

                        },
                        drawerState = drawerState) {

                        CenteredAlignedTopAppBarWrapper(
                            title = "Centered Text",
                            navigationIcon = {
                                IconButton(onClick = {
                                    coroutineScope.launch {
                                        drawerState.open()
                                    }
                                }) {
                                    Icon(
                                        imageVector = Icons.Filled.Menu,
                                        contentDescription = "Localized description"
                                    )
                                }
                            }
                        ) {
                            NotesSection(
                                viewModel.noteUIState,
                                viewModel::createNote,
                                viewModel::updateNoteDescription,
                                viewModel::deleteNote
                            )
                        }
                    }
                }


            }
        }
    }
}

data class NavigationItemModel(
    val route: String,
    val icon: ImageVector,
    val label: @Composable () -> Unit,
)