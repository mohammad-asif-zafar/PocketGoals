package com.hathway.pocketgoals.presentation.ui.localization

object CurrencyFormatter {
    /**
     * Formats an input amount string according to a selected currency structure.
     */
    fun formatAmount(amount: String, config: CurrencyConfig): String {
        val cleanAmount = amount.replace(Regex("[^0-9.]"), "")
        if (cleanAmount.isEmpty()) return "${config.symbol} 0"

        // Custom simple regex formatter for standard thousands grouping separation splits
        val parts = cleanAmount.split(".")
        val integerPart = parts[0].reversed().chunked(3).joinToString(",").reversed()
        val decimalPart = if (parts.size > 1) ".${parts[1].take(2)}" else ""
        val formattedNumber = integerPart + decimalPart

        return if (config.isSymbolPrefix) {
            "${config.symbol} $formattedNumber"
        } else {
            "$formattedNumber ${config.symbol}"
        }
    }
}