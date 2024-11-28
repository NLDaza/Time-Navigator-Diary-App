package com.taller.myapplication.ui.menu

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.taller.myapplication.R
import com.taller.myapplication.ui.AlertDialogGeneral
import com.taller.myapplication.ui.CustomBtnMenu


@Composable //Revisar los contentdescription, menu mejorado
fun MenuScreen(navController: NavController){
    Column (
        modifier = Modifier.fillMaxSize(), //Para que ocupe lo máximo posible
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,//Para que se alineen
    ){
        Text(
            text = stringResource(id = R.string.main_screen),
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.SansSerif,
            style = MaterialTheme.typography.titleLarge
        )
        //Agregamos este botón para añadir una nueva entrada al diario
        Spacer(modifier = Modifier.height(15.dp))
        CustomBtnMenu(btnText = { Text(stringResource(id = R.string.add_entrie))},
                      btnIcon = { Icon(Icons.Filled.Add, contentDescription = null)},
                      containerColor = MaterialTheme.colorScheme.primary,
                      onClick = {navController.navigate("add")}
        )
        Spacer(modifier = Modifier.height(15.dp))
        CustomBtnMenu(btnText = { Text(stringResource(id = R.string
            .entries_list))},
                      btnIcon = { Icon(Icons.Filled.List, contentDescription = null)},
                      containerColor = MaterialTheme.colorScheme.primaryContainer,
                      onClick = {navController.navigate("list")}
        )
        Spacer(modifier = Modifier.height(15.dp))
        //Búsqueda de entradas según un calendario, añadiremos un Date Picker, en proceso
        CustomBtnMenu(btnText = { Text(stringResource(id = R.string.calendar))},
                      btnIcon = { Icon(Icons.Filled.DateRange, contentDescription = null)},
                      containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                      onClick = {}
        )
        Spacer(modifier = Modifier.height(15.dp))
        //Abrimos los ajustes, en proceso
        CustomBtnMenu(btnText = { Text(stringResource(id = R.string.settings))},
                      btnIcon = { Icon(Icons.Filled.Build, contentDescription =
                      null)},
                      containerColor = MaterialTheme.colorScheme.onPrimary,
                      onClick = {}
        )
        Spacer(modifier = Modifier.height(15.dp))
        //Botón ara cerrar la app
        AlertDialogGeneral(
            title = { Text(stringResource(id = R.string.close_app))},
            text = { Text(stringResource(id = R.string.are_u_sure))},
            btnContainerColor = MaterialTheme.colorScheme.error,
            btnAlertText = { Text(stringResource(id = R.string.close))},
            btnAlertIcon = {Icon(Icons.Filled.ExitToApp,contentDescription = null)}
        )
    }
}