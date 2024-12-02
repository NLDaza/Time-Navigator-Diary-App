package com.taller.myapplication.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName= "entries")
data class Entry (
    @PrimaryKey(autoGenerate = false)
    val idEntry: String,
    @ColumnInfo ("mood")
    val mood: String,
    @ColumnInfo ("score")
    val score: String,
    @ColumnInfo ("memory")
    val memory: String,
    @ColumnInfo ("day")
    val day: String,
    @ColumnInfo ("month")
    val month: String,
    @ColumnInfo ("year")
    val year: String
)