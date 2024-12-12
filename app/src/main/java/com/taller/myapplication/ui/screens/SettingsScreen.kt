package com.taller.myapplication.ui.screens

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.taller.myapplication.R
import com.taller.myapplication.ui.menu.BackgroundApp
import kotlin.system.exitProcess


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(navController: NavHostController) {
    Scaffold (
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(
                    stringResource(id = R.string.settings),
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
                            contentDescription = stringResource(id = R.string.back),
                            tint = Color.White)
                    }
                }
            )
        }
    ){
        BackgroundApp()
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
        Button(
            onClick = {showDialog = true},
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
        ) {
            Text(stringResource(id = R.string.delete_db))
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
                        Text(stringResource(id = R.string.delete))
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = {showDialog = false}
                    ) {
                        Text(stringResource(id = R.string.cancel))
                    }
                },
                title = { Text(stringResource(id = R.string.are_u_sure ))},
                text = { Text(stringResource(id = R.string.are_u_sure_db) )}
            )
        }
    }
}
fun deleteDatabase(context: Context) {
    val databaseName = "db_diary"
    val isDeleted = context.deleteDatabase(databaseName)
    if (isDeleted) {
        Toast.makeText(context, context.getString(R.string.db_eliminated), Toast.LENGTH_SHORT).show()
    } else {
        Toast.makeText(context, context.getString(R.string.db_not_eliminated), Toast.LENGTH_SHORT).show()
    }
    // Cierra la aplicación
    if (context is Activity) {
        context.finishAffinity() // Cierra todas las actividades
        exitProcess(0) // Asegura la detención completa
    }
}