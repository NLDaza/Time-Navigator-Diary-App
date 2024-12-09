package com.taller.myapplication.ui.menu

import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.taller.myapplication.R


@Composable //Revisar los contentdescription, menu mejorado
fun MenuScreen(navController: NavController){
    Backgroundapp()
    Column (
        modifier = Modifier
            .fillMaxSize() //Para que ocupe lo máximo posible
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,//Para que se alineen
    ){

        Text(
            text = stringResource(id = R.string.app_name),
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
        //ELIMINAR LISTA DE ENTRADAS, REDUNDANTE
        /*CustomBtnMenu(btnText = { Text(stringResource(id = R.string
            .entries_list))},
                      btnIcon = { Icon(Icons.Filled.List, contentDescription = null)},
                      containerColor = MaterialTheme.colorScheme.primaryContainer,
                      onClick = {navController.navigate("list")}
        )
        Spacer(modifier = Modifier.height(15.dp))*/
        //Búsqueda de entradas según un calendario, añadiremos un Date Picker, en proceso
        CustomBtnMenu(btnText = { Text(stringResource(id = R.string.calendar))},
                      btnIcon = { Icon(Icons.Filled.DateRange, contentDescription = null)},
                      containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                      onClick = {navController.navigate("calendar")}
        )
        Spacer(modifier = Modifier.height(15.dp))
        //Abrimos los ajustes, en proceso
        CustomBtnMenu(btnText = { Text(stringResource(id = R.string.settings))},
                      btnIcon = { Icon(Icons.Filled.Build, contentDescription =
                      null)},
                      containerColor = MaterialTheme.colorScheme.onPrimary,
                      onClick = {navController.navigate("settings")}
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
@Composable
fun AlertDialogGeneral(
    title: @Composable () -> Unit,
    text: @Composable () -> Unit,
    btnContainerColor: Color,
    btnAlertText: @Composable () -> Unit,
    btnAlertIcon: @Composable () -> Unit,
){
    val context = LocalContext.current

    val openDialog = remember { mutableStateOf(false) }

    if (openDialog.value){
        AlertDialog(
            //Quita el dialog cuando clica fuera
            onDismissRequest = {openDialog.value = false},
            title = title,
            text = text,
            confirmButton = {
                TextButton(onClick = {
                    openDialog.value = false
                    (context as Activity).finish()
                }) { Text(stringResource(id = R.string.confirm)) }
            },
            dismissButton = {
                TextButton(onClick = { openDialog.value = false }) { Text(
                    stringResource(id = R.string.cancel)
                ) }
            }
        )
    }
    CustomBtnMenu(btnText = btnAlertText,
                  btnIcon = btnAlertIcon,
                  containerColor = btnContainerColor,
                  onClick = {openDialog.value = true}
    )
}
@Composable
fun CustomBtnMenu (
    btnText: @Composable () -> Unit,//El texto que recibe le ponemos una
    // expresión lambda, por lo que se le podrá poner cualquier Composable
    // para personalizar el texto, haremos lo mismo con icon.
    btnIcon: @Composable () -> Unit,
    containerColor: Color = MaterialTheme.colorScheme.primary,
    onClick: () -> Unit,
) {
    ExtendedFloatingActionButton(
        onClick = onClick,
        containerColor = containerColor,
        contentColor = contentColorFor(backgroundColor = containerColor),
        icon = { btnIcon() },
        text = { btnText() },
        modifier = Modifier.size(width = 240.dp, height = 55.dp)
    )
}
@Composable
fun Backgroundapp(){
    Image(
        painter = painterResource(id = R.drawable.fondo_app ),
        contentDescription = "fondo de pantalla",
        contentScale = ContentScale.FillBounds,
        modifier = Modifier.fillMaxSize()
    )
}