package com.hathway.pocketgoals.presentation.ui.state

// Encapsulates all reactive data properties for the home dashboard screen
data class HomeUiState(
    val userName: String = "",
    val totalBalance: String = "0.00",
    val income: String = "0.00",
    val expenses: String = "0.00",
    val unreadNotificationCount: Int = 0,
    val isLoading: Boolean = false
)
