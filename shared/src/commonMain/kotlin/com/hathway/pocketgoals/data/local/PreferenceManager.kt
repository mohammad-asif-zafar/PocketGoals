package com.hathway.pocketgoals.data.local

import com.hathway.pocketgoals.domain.model.AppLanguage
import com.hathway.pocketgoals.domain.model.ThemeMode
import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.ObservableSettings
import com.russhwolf.settings.Settings
import com.russhwolf.settings.coroutines.toFlowSettings
import com.russhwolf.settings.set
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

@OptIn(ExperimentalSettingsApi::class)
class PreferenceManager(private val settings: Settings) {
    private val flowSettings = (settings as ObservableSettings).toFlowSettings()

    val themeMode: Flow<ThemeMode> = flowSettings.getStringFlow("theme_mode", ThemeMode.SYSTEM.name)
        .map { try { ThemeMode.valueOf(it) } catch (e: Exception) { ThemeMode.SYSTEM } }

    val language: Flow<AppLanguage> = flowSettings.getStringFlow("language", AppLanguage.ENGLISH.name)
        .map { try { AppLanguage.valueOf(it) } catch (e: Exception) { AppLanguage.ENGLISH } }

    suspend fun setThemeMode(mode: ThemeMode) {
        settings["theme_mode"] = mode.name
    }

    suspend fun setLanguage(lang: AppLanguage) {
        settings["language"] = lang.name
    }
}