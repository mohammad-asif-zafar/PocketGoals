package com.hathway.pocketgoals.presentation.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import org.koin.compose.koinInject
import com.hathway.pocketgoals.presentation.ui.navigation_content.AnalyticsContent
import com.hathway.pocketgoals.presentation.ui.viewmodel.AnalyticsViewModel

@Composable
fun AnalyticsScreen(viewModel: AnalyticsViewModel = koinInject()) {

    // Safely collect the flow state matching active Compose lifecycles
    val uiState by viewModel.uiState.collectAsState()

    AnalyticsContent(
        uiState = uiState,
        onPeriodChange = { viewModel.updateSelectedPeriod(it) },
        onTypeChange = { viewModel.updateSelectedType(it) }
    )
}
