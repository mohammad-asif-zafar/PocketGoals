package com.hathway.pocketgoals.presentation.ui.util

object CurrencyInputValidator {
    /**
     * Sanitizes incoming text entries to retain only mathematical formatting structures.
     * Drops letters, special symbols, and restricts decimals to a maximum of 2 digits.
     */
    fun formatAmountInput(input: String): String {
        // Strip out everything except digits and decimal point characters
        val sanitized = input.filter { it.isDigit() || it == '.' }

        // Ensure there is at most one decimal point layout split node
        val firstDecimalIdx = sanitized.indexOf('.')
        val singleDecimalString = if (firstDecimalIdx != -1) {
            val beforeDecimal = sanitized.substring(0, firstDecimalIdx)
            val afterDecimal = sanitized.substring(firstDecimalIdx + 1).replace(".", "")
            "$beforeDecimal.$afterDecimal"
        } else {
            sanitized
        }

        // Enforce two decimal points accuracy limit rules (cents tracking)
        return if (singleDecimalString.contains('.')) {
            val parts = singleDecimalString.split('.')
            val whole = parts[0]
            val decimal = parts[1].take(2)
            "$whole.$decimal"
        } else {
            singleDecimalString
        }
    }
}