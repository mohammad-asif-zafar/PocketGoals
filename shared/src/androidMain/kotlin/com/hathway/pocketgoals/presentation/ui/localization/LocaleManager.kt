package com.hathway.pocketgoals.presentation.ui.localization

import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat

actual object LocaleManager {
    actual fun setLocale(langCode: String) {
        val appLocale: LocaleListCompat = LocaleListCompat.forLanguageTags(langCode)
        AppCompatDelegate.setApplicationLocales(appLocale)
    }
}
