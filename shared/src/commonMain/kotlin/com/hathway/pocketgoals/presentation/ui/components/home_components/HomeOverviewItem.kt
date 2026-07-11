package com.hathway.pocketgoals.presentation.ui.components.home_components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowDownward
import androidx.compose.material.icons.rounded.ArrowUpward
import androidx.compose.material3.Icon
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
import com.hathway.pocketgoals.presentation.ui.theme.PocketGoalsTheme

@Composable
fun HomeOverviewItem(
    label: String,
    amount: String,
    color: Color,
    isUpTrend: Boolean? = null // Nullable flag: true = up, false = down, null = hidden
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            // Render indicator icon only if trend direction data is passed explicitly
            if (isUpTrend != null) {
                Icon(
                    imageVector = if (isUpTrend) Icons.Rounded.ArrowUpward else Icons.Rounded.ArrowDownward,
                    contentDescription = if (isUpTrend) "Upward Trend" else "Downward Trend",
                    tint = color,
                    modifier = Modifier.size(14.dp)
                )
                Spacer(modifier = Modifier.width(2.dp))
            }

            Text(
                text = "₹$amount",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                color = color
            )
        }
    }
}

@Preview
@Composable
fun HomeOverviewItemUpPreview() {
    PocketGoalsTheme(themeMode = ThemeMode.LIGHT) {
        Box(modifier = Modifier.background(MaterialTheme.colorScheme.background).padding(16.dp)) {
            HomeOverviewItem(
                label = "Income",
                amount = "2,00,000",
                color = Color(0xFF10B981),
                isUpTrend = true
            )
        }
    }
}

@Preview
@Composable
fun HomeOverviewItemDownPreview() {
    PocketGoalsTheme(themeMode = ThemeMode.DARK) {
        Box(modifier = Modifier.background(MaterialTheme.colorScheme.background).padding(16.dp)) {
            HomeOverviewItem(
                label = "Expenses",
                amount = "55,000",
                color = Color(0xFFEF4444),
                isUpTrend = false
            )
        }
    }
}
