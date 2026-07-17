package com.hathway.pocketgoals

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hathway.pocketgoals.presentation.ui.localization.LanguageProvider
import com.hathway.pocketgoals.presentation.ui.navigation.NavigationContainer
import com.hathway.pocketgoals.presentation.ui.theme.PocketGoalsTheme
import com.hathway.pocketgoals.presentation.ui.viewmodel.SettingsViewModel

@Composable
@Preview
fun App(
    settingsViewModel: SettingsViewModel = viewModel()
) {
    val themeMode by settingsViewModel.themeMode.collectAsState()
    val language by settingsViewModel.language.collectAsState()

    // Using key(language) forces a full recomposition when the language changes,
    // ensuring all stringResource() calls are re-evaluated.
    key(language) {
        LanguageProvider(language.code) {
            PocketGoalsTheme(themeMode = themeMode) {
                NavigationContainer()
            }
        }
    }
}