package com.hathway.pocketgoals.presentation.ui.localization

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf

val LocalLanguageCode = staticCompositionLocalOf { "en" }

expect object LocaleManager {
    fun setLocale(langCode: String)
}

@Composable
fun LanguageProvider(languageCode: String, content: @Composable () -> Unit) {
    CompositionLocalProvider(LocalLanguageCode provides languageCode) {
        content()
    }
}
