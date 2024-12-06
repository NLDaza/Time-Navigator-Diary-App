package com.taller.myapplication.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName= "entries")
data class Entry (
    @PrimaryKey(autoGenerate = true)
    val idEntry: Int,
    @ColumnInfo ("mood")
    val mood: String,
    @ColumnInfo ("memory")
    val memory: String,
    @ColumnInfo ("day")
    val day: Int,
    @ColumnInfo ("month")
    val month: Int,
    @ColumnInfo ("year")
    val year: Int
)