package com.taller.timenavigator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.room.Room
import com.taller.timenavigator.data.EntryDatabase
import com.taller.timenavigator.ui.navigation.NavigationMenu
import com.taller.timenavigator.ui.theme.MyApplicationTheme
import com.taller.timenavigator.ui.viewmodels.EntryViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val db = Room.databaseBuilder(//Inicializamos la base de datos, creamos una variable
                        this,
                        EntryDatabase::class.java,
                        "db_diary"
                    ).build()
                    //Inicializamos el DAO
                    val dao = db.entryDao()
                    //Inicializamos el viewmodel
                    val viewModel = EntryViewModel(dao = dao)
                    NavigationMenu(viewmodel = viewModel)
                }
            }
        }
    }
}

