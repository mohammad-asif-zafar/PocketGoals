package com.hathway.pocketgoals.data.local.database

import androidx.room.RoomDatabase

expect class DatabaseFactory {
    fun createDatabase(): RoomDatabase.Builder<AppDatabase>
}
