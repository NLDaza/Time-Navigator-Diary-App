package com.taller.myapplication.ui.screens

import androidx.compose.foundation.background
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
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.taller.myapplication.data.Entry
import com.taller.myapplication.ui.menu.Backgroundapp
import com.taller.myapplication.ui.viewmodels.EntryViewModel
import java.util.Calendar
import java.util.Date
import java.util.Objects.toString
import java.util.TimeZone


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEntryScreen(
    navController: NavController,
    viewModel: EntryViewModel
){
    Scaffold (
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(
                    text = "Agregar entrada",
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )},
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
        ContentAddScreen(it, navController, viewModel)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContentAddScreen(
    it: PaddingValues,
    navController: NavController,
    viewModel: EntryViewModel
){
    Backgroundapp()
    //Creamos una variable que al abrirse, oculte el teclado virtual.
    //val keyboardController = LocalSoftwareKeyboardController.current

    //Creamos variables para almacenar y detectar cuando se estan cambiando
// los elementos que el usuario introduce

    var mood by remember { mutableStateOf("") }
    var memory by remember { mutableStateOf("") }

    val date = Date()// your date
    //https://stackoverflow.com/questions/9474121/i-want-to-get-year-month-day-etc-from-java-date-to-compare-with-gregorian-cal
    // Choose time zone in which you want to interpret your Date
    val cal: Calendar = Calendar.getInstance(TimeZone.getTimeZone("Europe/Paris"))
    cal.setTime(date)
    val year = cal[Calendar.YEAR]
    val month = cal[Calendar.MONTH]
    val day = cal[Calendar.DAY_OF_MONTH]

    //Para crear una lista desplegable REVISAR
    val dayList: MutableList<Int> = ArrayList()
        for (i in 1..31) {
            dayList.add(i)
        }
    var showDays by remember {
        mutableStateOf(false)
    }
    var selectedDay by remember {
        mutableStateOf(dayList[day-1])
    }
    val monthList: MutableList<Int> = ArrayList()
    for (i in 1..12 ){
        monthList.add(i)
    }
    var showMonth by remember {
        mutableStateOf(false)
    }
    var selectedMonth by remember {
        mutableStateOf(monthList[month])//En Java los meses empiezan en 0, al contrario que los dias que empiezan en 1
    }//https://docs.oracle.com/javase/8/docs/api/java/util/Calendar.html
    val yearList = listOf( year-4, year-3, year-2 , year-1, year, year+1, year+2, year +3, year+4 )

    var showYear by remember {
        mutableStateOf(false)
    }
    var selectedYear by remember {
        mutableStateOf(yearList[4])
    }
    Column (
        modifier = Modifier
            .padding(it)
            .padding(top = 20.dp)
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
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
                //keyboardController?.hide()
                TextField(
                    value = selectedDay.toString(),
                    onValueChange = { },
                    readOnly = true,
                    modifier = Modifier.menuAnchor(),
                    trailingIcon = {ExposedDropdownMenuDefaults.TrailingIcon(expanded = showDays)},
                    colors = ExposedDropdownMenuDefaults.textFieldColors()
                )
                ExposedDropdownMenu(
                    expanded = showDays,
                    onDismissRequest = {showDays = false}
                ) {
                    dayList.forEachIndexed { _, s ->
                        DropdownMenuItem(
                            text = { Text(text = s.toString())},
                            onClick = {
                                    selectedDay = s
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
                //keyboardController?.hide()
                TextField(
                    value = selectedMonth.toString(),
                    onValueChange = { },
                    readOnly = true,
                    modifier = Modifier.menuAnchor(),
                    trailingIcon = {ExposedDropdownMenuDefaults.TrailingIcon(expanded = showMonth)},
                    colors = ExposedDropdownMenuDefaults.textFieldColors()
                )
                ExposedDropdownMenu(
                    expanded = showMonth,
                    onDismissRequest = {showMonth = false}
                ) {
                    monthList.forEachIndexed { _, s ->
                        DropdownMenuItem(
                            text = {Text(text = s.toString())},
                            onClick = {
                                selectedMonth = s
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
                            text = {Text(text = s.toString())},
                            onClick = {
                                selectedYear = s
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
                .height(400.dp)
                .verticalScroll(rememberScrollState()),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                unfocusedBorderColor = Color.Gray,
                focusedBorderColor = Color.White,
                //focusedLabelColor = Color.White,
                unfocusedLabelColor = MaterialTheme.colorScheme.primary,
                //cursorColor = Color.Black,
            )
        )
        Button(
            onClick = {
                    val entry = Entry(
                        idEntry = 0,
                        mood = mood,
                        memory = memory,
                        day = selectedDay,
                        month = selectedMonth,
                        year = selectedYear
                    )
                    viewModel.addEntry(entry)
                    navController.popBackStack()
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .padding(bottom = 10.dp),
        ) {
            Text(text = "Añadir entrada")
        }
    }
}

@Composable
fun TextAddScreen(text: String){
    Text(text = text,
         modifier = Modifier
             .padding(horizontal = 5.dp)
             .padding(bottom = 5.dp)
             .width(100.dp)
    )
}