package com.hathway.pocketgoals.presentation.ui.state

import com.hathway.pocketgoals.presentation.ui.components.analytics_components.AnalyticsCategoryData


// Immutable state holding screen data variables
data class AnalyticsUiState(
    val totalAmount: String = "₹0",
    val categories: List<AnalyticsCategoryData> = emptyList(),
    val selectedPeriod: String = "This Month",
    val isLoading: Boolean = false
)
