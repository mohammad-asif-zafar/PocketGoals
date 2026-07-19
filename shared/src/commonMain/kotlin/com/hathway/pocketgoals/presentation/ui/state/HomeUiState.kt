package com.hathway.pocketgoals.presentation.ui.state

import com.hathway.pocketgoals.presentation.ui.components.analytics_components.AnalyticsCategoryData

// Encapsulates all reactive data properties for the home dashboard screen
data class HomeUiState(
    val userName: String = "",
    val totalBalance: String = "0.00",
    val income: String = "0.00",
    val expenses: String = "0.00",
    val monthlyIncome: String = "0.00",
    val monthlyExpenses: String = "0.00",
    val monthlySavings: String = "0.00",
    val topCategories: List<AnalyticsCategoryData> = emptyList(),
    val isTransactionsEmpty: Boolean = true,
    val unreadNotificationCount: Int = 0,
    val isLoading: Boolean = false
)
