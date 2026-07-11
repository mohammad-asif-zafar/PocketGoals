package com.hathway.pocketgoals.presentation.ui.components.analytics_components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.background
import androidx.compose.ui.tooling.preview.Preview
import com.hathway.pocketgoals.domain.model.ThemeMode
import com.hathway.pocketgoals.presentation.ui.theme.PocketGoalsTheme

@Composable
fun AnalyticsHeader(
    selectedPeriod: String = "This Month",
    onPeriodClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Analytics",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground // Adapts text color safely
        )
        Surface(
            modifier = Modifier.clickable { onPeriodClick() },
            shape = RoundedCornerShape(12.dp),
            // Dynamically updates border and surface background using your defined theme tokens
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
            color = MaterialTheme.colorScheme.surfaceVariant
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = selectedPeriod,
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant // Matches container content spec
                )
                Spacer(modifier = Modifier.width(4.dp))
                Icon(
                    imageVector = Icons.Rounded.KeyboardArrowDown,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp),
                    tint = MaterialTheme.colorScheme.onSurfaceVariant // Tints icon dynamically
                )
            }
        }
    }
}

@Preview
@Composable
fun AnalyticsHeaderLightPreview() {
    PocketGoalsTheme(themeMode = ThemeMode.LIGHT) {
        Box(modifier = Modifier.background(MaterialTheme.colorScheme.background).padding(16.dp)) {
            AnalyticsHeader(
                selectedPeriod = "This Month",
                onPeriodClick = {}
            )
        }
    }
}

@Preview
@Composable
fun AnalyticsHeaderDarkPreview() {
    PocketGoalsTheme(themeMode = ThemeMode.DARK) {
        Box(modifier = Modifier.background(MaterialTheme.colorScheme.background).padding(16.dp)) {
            AnalyticsHeader(
                selectedPeriod = "This Month",
                onPeriodClick = {}
            )
        }
    }
}
