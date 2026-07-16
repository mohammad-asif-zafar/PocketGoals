package com.hathway.pocketgoals.presentation.ui.localization

import platform.Foundation.NSUserDefaults

actual object LocaleManager {
    actual fun setLocale(langCode: String) {
        NSUserDefaults.standardUserDefaults.setObject(listOf(langCode), "AppleLanguages")
        NSUserDefaults.standardUserDefaults.synchronize()
    }
}
