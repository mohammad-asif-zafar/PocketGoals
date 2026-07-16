package com.hathway.pocketgoals.presentation.ui.components.transactions_components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hathway.pocketgoals.domain.model.ThemeMode
import com.hathway.pocketgoals.presentation.ui.theme.Danger
import com.hathway.pocketgoals.presentation.ui.theme.PocketGoalsTheme
import com.hathway.pocketgoals.presentation.ui.theme.Success

@Composable
fun TransactionSummaryItem(label: String, amount: String, color: Color) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            label,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            "₹$amount",
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.Bold,
            color = color
        )
    }
}

@Preview(name = "Summary Light Mode", showBackground = true, widthDp = 360)
@Composable
fun TransactionSummaryItemLightPreview() {
    PocketGoalsTheme(themeMode = ThemeMode.LIGHT) {
        SummaryPreviewRow()
    }
}

@Preview(name = "Summary Dark Mode", showBackground = true, widthDp = 360)
@Composable
fun TransactionSummaryItemDarkPreview() {
    PocketGoalsTheme(themeMode = ThemeMode.DARK) {
        SummaryPreviewRow()
    }
}

@Composable
private fun SummaryPreviewRow() {
    Row(
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        // Total Income Widget - utilizing dynamic theme success token
        TransactionSummaryItem(
            label = "Income", amount = "54,200", color = Success
        )

        // Total Expense Widget - utilizing dynamic theme danger token
        TransactionSummaryItem(
            label = "Expenses", amount = "12,850", color = Danger
        )
    }
}