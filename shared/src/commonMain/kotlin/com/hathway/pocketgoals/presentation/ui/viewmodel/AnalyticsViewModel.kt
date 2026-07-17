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
                if (transactions.isEmpty()) {
                    _uiState.update {
                        it.copy(
                            totalAmount = "₹0",
                            categories = emptyList(),
                            isLoading = false
                        )
                    }
                } else {
                    // Calculate real analytics from transactions
                    val totalExpense = transactions
                        .filter { it.type == TransactionType.EXPENSE }
                        .sumOf { it.amount }
                    
                    // Simple category grouping for analytics
                    val categoryGroups = transactions
                        .filter { it.type == TransactionType.EXPENSE }
                        .groupBy { it.category }
                        .map { (category, txs) ->
                            val amount = txs.sumOf { it.amount }
                            val percentage = (amount / totalExpense * 100).toInt()
                            AnalyticsCategoryData(
                                name = category,
                                value = "₹${amount.toInt()} ($percentage%)",
                                color = Color(0xFF3B82F6), // Should come from category
                                icon = txs.first().icon
                            )
                        }

                    _uiState.update {
                        it.copy(
                            totalAmount = "₹${totalExpense.toInt()}",
                            categories = categoryGroups,
                            isLoading = false
                        )
                    }
                }
            }
        }
    }

    fun updateSelectedPeriod(period: String) {
        _uiState.update { it.copy(selectedPeriod = period) }
    }
}
