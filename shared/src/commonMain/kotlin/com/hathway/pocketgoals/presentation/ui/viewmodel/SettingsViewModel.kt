package com.hathway.pocketgoals.presentation.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hathway.pocketgoals.data.local.PreferenceManager
import com.hathway.pocketgoals.domain.model.AppLanguage
import com.hathway.pocketgoals.domain.model.ThemeMode
import com.russhwolf.settings.Settings
import com.hathway.pocketgoals.presentation.ui.localization.LocaleManager
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SettingsViewModel : ViewModel() {
    private val preferenceManager = PreferenceManager(Settings())

    init {
        // Apply saved language on startup
        viewModelScope.launch {
            preferenceManager.language.collect { lang ->
                LocaleManager.setLocale(lang.code)
            }
        }
    }

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
            LocaleManager.setLocale(lang.code)
        }
    }
}