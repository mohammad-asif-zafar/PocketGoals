package com.hathway.pocketgoals.presentation.ui.components.add_expense_components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.icons.rounded.AccountBalanceWallet
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.material.icons.rounded.TrendingUp
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hathway.pocketgoals.domain.model.ThemeMode
import com.hathway.pocketgoals.presentation.ui.theme.Info
import com.hathway.pocketgoals.presentation.ui.theme.PocketGoalsTheme
import com.hathway.pocketgoals.presentation.ui.theme.Success

@Composable
fun SummaryItemRow(
    icon: ImageVector, label: String, value: String, customIconColor: Color? = null
) {
    val baseIconColor = customIconColor ?: MaterialTheme.colorScheme.primary
    val iconContainerBg = baseIconColor.copy(alpha = 0.1f)

    Row(
        modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier.size(48.dp).clip(RoundedCornerShape(12.dp))
                .background(iconContainerBg), contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = baseIconColor,
                modifier = Modifier.size(24.dp)
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
            )
            Text(
                text = value,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Preview(name = "Row Light Mode", showBackground = true, widthDp = 360)
@Composable
fun SummaryItemRowLightPreview() {
    PocketGoalsTheme(themeMode = ThemeMode.LIGHT) {
        SummaryItemRowPreviewContent()
    }
}

@Preview(name = "Row Dark Mode", showBackground = true, widthDp = 360)
@Composable
fun SummaryItemRowDarkPreview() {
    PocketGoalsTheme(themeMode = ThemeMode.DARK) {
        SummaryItemRowPreviewContent()
    }
}

@Composable
private fun SummaryItemRowPreviewContent() {
    Column(
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Example 1: Standard theme primary icon coloring
        SummaryItemRow(
            icon = Icons.Rounded.AccountBalanceWallet, label = "Total Balance", value = "₹45,250.00"
        )

        // Example 2: Override with dynamic success theme token
        SummaryItemRow(
            icon = Icons.Rounded.TrendingUp,
            label = "Monthly Growth",
            value = "+12.4%",
            customIconColor = Success
        )

        // Example 3: Override with custom workflow info token
        SummaryItemRow(
            icon = Icons.Rounded.Notifications,
            label = "Linked Goals",
            value = "3 Active Goals",
            customIconColor = Info
        )
    }
}
