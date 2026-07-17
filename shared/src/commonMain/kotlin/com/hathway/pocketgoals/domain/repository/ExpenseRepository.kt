package com.hathway.pocketgoals.domain.repository

import com.hathway.pocketgoals.domain.model.Transaction
import kotlinx.coroutines.flow.Flow

interface ExpenseRepository {
    fun getAllExpenses(): Flow<List<Transaction>>
    suspend fun insertExpense(expense: Transaction)
    suspend fun deleteExpense(id: Long)
}
