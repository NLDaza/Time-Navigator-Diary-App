package com.taller.myapplication.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.taller.myapplication.R
import com.taller.myapplication.data.Entry
import com.taller.myapplication.ui.menu.BackgroundApp
import com.taller.myapplication.ui.viewmodels.EntryViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarFilterScreen(navController: NavController, viewModel: EntryViewModel){
    Scaffold (
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        stringResource(id = R.string.entries_list),
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = MaterialTheme.colorScheme.primary),
                navigationIcon = {
                    IconButton(
                        onClick = { navController.popBackStack() }
                    ) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = stringResource(id = R.string.back), tint = Color.White)
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
                Icon(Icons.Filled.Add, contentDescription = stringResource(id = R.string.add))
            }
        }
    ){
        ContentCalendarScreen(it, navController, viewModel)
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContentCalendarScreen(it: PaddingValues, navController: NavController, viewModel: EntryViewModel){
    BackgroundApp()
    //Creamos una variable para poder usar el estado, una lista de entradas.
    val state = viewModel.state
    //Creamos variables para los filtros
    var dayFilter by rememberSaveable { mutableStateOf("") }
    var monthFilter by rememberSaveable { mutableStateOf("") }
    var yearFilter by rememberSaveable { mutableStateOf("") }
    val openDialogDeleteCalendar = remember { mutableStateOf(false) }
    var filteredEntryList = state.entryList
        .sortedWith(compareByDescending<Entry> { it.year }
                        .thenByDescending { it.month }
                        .thenByDescending { it.day })
    if(dayFilter != "") {
        filteredEntryList = filteredEntryList.filter { it.day == dayFilter.toInt()}
    }
    if(monthFilter != "") {
        filteredEntryList = filteredEntryList.filter { it.month == monthFilter.toInt()}
    }
    if(yearFilter != "") {
        filteredEntryList = filteredEntryList.filter { it.year == yearFilter.toInt()}
    }
    //Para que se filtre y sean necesarias todos los campos (opcional)
    //filteredEntryList = filteredEntryList.filter { it.day == dayFilter && it.month == monthFilter && it.year ==yearFilter}
    Column(
        modifier = Modifier
            .padding(it)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
    Row {
        TextField(
            value = dayFilter,
            onValueChange = { if (it.length <= 2){dayFilter = it }},
            label = { Text(stringResource(id = R.string.day)) },
            singleLine = true,
            placeholder = { Text(stringResource(id = R.string.day_example)) },
            modifier = Modifier.width(90.dp),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            )
        TextField(
            value = monthFilter,
            onValueChange = { if (it.length <=2){monthFilter = it }},
            label = { Text(stringResource(id = R.string.month)) },
            singleLine = true,
            placeholder = { Text(stringResource(id = R.string.month_example)) },
            modifier = Modifier.width(90.dp),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
        )
        TextField(
            value = yearFilter,
            onValueChange = { if(it.length <= 4) {yearFilter = it }},
            label = { Text(stringResource(id = R.string.year)) },
            singleLine = true,
            placeholder = { Text(stringResource(id = R.string.year_example)) },
            modifier = Modifier.width(100.dp),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
        )
    }


        LazyColumn {
            items(filteredEntryList
            ){
                Card (
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(5.dp),
                    onClick = { navController.navigate("preview/${it.idEntry}") }
                ){
                    Column (
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(10.dp)
                    ){
                        Text(
                            text = " Mood: ${it.mood}",
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                            maxLines = 1,
                        )
                        Text(//REVISAR
                            text = "Memory: ${it.memory}",
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
                                onClick = {navController.navigate("edit/${it.idEntry}")}
                            ) {
                                Icon(Icons.Filled.Edit,contentDescription =
                                "Edit")
                            }
                            if(openDialogDeleteCalendar.value) {
                                AlertDialog(
                                    onDismissRequest = {openDialogDeleteCalendar.value = false},
                                    title = {Text (text ="¿Quieres eliminar la entrada?")},
                                    text = {Text(text = "No podrás recuperarla")},
                                    confirmButton = {
                                        TextButton(
                                            onClick = {
                                                viewModel.deleteEntry(it)
                                                navController.popBackStack("calendar", inclusive = false)
                                            }) {
                                            Text(text = "Aceptar")
                                        }
                                    },
                                    dismissButton = {
                                        TextButton(
                                            onClick = {
                                                openDialogDeleteCalendar.value = false
                                            }
                                        ) {
                                            Text("Salir")
                                        }
                                    }
                                )
                            }
                            IconButton(
                                onClick = {
                                    openDialogDeleteCalendar.value= true
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