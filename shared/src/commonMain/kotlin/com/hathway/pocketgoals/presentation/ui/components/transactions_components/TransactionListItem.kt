package com.hathway.pocketgoals.presentation.ui.components.transactions_components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.hathway.pocketgoals.domain.Transaction
import com.hathway.pocketgoals.domain.TransactionType
import org.jetbrains.compose.resources.stringResource
import pocketgoals.shared.generated.resources.*

@Composable
fun TransactionListItem(transaction: Transaction, onClick: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth().clickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier.size(48.dp).background(MaterialTheme.colorScheme.surfaceVariant, RoundedCornerShape(12.dp)),
            contentAlignment = Alignment.Center
        ) {
            Icon(transaction.icon, null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(24.dp))
        }
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(transaction.title, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.SemiBold)
            Text("${transaction.date} • ${transaction.time}", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
        Column(horizontalAlignment = Alignment.End) {
            Text(
                text = when (transaction.type) {
                    TransactionType.INCOME -> "+ ₹${transaction.amount.toInt()}"
                    TransactionType.EXPENSE -> "- ₹${transaction.amount.toInt()}"
                    TransactionType.GOAL_CREATED -> stringResource(Res.string.goal_created)
                },
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
                color = when (transaction.type) {
                    TransactionType.INCOME -> Color(0xFF10B981)
                    TransactionType.EXPENSE -> Color(0xFFEF4444)
                    TransactionType.GOAL_CREATED -> MaterialTheme.colorScheme.primary
                }
            )
            Text(transaction.paymentMethod, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}
