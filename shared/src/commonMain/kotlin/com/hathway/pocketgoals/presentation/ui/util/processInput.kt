package com.hathway.pocketgoals.presentation.ui.util

object AmountFormatter {
    /**
     * Processes input string changes for currency configurations.
     * Enforces character limits and strict single-decimal constraints.
     */
    fun processInput(
        currentAmount: String, inputChar: String, maxLength: Int = 12, maxDecimalPlaces: Int = 2
    ): String {
        val hasDot = currentAmount.contains(".")

        // 1. Block multiple decimal points
        if (inputChar == "." && hasDot) return currentAmount

        // 2. Format zero starting sequences
        if (inputChar == "." && currentAmount == "0") return "0."
        if (currentAmount == "0" && inputChar != ".") return inputChar

        // 3. Optional: Limit decimal numbers to a set count (e.g., cents/paise)
        if (hasDot && !inputChar.contains(".")) {
            val decimalPart = currentAmount.substringAfter(".")
            if (decimalPart.length >= maxDecimalPlaces) return currentAmount
        }

        // 4. Enforce max character limit
        if (currentAmount.length >= maxLength) return currentAmount

        return currentAmount + inputChar
    }
}
