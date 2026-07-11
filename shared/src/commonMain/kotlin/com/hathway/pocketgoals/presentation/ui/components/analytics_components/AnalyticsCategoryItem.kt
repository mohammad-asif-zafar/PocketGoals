package com.hathway.pocketgoals.presentation.ui.components.analytics_components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hathway.pocketgoals.presentation.ui.theme.PocketGoalsTheme
import androidx.compose.material.icons.filled.ShoppingCart // Example icon for preview
import com.hathway.pocketgoals.domain.model.ThemeMode

@Composable
fun AnalyticsCategoryItem(name: String, value: String, color: Color, icon: ImageVector) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier.size(40.dp)
                .background(color.copy(alpha = 0.1f), RoundedCornerShape(8.dp)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = color,
                modifier = Modifier.size(20.dp)
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        Text(
            text = name,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.weight(1f),
            color = MaterialTheme.colorScheme.onBackground // Flipped to follow theme
        )

        Text(
            text = value,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant // Replaced hardcoded Color.DarkGray
        )

        Spacer(modifier = Modifier.width(8.dp))

        Icon(
            imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowRight,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f), // Replaced hardcoded Color.Gray
            modifier = Modifier.size(16.dp)
        )
    }
}

@Preview
@Composable
fun AnalyticsCategoryItemLightPreview() {
    PocketGoalsTheme(themeMode = ThemeMode.LIGHT) {
        Box(modifier = Modifier.background(MaterialTheme.colorScheme.background).padding(16.dp)) {
            AnalyticsCategoryItem(
                name = "Shopping",
                value = "$120.50",
                color = Color(0xFFFF5722),
                icon = Icons.Default.ShoppingCart
            )
        }
    }
}

@Preview
@Composable
fun AnalyticsCategoryItemDarkPreview() {
    PocketGoalsTheme(themeMode = ThemeMode.DARK) {
        Box(modifier = Modifier.background(MaterialTheme.colorScheme.background).padding(16.dp)) {
            AnalyticsCategoryItem(
                name = "Shopping",
                value = "$120.50",
                color = Color(0xFFFF5722),
                icon = Icons.Default.ShoppingCart
            )
        }
    }
}
