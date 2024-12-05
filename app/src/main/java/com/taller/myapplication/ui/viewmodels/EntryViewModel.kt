package com.taller.myapplication.ui.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.taller.myapplication.data.Entry
import com.taller.myapplication.data.EntryDatabaseDao
import com.taller.myapplication.ui.states.EntryState
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class EntryViewModel (
    private val dao: EntryDatabaseDao
): ViewModel() {
    var state by mutableStateOf(EntryState())
        private set
    var selectedEntry by mutableStateOf<Entry?>(null)
        private set
    //Definimos init, para que cada vez que se abra la app, ejecute esto en
    // primer lugar, que se obtengan las entradas de la base de datos para
    // luego mostrarlas al usuario.
    init {
        viewModelScope.launch {
            dao.getAllEntries().collectLatest {
                state = state.copy(entryList = it)
            }
        }
    }
    //Añadimos las funciones de añadir entrada, editar y eliminar
    fun addEntry(entry: Entry) = viewModelScope.launch {
        dao.addEntry(entry)
    }
    fun updateEntry(entry: Entry) = viewModelScope.launch {
        dao.updateEntry(entry)
    }
    fun deleteEntry(entry: Entry) = viewModelScope.launch {
        dao.deleteEntry(entry)
    }
    fun getEntryById(idEntry: Int )= viewModelScope.launch {
        selectedEntry = dao.getEntryById(idEntry)
    }
}