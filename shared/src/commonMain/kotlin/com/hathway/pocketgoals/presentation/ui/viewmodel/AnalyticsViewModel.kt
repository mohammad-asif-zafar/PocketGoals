package com.hathway.pocketgoals.presentation.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Kitchen
import androidx.compose.material.icons.rounded.Work
import androidx.compose.ui.graphics.Color
import com.hathway.pocketgoals.presentation.ui.components.analytics_components.AnalyticsCategoryData
import com.hathway.pocketgoals.presentation.ui.state.AnalyticsUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AnalyticsViewModel : ViewModel() {

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

            // Simulation point for database or networking layer routines
            val sampleCategories = listOf(
                AnalyticsCategoryData("Investment", "₹31,023 (34.47%)", Color(0xFF3B82F6), Icons.Rounded.Work),
                AnalyticsCategoryData("Basic Needs", "₹25,029 (27.81%)", Color(0xFF10B981), Icons.Rounded.Home),
                AnalyticsCategoryData("Future Pay", "₹13,599 (15.11%)", Color(0xFFF59E0B), Icons.Rounded.Kitchen)
            )

            _uiState.update {
                it.copy(
                    totalAmount = "₹90,000",
                    categories = sampleCategories,
                    isLoading = false
                )
            }
        }
    }

    fun updateSelectedPeriod(period: String) {
        _uiState.update { it.copy(selectedPeriod = period) }
        // Re-trigger loadAnalyticsData() here based on the selected period if needed
    }
}
