package com.hathway.pocketgoals.presentation.ui.localization

import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import java.util.Locale

actual object LocaleManager {
    actual fun setLocale(langCode: String) {
        val appLocale: LocaleListCompat = LocaleListCompat.forLanguageTags(langCode)
        AppCompatDelegate.setApplicationLocales(appLocale)
        
        // Also set the default locale for the JVM to ensure non-Android resource calls match
        Locale.setDefault(Locale.forLanguageTag(langCode))
    }
}
