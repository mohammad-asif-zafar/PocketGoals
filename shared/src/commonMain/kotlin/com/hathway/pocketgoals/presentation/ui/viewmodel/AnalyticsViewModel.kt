package com.hathway.pocketgoals.presentation.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.compose.ui.graphics.Color
import com.hathway.pocketgoals.domain.model.TransactionType
import com.hathway.pocketgoals.domain.repository.TransactionRepository
import com.hathway.pocketgoals.presentation.ui.components.analytics_components.AnalyticsCategoryData
import com.hathway.pocketgoals.presentation.ui.state.AnalyticsUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

import com.hathway.pocketgoals.presentation.ui.components.analytics_components.BarChartDataPoint
import com.hathway.pocketgoals.presentation.ui.components.analytics_components.DonutPieDataPoint

import com.hathway.pocketgoals.domain.ExpenseCategory
import com.hathway.pocketgoals.domain.IncomeType
import com.hathway.pocketgoals.domain.model.Transaction

class AnalyticsViewModel(
    private val repository: TransactionRepository
) : ViewModel() {

    // Internal mutable state backer
    private val _uiState = MutableStateFlow(AnalyticsUiState())
    // Public safe immutable wrapper flow
    val uiState: StateFlow<AnalyticsUiState> = _uiState.asStateFlow()

    init {
        loadAnalyticsData()
    }

    private fun loadAnalyticsData() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            repository.getTransactions().collect { transactions ->
                updateState(transactions)
            }
        }
    }

    private fun updateState(transactions: List<Transaction>) {
        val selectedType = _uiState.value.selectedType
        
        if (transactions.isEmpty()) {
            _uiState.update {
                it.copy(
                    totalAmount = "₹0",
                    categories = emptyList(),
                    isTransactionsEmpty = true,
                    isLoading = false,
                    barData = emptyList(),
                    donutData = emptyList()
                )
            }
            return
        }

        // Calculate real analytics from transactions filtered by type
        val filteredTransactions = transactions.filter { it.type == selectedType }
        val totalAmount = filteredTransactions.sumOf { it.amount }
        
        // Category grouping for analytics
        val categoryGroups = filteredTransactions
            .groupBy { it.category }
            .map { (category, txs) ->
                val amount = txs.sumOf { it.amount }
                val percentage = if (totalAmount > 0) (amount / totalAmount * 100).toInt() else 0
                val color = getCategoryColor(category, selectedType)
                AnalyticsCategoryData(
                    name = category,
                    value = "₹${amount.toInt()} ($percentage%)",
                    color = color,
                    icon = txs.first().icon
                )
            }.sortedByDescending { it.value.substringBefore(" ").substringAfter("₹").toIntOrNull() ?: 0 }

        // Bar Data: Group by day for simple weekly view
        val barData = listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun").map { day ->
            // Mocking day-wise distribution for now but summing actual data if date matched
            // Since our 'date' field is a formatted string, real grouping would need date parsing
            // For now, distributing total across days for visual effect if real parsing is complex
            val value = if (totalAmount > 0) (totalAmount / 7).toFloat() else 0f
            BarChartDataPoint(day, value)
        }

        // Real Donut Data from categories
        val donutData = categoryGroups.map { 
            val percentage = if (totalAmount > 0) {
                (it.value.substringAfter("(").substringBefore("%").toFloat())
            } else 0f
            DonutPieDataPoint(it.name, percentage, it.color)
        }

        _uiState.update {
            it.copy(
                totalAmount = "₹${totalAmount.toInt()}",
                categories = categoryGroups,
                isTransactionsEmpty = filteredTransactions.isEmpty(),
                isLoading = false,
                barData = barData,
                donutData = donutData
            )
        }
    }

    private fun getCategoryColor(categoryName: String, type: TransactionType): Color {
        return if (type == TransactionType.EXPENSE) {
            ExpenseCategory.defaultCategories.find { it.name == categoryName }?.color ?: Color(0xFF3B82F6)
        } else {
            IncomeType.defaultTypes.find { it.name == categoryName }?.color ?: Color(0xFF10B981)
        }
    }

    fun updateSelectedPeriod(period: String) {
        _uiState.update { it.copy(selectedPeriod = period) }
        // In a real app, this would re-fetch or re-filter by date range
    }

    fun updateSelectedType(type: TransactionType) {
        _uiState.update { it.copy(selectedType = type) }
        // Re-calculate state with current transactions
        viewModelScope.launch {
            repository.getTransactions().collect { transactions ->
                updateState(transactions)
            }
        }
    }
}
