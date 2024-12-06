package com.taller.myapplication.ui.screens

import android.net.Uri
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.taller.myapplication.data.Entry
import com.taller.myapplication.ui.viewmodels.EntryViewModel
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
                    text = "Editar entrada",
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
                            contentDescription = "Back",
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
    //REVISAR
    //Creamos una variable que al abrirse, oculte el teclado virtual.
    //val keyboardController = LocalSoftwareKeyboardController.current

    //Creamos variables para almacenar y detectar cuando se estan cambiando
// los elementos que el usuario introduce
    LaunchedEffect(idEntry) {
        viewModel.getEntryById(idEntry)
    }
    val paddingValues = it
    val selectedEntry = viewModel.selectedEntry
    selectedEntry?.let {
        var mood by remember { mutableStateOf(selectedEntry.mood) }
        var memory by remember { mutableStateOf(selectedEntry.memory) }

        //Para crear una lista desplegable REVISAR
        val dayList: MutableList<String> = ArrayList()

        for (i in 1..31) {
            dayList.add(i.toString())
        }
        //Para crear una lista desplegable
        var showDays by remember {
            mutableStateOf(false)
        }
        var selectedDay by remember {
            mutableStateOf(selectedEntry.day)
        }
        val monthList: MutableList<String> = ArrayList()
        for (i in 1..12 ){
            monthList.add(i.toString())
        }
        //val monthList = listOf("Seleciona un mes", "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre","Octubre", "Noviembre", "Diciembre")
        var showMonth by remember {
            mutableStateOf(false)
        }
        var selectedMonth by remember {
            mutableStateOf(selectedEntry.month)
        }
        val intYear = selectedEntry.year
        val yearList = listOf(Objects.toString(intYear - 4), Objects.toString(intYear - 3), Objects.toString(intYear - 2), Objects.toString(intYear - 1), Objects.toString(intYear), Objects.toString(intYear + 1), Objects.toString(intYear + 2), Objects.toString(intYear + 3), Objects.toString(intYear + 4))
        var showYear by remember {
            mutableStateOf(false)
        }
        var selectedYear by remember {
            mutableStateOf(selectedEntry.year)
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
                TextAddScreen(text = "Día")
                TextAddScreen(text = "Mes")
                TextAddScreen(text = "Año")
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
                    //keyboardController?.hide() REVISAR
                    TextField(
                        value = selectedDay.toString(),
                        onValueChange = { },
                        readOnly = true,
                        modifier = Modifier.menuAnchor(),
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = showDays)},
                        colors = ExposedDropdownMenuDefaults.textFieldColors()
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
                    //keyboardController?.hide() REVISAR
                    TextField(
                        value = selectedMonth.toString(),
                        onValueChange = { },
                        readOnly = true,
                        modifier = Modifier.menuAnchor(),
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = showMonth)},
                        colors = ExposedDropdownMenuDefaults.textFieldColors()
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
                    //keyboardController?.hide()
                    TextField(
                        value = selectedYear.toString(),
                        onValueChange = { },
                        readOnly = true,
                        modifier = Modifier.menuAnchor(),
                        trailingIcon = {ExposedDropdownMenuDefaults.TrailingIcon(expanded = showYear)},
                        colors = ExposedDropdownMenuDefaults.textFieldColors()
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
                label = {Text(text = "Mood")},
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType =
                                                               KeyboardType.Text),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .padding(bottom = 10.dp)
            )
            OutlinedTextField(
                value = memory,
                onValueChange = {memory = it},
                label = {Text(text = "Memory")},
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType =
                                                               KeyboardType.Text),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .padding(bottom = 10.dp)
                    .height(300.dp)
                    .verticalScroll(rememberScrollState())
            )
            //REVISAR
            if(openDialog.value) {
                AlertDialog(
                    onDismissRequest = {openDialog.value = false},
                    title = {Text (text ="¿Quieres modificar la entrada?")},
                    text = {Text(text = "Los cambios no pueden deshacerse")},
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
                            Text(text = "Aceptar")
                        }
                    },
                    dismissButton = {
                        TextButton(
                            onClick = {
                                openDialog.value = false
                            }
                        ) {
                            Text("Salir")
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
                Text(text = "Actualizar entrada")
            }
        }
    } ?: Text(text = "Entrada no encontrada")

}

