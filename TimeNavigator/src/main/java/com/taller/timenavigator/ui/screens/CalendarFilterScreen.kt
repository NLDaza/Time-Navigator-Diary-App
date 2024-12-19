package com.taller.timenavigator.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.taller.timenavigator.R
import com.taller.timenavigator.data.Entry
import com.taller.timenavigator.ui.menu.BackgroundApp
import com.taller.timenavigator.ui.viewmodels.EntryViewModel

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
    //Para que se filtre y sean necesarias todos los campos (opcional para futuros cambios)
    //filteredEntryList = filteredEntryList.filter { it.day == dayFilter && it.month == monthFilter && it.year ==yearFilter}
    val myCardColor = Color(0xFFFFF6A7)
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
        IconButton(
            onClick = {
                dayFilter = ""
                monthFilter = ""
                yearFilter = ""
            },
            modifier = Modifier
                .background(
                    color = MaterialTheme.colorScheme.inversePrimary, // Color de fondo
                    shape = CircleShape // Forma del fondo
                )
                .padding(2.dp) // Margen interno alrededor del ícono
        ) {
            Icon(
                imageVector = Icons.Filled.Delete,
                contentDescription = "Delete filter",
                tint = Color.White
            )
        }
    }


        LazyColumn {
            items(filteredEntryList
            ){
                Card (
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                        .height(100.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = myCardColor // Fondo azul
                    ),
                    shape = RoundedCornerShape(8.dp),
                    onClick = { navController.navigate("preview/${it.idEntry}") }
                ){
                    Row {
                        Column (
                            modifier = Modifier
                                .padding(8.dp)
                                .height(70.dp)
                                .width(90.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ){
                            Text(
                                text = "${it.day}",
                                fontSize = 30.sp
                            )
                            Text(
                                text = "${it.month} / ${it.year}",
                                fontSize = 16.sp
                            )
                        }
                        Column (
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(5.dp)

                        ){
                            Text(stringResource(id = R.string.mood))
                            Text(
                                text = it.mood,
                                maxLines = 1,
                            )

                            Text(stringResource(id = R.string.memory))
                            Text(
                                text = it.memory,
                                modifier = Modifier.align(Alignment.Start),
                                maxLines = 1,
                            )
                        }
                    }
                }
            }
        }
    }
}