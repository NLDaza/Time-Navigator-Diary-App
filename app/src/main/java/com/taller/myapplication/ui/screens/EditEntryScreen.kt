package com.taller.myapplication.ui.screens

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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditEntryScreen(
    navController: NavController,
    viewModel: EntryViewModel,
    idEntry: String,
    mood: String,
    score: String,
    memory: String,
    day: String,
    month: String
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
            it, navController, viewModel, idEntry, mood, score, memory,day, month
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContentEditScreen(
    it: PaddingValues,
    navController: NavController,
    viewModel: EntryViewModel,
    idEntry: String,
    mood: String,
    score: String,
    memory: String,
    day: String,
    month: String,
) {
    //REVISAR
    //Creamos una variable que al abrirse, oculte el teclado virtual.
    //val keyboardController = LocalSoftwareKeyboardController.current

    //Creamos variables para almacenar y detectar cuando se estan cambiando
// los elementos que el usuario introduce

    var mood by remember { mutableStateOf(mood) }
    var score by remember { mutableStateOf(score) }
    var memory by remember { mutableStateOf(memory) }

    //Para crear una lista desplegable
    val dayList = listOf(
        "Selecciona un día",
        "1", "2", "3", "4", "5", "6", "7","8","9","10",
        "11", "12", "13", "14", "15", "16", "17","18","19","20",
        "21", "22", "23", "24", "25", "26", "27","28","29","30","31")
    var showDays by remember {
        mutableStateOf(false)
    }
    var selectedDay by remember {
        mutableStateOf(day)
    }

    val monthList = listOf("Seleciona un mes", "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre","Octubre", "Noviembre", "Diciembre")
    var showMonth by remember {
        mutableStateOf(false)
    }
    var selectedMonth by remember {
        mutableStateOf(month)
    }
    //Para que la puntuación (score) no sea mayor a 10 (REVISAR), EL MAXIMO
    // SE REFIERE A CANTIDAD DE NUMEROS, por ejemplo si cambia a 10, aceptara
    // 9 numeros en vez de 2
    val maxScore = 2

    val openDialog = remember { mutableStateOf(false) }
    Column (
        modifier = Modifier
            .padding(it)
            .padding(top = 20.dp)
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
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
            value = score,
            onValueChange = {
                if ((it.length <= maxScore)){
                    score = it
                }
            },
            label = {Text(text = "Score")},
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType =
                                                           KeyboardType.Number),
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
        Row {
            ExposedDropdownMenuBox(
                expanded = showDays,
                onExpandedChange = { showDays = !showDays },
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .padding(bottom = 15.dp)
                    .width(50.dp)
            ) {
                //keyboardController?.hide() REVISAR
                TextField(
                    value = selectedDay,
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
                                if (s != dayList[0]){
                                    selectedDay = s
                                }
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
                    .padding(horizontal = 20.dp)
                    .padding(bottom = 15.dp)
                    .width(100.dp)
            ) {
                //keyboardController?.hide() REVISAR
                TextField(
                    value = selectedMonth,
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
                                if (s != monthList[0]){
                                    selectedMonth = s
                                }
                                showMonth = false
                            },
                            contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                        )
                    }
                }
            }
        }
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
                                score,
                                memory,
                                selectedDay,
                                selectedMonth,
                                ""
                            )
                            viewModel.updateEntry(entry)
                            navController.popBackStack()
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
}

