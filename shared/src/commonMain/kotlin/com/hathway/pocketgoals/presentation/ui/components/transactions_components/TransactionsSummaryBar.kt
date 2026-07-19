package com.hathway.pocketgoals.presentation.ui.components.transactions_components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.hathway.pocketgoals.presentation.ui.localization.CurrencyConfig
import com.hathway.pocketgoals.presentation.ui.localization.CurrencyFormatter

@Composable
fun TransactionsSummaryBar(
    income: String,
    expense: String,
    savings: String,
    currencyConfig: CurrencyConfig = CurrencyConfig.fromSystemLocale()
) {

    Card(
        modifier = Modifier.padding(horizontal = 16.dp).fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier = Modifier.padding(12.dp).fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TransactionSummaryItem("Total Income",   CurrencyFormatter.formatAmount(income, currencyConfig), Color(0xFF10B981))
            TransactionSummaryItem("Total Expense",   CurrencyFormatter.formatAmount(expense, currencyConfig), Color(0xFFEF4444))
            TransactionSummaryItem("Net Savings",   CurrencyFormatter.formatAmount(savings, currencyConfig), Color(0xFF3B82F6))
        }
    }
}
