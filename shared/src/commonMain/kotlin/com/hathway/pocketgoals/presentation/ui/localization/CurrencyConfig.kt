package com.hathway.pocketgoals.presentation.ui.localization

import androidx.compose.ui.text.intl.Locale

enum class CurrencyConfig(
    val isoCode: String, val symbol: String, val isSymbolPrefix: Boolean
) {
    INR("INR", "₹", true), MYR("MYR", "RM", true), AED(
        "AED", "د.إ", false
    ), // Symbol placed after amount for Arabic/AED standard
    USD("USD", "$", true);

    companion object {
        /**
         * Resolves the currency configuration based on the current system language locale code.
         */
        fun fromSystemLocale(): CurrencyConfig {
            return when (Locale.current.language.lowercase()) {
                "hi" -> INR
                "ur" -> INR
                "ms" -> MYR
                "ar" -> AED
                else -> USD // Default fallback
            }
        }
    }
}