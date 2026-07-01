package com.hathway.pocketgoals.presentation.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hathway.pocketgoals.presentation.ui.state.HomeUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

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

            // Simulation of concurrent database queries or backend updates
            _uiState.update {
                it.copy(
                    userName = "Alex",
                    totalBalance = "1,45,000",
                    income = "2,00,000",
                    expenses = "55,000",
                    unreadNotificationCount = 5, // Hydrated safely via asynchronous data streams
                    isLoading = false
                )
            }
        }
    }

    // Call this execution handle whenever a notification message gets read/cleared
    fun clearNotifications() {
        _uiState.update { it.copy(unreadNotificationCount = 0) }
    }
}
