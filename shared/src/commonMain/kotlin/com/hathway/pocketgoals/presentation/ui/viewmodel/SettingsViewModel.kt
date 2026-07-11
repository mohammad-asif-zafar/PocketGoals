package com.hathway.pocketgoals.presentation.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hathway.pocketgoals.data.local.PreferenceManager
import com.hathway.pocketgoals.domain.model.AppLanguage
import com.hathway.pocketgoals.domain.model.ThemeMode
import com.russhwolf.settings.Settings
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SettingsViewModel : ViewModel() {
    private val preferenceManager = PreferenceManager(Settings())

    val themeMode: StateFlow<ThemeMode> = preferenceManager.themeMode
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ThemeMode.SYSTEM)

    val language: StateFlow<AppLanguage> = preferenceManager.language
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), AppLanguage.ENGLISH)

    fun setThemeMode(mode: ThemeMode) {
        viewModelScope.launch {
            preferenceManager.setThemeMode(mode)
        }
    }

    fun setLanguage(lang: AppLanguage) {
        viewModelScope.launch {
            preferenceManager.setLanguage(lang)
        }
    }
}