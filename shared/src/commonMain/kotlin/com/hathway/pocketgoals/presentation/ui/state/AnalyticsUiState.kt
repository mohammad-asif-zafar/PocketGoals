package com.hathway.pocketgoals.presentation.ui.state

import com.hathway.pocketgoals.domain.model.TransactionType
import com.hathway.pocketgoals.presentation.ui.components.analytics_components.AnalyticsCategoryData
import com.hathway.pocketgoals.presentation.ui.components.analytics_components.BarChartDataPoint
import com.hathway.pocketgoals.presentation.ui.components.analytics_components.DonutPieDataPoint


// Immutable state holding screen data variables
data class AnalyticsUiState(
    val totalAmount: String = "0",
    val categories: List<AnalyticsCategoryData> = emptyList(),
    val isTransactionsEmpty: Boolean = true,
    val selectedPeriod: String = "This Month",
    val selectedType: TransactionType = TransactionType.EXPENSE,
    val isLoading: Boolean = false,
    val barData: List<BarChartDataPoint> = emptyList(),
    val donutData: List<DonutPieDataPoint> = emptyList(),
    val trendPoints: List<Float> = emptyList(),
    val peakValueLabel: String = "0",
    val xAxisLabels: List<String> = emptyList()
)
