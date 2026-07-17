package com.hathway.pocketgoals.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hathway.pocketgoals.data.local.dao.*
import com.hathway.pocketgoals.data.local.entity.*

@Database(
    entities = [
        ExpenseEntity::class,
        IncomeEntity::class,
        GoalEntity::class,
        BudgetEntity::class,
        AccountEntity::class,
        CategoryEntity::class
    ],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun expenseDao(): ExpenseDao
    abstract fun incomeDao(): IncomeDao
    abstract fun goalDao(): GoalDao
    abstract fun budgetDao(): BudgetDao
    abstract fun accountDao(): AccountDao
    abstract fun categoryDao(): CategoryDao
}
