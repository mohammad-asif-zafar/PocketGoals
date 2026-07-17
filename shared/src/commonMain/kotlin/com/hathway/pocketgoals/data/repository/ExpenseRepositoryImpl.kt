package com.hathway.pocketgoals.data.repository

import com.hathway.pocketgoals.data.local.dao.ExpenseDao
import com.hathway.pocketgoals.data.mapper.toDomain
import com.hathway.pocketgoals.data.mapper.toExpenseEntity
import com.hathway.pocketgoals.domain.model.Transaction
import com.hathway.pocketgoals.domain.repository.ExpenseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ExpenseRepositoryImpl(
    private val expenseDao: ExpenseDao
) : ExpenseRepository {
    override fun getAllExpenses(): Flow<List<Transaction>> {
        return expenseDao.getAllExpenses().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun insertExpense(expense: Transaction) {
        expenseDao.insertExpense(expense.toExpenseEntity())
    }

    override suspend fun deleteExpense(id: Long) {
        expenseDao.deleteExpense(id)
    }
}
