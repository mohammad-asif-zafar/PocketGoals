package com.hathway.pocketgoals.presentation.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.hathway.pocketgoals.domain.Transaction
import com.hathway.pocketgoals.domain.TransactionType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountBalance
import androidx.compose.material.icons.rounded.DirectionsCar
import androidx.compose.material.icons.rounded.Lightbulb
import androidx.compose.material.icons.rounded.ShoppingCart

class TransactionsViewModel : ViewModel() {
    private val _transactions = MutableStateFlow(listOf(
        Transaction("1", "Groceries", 2500.0, TransactionType.EXPENSE, "18 May 2024", "10:30 AM", "Groceries", "Cash", "Weekly groceries from supermarket", Icons.Rounded.ShoppingCart),
        Transaction("2", "Salary", 125000.0, TransactionType.INCOME, "15 May 2024", "09:00 AM", "Salary", "Bank Transfer", "", Icons.Rounded.AccountBalance),
        Transaction("3", "Electricity Bill", 1200.0, TransactionType.EXPENSE, "14 May 2024", "08:00 PM", "Bills", "UPI", "", Icons.Rounded.Lightbulb),
        Transaction("4", "Fuel", 4500.0, TransactionType.EXPENSE, "14 May 2024", "04:45 PM", "Transport", "Card", "", Icons.Rounded.DirectionsCar)
    ))
    val transactions: StateFlow<List<Transaction>> = _transactions.asStateFlow()

    fun addTransaction(transaction: Transaction) {
        _transactions.update { current ->
            listOf(transaction) + current
        }
    }
}