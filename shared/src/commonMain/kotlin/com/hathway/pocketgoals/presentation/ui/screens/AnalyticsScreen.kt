package com.hathway.pocketgoals.presentation.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hathway.pocketgoals.presentation.ui.navigation_content.AnalyticsContent
import com.hathway.pocketgoals.presentation.ui.viewmodel.AnalyticsViewModel

@Composable
fun AnalyticsScreen(viewModel: AnalyticsViewModel = viewModel()) {

    // Safely collect the flow state matching active Compose lifecycles
    val uiState by viewModel.uiState.collectAsState()

    AnalyticsContent(
        uiState = uiState,
        onPeriodClick = { viewModel.updateSelectedPeriod("Next Month") }
    )
}
