package com.taller.myapplication.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow


@Dao
interface EntryDatabaseDao {
    @Insert //Funciones suspendidas para evitar bloquear el hilo principal
    suspend fun addEntry(entry: Entry)
    @Update
    suspend fun updateEntry(entry: Entry)
    @Delete
    suspend fun deleteEntry(entry: Entry)

    @Query("SELECT * FROM entries")
    fun getAllEntries(): Flow<List<Entry>>

    @Query("SELECT * FROM entries WHERE idEntry = :id LIMIT 1")
    suspend fun getEntryById(id: Int): Entry?
}