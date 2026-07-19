package com.hathway.pocketgoals.data.repository

import com.hathway.pocketgoals.data.local.dao.ExpenseDao
import com.hathway.pocketgoals.data.local.dao.IncomeDao
import com.hathway.pocketgoals.data.mapper.toDomain
import com.hathway.pocketgoals.data.mapper.toExpenseEntity
import com.hathway.pocketgoals.data.mapper.toIncomeEntity
import com.hathway.pocketgoals.domain.model.Transaction
import com.hathway.pocketgoals.domain.model.TransactionType
import com.hathway.pocketgoals.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map

class RoomTransactionRepositoryImpl(
    private val expenseDao: ExpenseDao,
    private val incomeDao: IncomeDao
) : TransactionRepository {

    override fun getTransactions(): Flow<List<Transaction>> {
        return combine(
            expenseDao.getAllExpenses(),
            incomeDao.getAllIncomes()
        ) { expenses, incomes ->
            val all = expenses.map { it.toDomain() } + incomes.map { it.toDomain() }
            all.sortedByDescending { it.createdAt }
        }
    }

    override suspend fun addTransaction(transaction: Transaction) {
        when (transaction.type) {
            TransactionType.EXPENSE -> expenseDao.insertExpense(transaction.toExpenseEntity())
            TransactionType.INCOME -> incomeDao.insertIncome(transaction.toIncomeEntity())
            TransactionType.GOAL_CREATED -> expenseDao.insertExpense(transaction.toExpenseEntity())
        }
    }

    override suspend fun updateTransaction(transaction: Transaction) {
        addTransaction(transaction) // REPLACE strategy
    }

    override suspend fun deleteTransaction(id: String) {
        when {
            id.startsWith("exp_") -> {
                id.removePrefix("exp_").toLongOrNull()?.let { expenseDao.deleteExpense(it) }
            }
            id.startsWith("inc_") -> {
                id.removePrefix("inc_").toLongOrNull()?.let { incomeDao.deleteIncome(it) }
            }
            else -> {
                val longId = id.toLongOrNull() ?: return
                expenseDao.deleteExpense(longId)
                incomeDao.deleteIncome(longId)
            }
        }
    }
}
