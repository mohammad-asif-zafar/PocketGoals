package com.hathway.pocketgoals

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hathway.pocketgoals.presentation.ui.navigation.NavigationContainer
import com.hathway.pocketgoals.presentation.ui.theme.PocketGoalsTheme
import com.hathway.pocketgoals.presentation.ui.viewmodel.SettingsViewModel

@Composable
@Preview
fun App(
    settingsViewModel: SettingsViewModel = viewModel()
) {
    val themeMode by settingsViewModel.themeMode.collectAsState()
    
    PocketGoalsTheme(themeMode = themeMode) {
        NavigationContainer()
    }
}