package com.taller.myapplication.ui

import android.app.Activity
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.contentColorFor
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
import androidx.compose.ui.unit.dp
import com.taller.myapplication.R
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale


//Componentes del Menu, traspasados


//Componentes NewEntryScreen
/*@Composable //Quizás cambiar por un Date Picker, necesita API 26
fun FechaActual(){
    val formatofecha = DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.UK)
    val date = LocalDate.now().format(formatofecha)
    Text(
        text = date,
        style = MaterialTheme.typography.titleLarge
    )
}*/
@Composable
fun EntryTextField(contenido: String) {
    var contenido by remember { mutableStateOf("") }
    OutlinedTextField(
        value = contenido,
        onValueChange = { contenido = it},
        modifier = Modifier
            .fillMaxWidth()
            .height(500.dp)
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    )
}
@Composable
fun BtnGuardar(
    onClick: () -> Unit
){
    ExtendedFloatingActionButton(
        text = { Text(stringResource(id = R.string.save)) },
        containerColor = MaterialTheme.colorScheme.tertiaryContainer,
        icon = { Icon(Icons.Filled.Done, contentDescription = null) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 30.dp)
            .padding(bottom = 15.dp),
        onClick = {})
}
@Composable
fun CustomOutlinedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(text = label) },
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 30.dp)
            .padding(bottom = 15.dp)
    )
}

@Composable //Revisar si hace falta su uso
fun CustomBtn (
    btnText: @Composable () -> Unit,//El texto que recibe le ponemos una
    // expresión lambda, por lo que se le podrá poner cualquier Composable
    // para personalizar el texto, haremos lo mismo con icon.
    btnIcon: @Composable () -> Unit,
    containerColor: Color = MaterialTheme.colorScheme.primary,
    onClick: () -> Unit,
    modifier: Modifier
) {
    ExtendedFloatingActionButton(
        onClick = onClick,
        containerColor = containerColor,
        contentColor = contentColorFor(backgroundColor = containerColor),
        icon = { btnIcon() },
        text = { btnText() },
        modifier = modifier
    )
}
//Componentes Settings
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