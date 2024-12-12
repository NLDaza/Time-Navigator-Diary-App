package com.taller.myapplication.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.taller.myapplication.R
import com.taller.myapplication.ui.menu.BackgroundApp
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
                    stringResource(id = R.string.app_name),
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
                            contentDescription = stringResource(id = R.string.back),
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
    viewModel: EntryViewModel,
    idEntry: Int){
    BackgroundApp()
    LaunchedEffect(idEntry) {
        viewModel.getEntryById(idEntry)
    }
    val paddingValues: PaddingValues = it
    val selectedEntry = viewModel.selectedEntry
    val openDialogDelete = remember { mutableStateOf(false) }
    //Pasamos selectedEntry y modificamos por ejemplo mood a selectedEntry.mood
    selectedEntry?.let {

        Column (modifier = Modifier
            .padding(paddingValues)
            .padding(top = 20.dp)
            .padding(horizontal = 20.dp)
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
        ){
            Box (modifier = Modifier
                .background(color = Color.White)
                .border(width = 2.dp, color = MaterialTheme.colorScheme.primary)
                .padding(8.dp)
                .fillMaxWidth(),
                 contentAlignment = Alignment.Center
            ){
                Row {
                    Text(stringResource(id = R.string.date))
                    Text(
                        text = "${selectedEntry.day} / ${selectedEntry.month} / ${selectedEntry.year}",
                        textAlign = TextAlign.Center
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(15.dp))
            Box(modifier = Modifier
                .height(50.dp)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .background(color = Color.White)
                .border(width = 2.dp, color = MaterialTheme.colorScheme.primary)
                .padding(8.dp)){
                Text(
                    text = selectedEntry.mood,
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth(),
                )
            }
            Spacer(modifier = Modifier.height(15.dp))
            Box (modifier = Modifier
                .height(460.dp)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .background(color = Color.White)
                .border(
                    width = 2.dp,
                    color = MaterialTheme.colorScheme.primary,
                )
                .padding(8.dp)
            ){
                Text(
                    text = selectedEntry.memory,
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                )
            }
            
            Button(onClick = {navController.navigate("edit/${idEntry}")
            }
            ) {
                Text(stringResource(id = R.string.edit_entry))
            }
            if(openDialogDelete.value) {
                AlertDialog(
                    onDismissRequest = {openDialogDelete.value = false},
                    title = {Text (stringResource(id = R.string.delete_entry_title))},
                    text = {Text(stringResource(id = R.string.wont_be_able_to_recover))},
                    confirmButton = {
                        TextButton(
                            onClick = {
                                viewModel.deleteEntry(it)
                                navController.popBackStack("calendar", inclusive = false)
                            }) {
                            Text(stringResource(id = R.string.accept))
                        }
                    },
                    dismissButton = {
                        TextButton(
                            onClick = {
                                openDialogDelete.value = false
                            }
                        ) {
                            Text(stringResource(id = R.string.cancel))
                        }
                    }
                )
            }
            Button(
                onClick = { openDialogDelete.value= true }
            ) {
                Text(stringResource(id = R.string.delete_entry))
            }
        }
    } ?: Text(stringResource(id = R.string.entry_not_found))
}