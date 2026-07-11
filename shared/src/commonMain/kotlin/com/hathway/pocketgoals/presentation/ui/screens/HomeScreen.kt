package com.hathway.pocketgoals.presentation.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hathway.pocketgoals.presentation.ui.navigation_content.HomeContent
import com.hathway.pocketgoals.presentation.ui.viewmodel.HomeViewModel

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = viewModel(), onProfileNavigation: () -> Unit = {},
    onAddExpenseClick: () -> Unit = {},
    onAddIncomeClick: () -> Unit = {},
    onTransferClick: () -> Unit = {},
    onViewReportsClick: () -> Unit = {},
    onManageClick: () -> Unit = {}

) {
    // Collect updates safely matching active Compose lifecycles
    val uiState by viewModel.uiState.collectAsState()

    HomeContent(
        uiState = uiState,
        onNotificationClick = { viewModel.clearNotifications() },
        onProfileClick = onProfileNavigation,
        onAddExpenseClick = { onAddExpenseClick() },
        onAddIncomeClick = { onAddIncomeClick() },
        onTransferClick = { onTransferClick() },
        onViewReportsClick = { onViewReportsClick() },
        onManageClick = { onManageClick() }
    )
}
