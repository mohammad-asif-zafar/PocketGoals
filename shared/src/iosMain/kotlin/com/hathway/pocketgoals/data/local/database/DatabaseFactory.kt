package com.hathway.pocketgoals.data.local.database

import androidx.room.Room
import androidx.room.RoomDatabase
import platform.Foundation.NSHomeDirectory

actual class DatabaseFactory {
    actual fun createDatabase(): RoomDatabase.Builder<AppDatabase> {
        val dbFile = NSHomeDirectory() + "/pocketgoals.db"
        return Room.databaseBuilder<AppDatabase>(
            name = dbFile,
            factory = { AppDatabase::class.instantiateImpl() }
        ).fallbackToDestructiveMigration(true)
    }
}
