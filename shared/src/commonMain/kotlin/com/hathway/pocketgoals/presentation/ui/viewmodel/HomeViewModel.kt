package com.hathway.pocketgoals.presentation.ui.viewmodel

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hathway.pocketgoals.domain.model.TransactionType
import com.hathway.pocketgoals.domain.repository.TransactionRepository
import com.hathway.pocketgoals.presentation.ui.components.analytics_components.AnalyticsCategoryData
import com.hathway.pocketgoals.presentation.ui.state.HomeUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: TransactionRepository
) : ViewModel() {

    // Internal mutable state backer
    private val _uiState = MutableStateFlow(HomeUiState())

    // Public safe immutable wrapper flow consumed by Composable screens
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        loadDashboardData()
    }

    private fun loadDashboardData() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            repository.getTransactions().collect { transactions ->
                val totalIncome = transactions
                    .filter { it.type == TransactionType.INCOME }
                    .sumOf { it.amount }
                
                val totalExpense = transactions
                    .filter { it.type == TransactionType.EXPENSE }
                    .sumOf { it.amount }
                
                val balance = totalIncome - totalExpense

                // Simple category grouping for top categories
                val categoryGroups = transactions
                    .filter { it.type == TransactionType.EXPENSE }
                    .groupBy { it.category }
                    .map { (category, txs) ->
                        val amount = txs.sumOf { it.amount }
                        val percentage = if (totalExpense > 0) (amount / totalExpense * 100).toInt() else 0
                        AnalyticsCategoryData(
                            name = category,
                            value = "${amount.toInt()} ($percentage%)",
                            color = Color(0xFF3B82F6), // Should come from category
                            icon = txs.first().icon
                        )
                    }.sortedByDescending { it.value }

                _uiState.update {
                    it.copy(
                        userName = "User",
                        totalBalance = balance.toInt().toString(),
                        income = totalIncome.toInt().toString(),
                        expenses = totalExpense.toInt().toString(),
                        monthlyIncome = totalIncome.toInt().toString(), // Using total as monthly for now
                        monthlyExpenses = totalExpense.toInt().toString(),
                        monthlySavings = (totalIncome - totalExpense).toInt().toString(),
                        topCategories = categoryGroups,
                        isTransactionsEmpty = transactions.isEmpty(),
                        unreadNotificationCount = 0,
                        isLoading = false
                    )
                }
            }
        }
    }

    // Call this execution handle whenever a notification message gets read/cleared
    fun clearNotifications() {
        _uiState.update { it.copy(unreadNotificationCount = 0) }
    }
}
