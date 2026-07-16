package com.hathway.pocketgoals.presentation.ui.components.transactions_components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AddCard
import androidx.compose.material.icons.rounded.AutoAwesome
import androidx.compose.material.icons.rounded.Restaurant
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hathway.pocketgoals.domain.Transaction
import com.hathway.pocketgoals.domain.TransactionType
import com.hathway.pocketgoals.domain.model.ThemeMode
import com.hathway.pocketgoals.presentation.ui.theme.PocketGoalsTheme
import com.hathway.pocketgoals.presentation.ui.theme.Primary
import com.hathway.pocketgoals.presentation.ui.theme.Tertiary
import org.jetbrains.compose.resources.stringResource
import pocketgoals.shared.generated.resources.Res
import pocketgoals.shared.generated.resources.goal_created

@Composable
fun TransactionListItem(transaction: Transaction, onClick: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(8.dp))
            .clickable(onClick = onClick).padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier.size(48.dp).background(
                MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f),
                RoundedCornerShape(12.dp)
            ), contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = transaction.icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(24.dp)
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = transaction.title,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = "${transaction.date} • ${transaction.time}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        Column(horizontalAlignment = Alignment.End) {
            Text(
                text = when (transaction.type) {
                    TransactionType.INCOME -> "+ ₹${transaction.amount.toInt()}"
                    TransactionType.EXPENSE -> "- ₹${transaction.amount.toInt()}"
                    TransactionType.GOAL_CREATED -> stringResource(Res.string.goal_created)
                }, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold,
                color = when (transaction.type) {
                    TransactionType.INCOME -> Primary
                    TransactionType.EXPENSE -> Tertiary
                    TransactionType.GOAL_CREATED -> MaterialTheme.colorScheme.primary
                }
            )
            Text(
                text = transaction.paymentMethod,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Preview(name = "Transactions Light Mode", showBackground = true, widthDp = 360)
@Composable
fun TransactionListItemLightPreview() {
    PocketGoalsTheme(themeMode = ThemeMode.LIGHT) {
        TransactionPreviewContainer()
    }
}

@Preview(name = "Transactions Dark Mode", showBackground = true, widthDp = 360)
@Composable
fun TransactionListItemDarkPreview() {
    PocketGoalsTheme(themeMode = ThemeMode.DARK) {
        TransactionPreviewContainer()
    }
}

@Composable
private fun TransactionPreviewContainer() {
    val mockTransactions = remember {
        listOf(
            Transaction(
                id = "1",
                title = "Monthly Salary",
                amount = 45000.0,
                type = TransactionType.INCOME,
                date = "16 July",
                time = "10:00 AM",
                category = "Salary",
                paymentMethod = "Bank Transfer",
                icon = Icons.Rounded.AddCard
            ), Transaction(
                id = "2",
                title = "Dinner with Friends",
                amount = 1250.0,
                type = TransactionType.EXPENSE,
                date = "15 July",
                time = "08:30 PM",
                category = "Food",
                paymentMethod = "Credit Card",
                icon = Icons.Rounded.Restaurant
            ), Transaction(
                id = "3",
                title = "Japan Trip Fund",
                amount = 0.0,
                type = TransactionType.GOAL_CREATED,
                date = "14 July",
                time = "02:15 PM",
                category = "Travel",
                paymentMethod = "Savings Account",
                icon = Icons.Rounded.AutoAwesome
            )
        )
    }

    Column(
        modifier = Modifier.fillMaxWidth().padding(16.dp)
    ) {
        mockTransactions.forEach { tx ->
            TransactionListItem(transaction = tx, onClick = {})
        }
    }
}