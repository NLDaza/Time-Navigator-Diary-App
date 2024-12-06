package com.taller.myapplication.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.taller.myapplication.ui.viewmodels.EntryViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListScreen(navController: NavController, viewModel: EntryViewModel){
    Scaffold (
        topBar = {
            CenterAlignedTopAppBar(title = { Text(text = "Lista de entradas",
                                                  color = Color.White,
                                                  fontWeight = FontWeight.Bold)},
                                   colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                                       containerColor = MaterialTheme
                                           .colorScheme.primary
                                   ),
                                   navigationIcon = {
                                       IconButton(
                                           onClick = {navController.popBackStack()}
                                       ){
                                           Icon(
                                               Icons.Filled.ArrowBack,
                                               contentDescription = "Back",
                                               tint = Color.White)
                                       }
                                   }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate("add") },
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = Color.White
            ) {
                Icon(Icons.Filled.Add, contentDescription = "Añadir")
            }
        }
    ){
        ContentListScreen(it, navController, viewModel)
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContentListScreen(it: PaddingValues, navController: NavController,
                      viewModel: EntryViewModel){
    //Creamos una variable para poder usar el estado, una lista de entradas.
    val state = viewModel.state
    Column(
        modifier = Modifier.padding(it)
    ) {
       LazyColumn {
           items(state.entryList
                     .sortedByDescending { it.day }
                     .sortedBy { it.month }
           ){
                Card (
                    onClick = {
                        navController.navigate("preview/${it.idEntry}")
                    },
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(5.dp)
                ){
                    Column (
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(10.dp)
                    ){
                        Text(
                            text = it.mood,
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                            maxLines = 1,
                        )
                        Text(
                            text = it.memory,
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                            maxLines = 1,
                        )
                        Text(
                            text = "${it.day} / ${it.month} / ${it.year}",
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )
                        Row(
                            modifier= Modifier.fillMaxSize()
                        ){
                            IconButton(
                                onClick = {
                                    navController.navigate("preview/${it.idEntry}")
                                }
                            ) {
                                Icon(Icons.Filled.Info, contentDescription
                                = "EntryPreview")
                            }
                            IconButton(
                                onClick = {navController.navigate("edit/${it.idEntry}")
                                }
                            ) {
                                Icon(Icons.Filled.Edit,contentDescription =
                                "Edit")
                            }
                            IconButton(
                                onClick = {
                                    viewModel.deleteEntry(it)
                                }
                            ) {
                                Icon(Icons.Filled.Delete, contentDescription
                                = "Delete")
                            }
                        }
                    }
                }
           }
       }
    }
}