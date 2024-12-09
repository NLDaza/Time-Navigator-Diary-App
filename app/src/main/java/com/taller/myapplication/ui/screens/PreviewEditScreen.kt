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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.taller.myapplication.ui.menu.Backgroundapp
import com.taller.myapplication.ui.viewmodels.EntryViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PreviewEditEntryScreen(
    navController: NavController,
    viewModel: EntryViewModel,
    idEntry: Int
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
            it, navController, viewModel, idEntry
        )
    }
}
@Composable
fun ContentPreviewEditScreen(
    it: PaddingValues,
    navController: NavController,
    viewModel: EntryViewModel, //Aunque parezca que no se usa, es necesario para que se pasen el resto de datos.
    idEntry: Int){
    Backgroundapp()
    LaunchedEffect(idEntry) {
        viewModel.getEntryById(idEntry)
    }
    val paddingValues = it
    val selectedEntry = viewModel.selectedEntry

    //Pasamos selectedEntry y modificamos por ejemplo mood a selectedEntry.mood
    selectedEntry?.let {

        Column (modifier = Modifier
            .padding(paddingValues)
            .padding(top = 20.dp)
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
        ){
            val openDialogDelete = remember { mutableStateOf(false) }

            PreviewEditText(text = "Fecha: ${selectedEntry.day} / ${selectedEntry.month} / ${selectedEntry.year} ", modifier = Modifier)

            PreviewEditText(text = selectedEntry.mood, modifier = Modifier)


            PreviewEditText(text = selectedEntry.memory,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(380.dp)
                                .verticalScroll(rememberScrollState())
            )
            Button(onClick = {navController.navigate("edit/${idEntry}")
            }
            ) {
                Text(text = "Modificar entrada")
            }
            if(openDialogDelete.value) {
                AlertDialog(
                    onDismissRequest = {openDialogDelete.value = false},
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
                                openDialogDelete.value = false
                            }
                        ) {
                            Text("Salir")
                        }
                    }
                )
            }
            Button(
                onClick = { openDialogDelete.value= true }
            ) {
                Text(text = "Eliminar entrada")
            }
        }
    } ?: Text(text = "Entrada no encontrada")

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