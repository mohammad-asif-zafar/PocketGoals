package com.hathway.pocketgoals.presentation.ui.localization

import androidx.compose.ui.text.intl.Locale

enum class CurrencyConfig(
    val isoCode: String, val symbol: String, val isSymbolPrefix: Boolean
) {
    INR("INR", "₹", true),
    MYR("MYR", "RM", true),
    AED("AED", "د.إ", false),
    USD("USD", "$", true),
    PAK("PNR", "RE", true);

    companion object {
        /**
         * Resolves the currency configuration based on the current system language locale code.
         */
        fun fromSystemLocale(): CurrencyConfig {
            return fromLanguageCode(Locale.current.language)
        }

        fun fromLanguageCode(code: String): CurrencyConfig {
            return when (code.lowercase()) {
                "hi" -> INR
                "ur" -> INR
                "ms" -> MYR
                "ar" -> AED
                else -> USD // Default fallback
            }
        }
    }
}