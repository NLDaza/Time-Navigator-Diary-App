package com.taller.timenavigator.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.taller.timenavigator.R
import com.taller.timenavigator.data.Entry
import com.taller.timenavigator.ui.menu.BackgroundApp
import com.taller.timenavigator.ui.viewmodels.EntryViewModel
import java.util.Objects

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditEntryScreen(
    navController: NavController,
    viewModel: EntryViewModel,
    idEntry: Int
) {
    Scaffold (
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(
                    stringResource(id = R.string.edit_entry),
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ),
                navigationIcon = {
                    IconButton(
                        onClick = {navController.popBackStack()}
                    ){
                        Icon(
                            Icons.Filled.ArrowBack,
                            contentDescription = stringResource(id = R.string.back),
                            tint = Color.White)
                    }
                }
            )
        },

        ){
        ContentEditScreen(
            it, navController, viewModel, idEntry
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContentEditScreen(
    it: PaddingValues,
    navController: NavController,
    viewModel: EntryViewModel,
    idEntry: Int,
) {
    BackgroundApp()
    //Creamos variables para almacenar y detectar cuando se estan cambiando los elementos que el usuario introduce
    //Llamamos al viewmodel para que muestre la entrada seleccionada según su id.
    LaunchedEffect(idEntry) {
        viewModel.getEntryById(idEntry)
    }
    val paddingValues = it
    //Cambiamos el it obtenido a paddingValues, para que se diferencie del it: Entry
    val selectedEntry = viewModel.selectedEntry
    //Según la entrada seleccionada por id (que se pasa al entrar directamente desde la lista), aparecen todos los demás datos
    selectedEntry?.let {
        var mood by remember { mutableStateOf(selectedEntry.mood) }
        var memory by remember { mutableStateOf(selectedEntry.memory) }

        //Para crear una lista desplegable
        val dayList: MutableList<String> = ArrayList()

        for (i in 1..31) {
            dayList.add(i.toString())
        }
        var showDays by remember {
            mutableStateOf(false)
        }
        var selectedDay by remember {
            mutableIntStateOf(selectedEntry.day)
        }
        val monthList: MutableList<String> = ArrayList()
        for (i in 1..12 ){
            monthList.add(i.toString())
        }
        var showMonth by remember {
            mutableStateOf(false)
        }
        var selectedMonth by remember {
            mutableIntStateOf(selectedEntry.month)
        }
        val intYear = selectedEntry.year
        val yearList = listOf(Objects.toString(intYear - 4), Objects.toString(intYear - 3), Objects.toString(intYear - 2), Objects.toString(intYear - 1), Objects.toString(intYear), Objects.toString(intYear + 1), Objects.toString(intYear + 2), Objects.toString(intYear + 3), Objects.toString(intYear + 4))
        var showYear by remember {
            mutableStateOf(false)
        }
        var selectedYear by remember {
            mutableIntStateOf(selectedEntry.year)
        }

        val openDialog = remember { mutableStateOf(false) }

        Column (
            modifier = Modifier
                .padding(paddingValues)
                .padding(top = 20.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row {
                TextAddScreen(stringResource(id = R.string.day))
                TextAddScreen(stringResource(id = R.string.month))
                TextAddScreen(stringResource(id = R.string.year))
            }
            Row {
                ExposedDropdownMenuBox(
                    expanded = showDays,
                    onExpandedChange = { showDays = !showDays },
                    modifier = Modifier
                        .padding(horizontal = 5.dp)
                        .padding(bottom = 15.dp)
                        .width(100.dp)
                ) {
                    TextField(
                        value = selectedDay.toString(),
                        onValueChange = { },
                        readOnly = true,
                        modifier = Modifier.menuAnchor(),
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = showDays)},
                        colors = ExposedDropdownMenuDefaults.textFieldColors(
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            focusedIndicatorColor = Color.Green,
                            unfocusedIndicatorColor = Color.Gray
                        )
                    )
                    ExposedDropdownMenu(
                        expanded = showDays,
                        onDismissRequest = {showDays = false}
                    ) {
                        dayList.forEachIndexed { _, s ->
                            DropdownMenuItem(
                                text = { Text(text = s)},
                                onClick = {
                                    selectedDay = s.toInt()
                                    showDays = false
                                },
                                contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                            )
                        }
                    }
                }
                ExposedDropdownMenuBox(
                    expanded = showMonth,
                    onExpandedChange = { showMonth = !showMonth },
                    modifier = Modifier
                        .padding(horizontal = 5.dp)
                        .padding(bottom = 15.dp)
                        .width(100.dp)
                ) {
                    TextField(
                        value = selectedMonth.toString(),
                        onValueChange = { },
                        readOnly = true,
                        modifier = Modifier.menuAnchor(),
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = showMonth)},
                        colors = ExposedDropdownMenuDefaults.textFieldColors(
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            focusedIndicatorColor = Color.Green,
                            unfocusedIndicatorColor = Color.Gray
                        )
                    )
                    ExposedDropdownMenu(
                        expanded = showMonth,
                        onDismissRequest = {showMonth = false}
                    ) {
                        monthList.forEachIndexed { _, s ->
                            DropdownMenuItem(
                                text = {Text(text = s)},
                                onClick = {
                                    selectedMonth = s.toInt()
                                    showMonth = false
                                },
                                contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                            )
                        }
                    }
                }
                ExposedDropdownMenuBox(
                    expanded = showYear,
                    onExpandedChange = { showYear = !showYear },
                    modifier = Modifier
                        .padding(horizontal = 5.dp)
                        .padding(bottom = 15.dp)
                        .width(110.dp)
                ) {
                    TextField(
                        value = selectedYear.toString(),
                        onValueChange = { },
                        readOnly = true,
                        modifier = Modifier.menuAnchor(),
                        trailingIcon = {ExposedDropdownMenuDefaults.TrailingIcon(expanded = showYear)},
                        colors = ExposedDropdownMenuDefaults.textFieldColors(
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            focusedIndicatorColor = Color.Green,
                            unfocusedIndicatorColor = Color.Gray
                        )
                    )
                    ExposedDropdownMenu(
                        expanded = showYear,
                        onDismissRequest = {showYear = false}
                    ) {
                        yearList.forEachIndexed { _, s ->
                            DropdownMenuItem(
                                text = {Text(text = s)},
                                onClick = {
                                    selectedYear = s.toInt()
                                    showYear = false
                                },
                                contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                            )
                        }
                    }
                }
            }

            OutlinedTextField(
                value = mood,
                onValueChange = {mood = it},
                label = {Text(stringResource(id = R.string.mood))},
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .padding(bottom = 10.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    unfocusedBorderColor = MaterialTheme.colorScheme.primary,
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    focusedLabelColor = Color.White,
                    unfocusedLabelColor = MaterialTheme.colorScheme.primary,
                )
            )
            OutlinedTextField(
                value = memory,
                onValueChange = {memory = it},
                label = {Text(stringResource(id = R.string.memory))},
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .padding(bottom = 10.dp)
                    .height(400.dp)
                    .verticalScroll(rememberScrollState()),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    unfocusedBorderColor = MaterialTheme.colorScheme.primary,
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    focusedLabelColor = Color.White,
                    unfocusedLabelColor = MaterialTheme.colorScheme.primary,
                )
            )
            if(openDialog.value) {
                AlertDialog(
                    onDismissRequest = {openDialog.value = false},
                    title = {Text (stringResource(id = R.string.edit_entry_btn_ok))},
                    text = {Text(stringResource(id = R.string.changes_cant_be_undone))},
                    confirmButton = {
                        TextButton(
                            onClick = {
                                val entry = Entry(
                                    idEntry,
                                    mood,
                                    memory,
                                    selectedDay,
                                    selectedMonth,
                                    selectedYear
                                )
                                viewModel.updateEntry(entry)
                                navController.popBackStack("calendar", inclusive = false)
                            }) {
                            Text(stringResource(id = R.string.accept))
                        }
                    },
                    dismissButton = {
                        TextButton(
                            onClick = {
                                openDialog.value = false
                            }
                        ) {
                            Text(stringResource(id = R.string.cancel))
                        }
                    }
                )
            }
            Button(
                onClick = {
                    openDialog.value= true
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .padding(bottom = 10.dp)
            ) {
                Text(stringResource(id = R.string.update_entry_btn))
            }
        }
    } ?: Text(stringResource(id = R.string.entry_not_found))

}

