package com.taller.myapplication.ui.screens

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.taller.myapplication.ui.menu.Backgroundapp


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(navController: NavHostController) {
    Scaffold (
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(
                    text = "Ajustes",
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
        }
    ){
        Backgroundapp()
        ContentSettingsScreen(it)
    }
}
@Composable
fun ContentSettingsScreen(it:PaddingValues){
    val context = LocalContext.current //Obtiene el contexto actual para el borrado de la bbdd
    var showDialog by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .padding(it)
            .padding(top = 20.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        SwitchBtnDayNight()
        Button(onClick = {showDialog = true}) {
            Text("Eliminar base de datos")
        }
        if (showDialog){
            AlertDialog(
                onDismissRequest = {showDialog= false},
                confirmButton = {
                    TextButton(
                        onClick = {
                            deleteDatabase(context)
                            showDialog = false
                        }
                    ) {
                        Text("Eliminar")
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = {showDialog = false}
                    ) {
                        Text("Cancelar")
                    }
                },
                title = { Text("Confirmación") },
                text = { Text("¿Estás seguro de que quieres eliminar la base de datos? Esta acción no se puede deshacer.") }
            )
        }
    }
}
fun deleteDatabase(context: Context) {
    val databaseName = "db_diary"
    val isDeleted = context.deleteDatabase(databaseName)
    if (isDeleted) {
        Toast.makeText(context, "Base de datos eliminada con éxito", Toast.LENGTH_SHORT).show()
    } else {
        Toast.makeText(context, "No se pudo eliminar la base de datos", Toast.LENGTH_SHORT).show()
    }
    // Cierra la aplicación
    if (context is Activity) {
        context.finishAffinity() // Cierra todas las actividades
        System.exit(0) // Asegura la detención completa
    }
}


@Composable
fun SwitchBtnDayNight() {
    var isDarkMode by remember { mutableStateOf(false) }

    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Switch(
            checked = isDarkMode,
            onCheckedChange = { isDarkMode = it },
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.White,
                uncheckedThumbColor = Color.Gray,
                checkedTrackColor = Color.DarkGray,
                uncheckedTrackColor = Color.LightGray
            )
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(text = if (isDarkMode) "Modo Noche" else "Modo Día")
    }
}
@Preview
@Composable
fun PreviewMode(){
    SwitchBtnDayNight()
}