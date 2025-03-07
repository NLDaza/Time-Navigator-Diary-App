package com.taller.timenavigator.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database (
    entities = [Entry::class],
    version = 1,
    exportSchema = false
)
abstract class EntryDatabase: RoomDatabase() {
    abstract fun entryDao(): EntryDatabaseDao
}