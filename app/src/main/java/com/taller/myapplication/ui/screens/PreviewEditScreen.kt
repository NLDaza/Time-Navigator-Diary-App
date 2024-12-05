package com.taller.myapplication.ui.screens

import android.net.Uri
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
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
fun PreviewEditEntryScreen(
    navController: NavController,
    viewModel: EntryViewModel,
    idEntry: Int,
    mood: String,
    score: String,
    memory: String,
    day: String,
    month: String,
    year: String
) {
    Scaffold (
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(
                    text = "Time Navigator",
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
        ContentPreviewEditScreen(
            it, navController, viewModel, idEntry, mood, score, memory,day, month, year
        )
    }
}
@Composable
fun ContentPreviewEditScreen(
    it: PaddingValues,
    navController: NavController,
    viewModel: EntryViewModel, //Aunque parezca que no se usa, es necesario para que se pasen el resto de datos.
    idEntry: Int,
    mood: String,
    score: String,
    memory: String,
    day: String,
    month: String,
    year: String){
    val decodedMemory = Uri.decode(memory) // Decodifica "memory"

    Column (
        modifier = Modifier
            .padding(it)
            .padding(top = 20.dp)
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        PreviewEditText(text = "Fecha: ${day} / ${month} / ${year} ", modifier = Modifier)
        PreviewEditText(text = mood, modifier = Modifier)
        PreviewEditText(text = "Puntuación: ${score}", modifier = Modifier)

        PreviewEditText(text = decodedMemory,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(380.dp)
                            .verticalScroll(rememberScrollState())
        )
        Button(onClick = {navController.navigate("edit/${idEntry}/${mood}/${score}/${memory}/${day}/${month}/${year}")
        }
        ) {
            Text(text = "Modificar entrada")
        }
    }
}
@Composable
fun PreviewEditText(text: String, modifier: Modifier){
    Text(text = text,
         modifier = modifier
             .padding(3.dp)
             .border(
                 width = 1.dp,            // Grosor del borde
                 color = Color.Black,      // Color del borde
                 shape = RoundedCornerShape(8.dp) // Esquinas redondeadas (opcional)
             )
             .padding(8.dp) // Espaciado interno entre el texto y el borde

    )
}