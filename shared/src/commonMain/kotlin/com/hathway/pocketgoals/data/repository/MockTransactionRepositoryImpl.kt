package com.hathway.pocketgoals.data.repository

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountBalance
import androidx.compose.material.icons.rounded.DirectionsCar
import androidx.compose.material.icons.rounded.Lightbulb
import androidx.compose.material.icons.rounded.ShoppingCart
import com.hathway.pocketgoals.domain.model.Transaction
import com.hathway.pocketgoals.domain.model.TransactionType
import com.hathway.pocketgoals.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MockTransactionRepositoryImpl : TransactionRepository {
    private val _transactions = MutableStateFlow(
        listOf(
            Transaction(
                "1",
                "Groceries",
                2500.0,
                TransactionType.EXPENSE,
                "18 May 2024",
                "10:30 AM",
                "Groceries",
                "Cash",
                "Weekly groceries from supermarket",
                Icons.Rounded.ShoppingCart
            ), Transaction(
                "2",
                "Salary",
                125000.0,
                TransactionType.INCOME,
                "15 May 2024",
                "09:00 AM",
                "Salary",
                "Bank Transfer",
                "",
                Icons.Rounded.AccountBalance
            ), Transaction(
                "3",
                "Electricity Bill",
                1200.0,
                TransactionType.EXPENSE,
                "14 May 2024",
                "08:00 PM",
                "Bills",
                "UPI",
                "",
                Icons.Rounded.Lightbulb
            ), Transaction(
                "4",
                "Fuel",
                4500.0,
                TransactionType.EXPENSE,
                "14 May 2024",
                "04:45 PM",
                "Transport",
                "Card",
                "",
                Icons.Rounded.DirectionsCar
            )
        )
    )

    override fun getTransactions(): Flow<List<Transaction>> = _transactions.asStateFlow()

    override suspend fun addTransaction(transaction: Transaction) {
        _transactions.update { current -> listOf(transaction) + current }
    }

    override suspend fun updateTransaction(transaction: Transaction) {
        _transactions.update { currentList ->
            currentList.map { existingTx ->
                if (existingTx.id == transaction.id) transaction else existingTx
            }
        }
    }

    override suspend fun deleteTransaction(id: String) {
        _transactions.update { currentList ->
            currentList.filter { it.id != id }
        }
    }
}
