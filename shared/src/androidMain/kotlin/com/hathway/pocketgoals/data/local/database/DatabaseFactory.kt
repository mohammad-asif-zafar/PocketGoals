package com.hathway.pocketgoals.data.local.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

actual class DatabaseFactory(private val context: Context) {
    actual fun createDatabase(): RoomDatabase.Builder<AppDatabase> {
        val dbFile = context.getDatabasePath("pocketgoals.db")
        return Room.databaseBuilder<AppDatabase>(
            context = context,
            name = dbFile.absolutePath
        ).fallbackToDestructiveMigration(true)
    }
}
