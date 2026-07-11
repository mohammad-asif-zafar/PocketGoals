package com.hathway.pocketgoals.presentation.ui.components.goals_components

import androidx.compose.foundation.background
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
import androidx.compose.ui.unit.sp
import com.hathway.pocketgoals.domain.model.ThemeMode
import com.hathway.pocketgoals.presentation.ui.theme.PocketGoalsTheme


@Composable
fun GoalStatItem(
    label: String,
    value: String,
    valueColor: Color,
    modifier: Modifier = Modifier,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start
) {
    Column(
        modifier = modifier,
        horizontalAlignment = horizontalAlignment
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            // Replaced hardcoded Color.Gray to ensure perfect readability on dark canvases
            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f)
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodySmall,
            fontWeight = FontWeight.Bold,
            color = valueColor,
            fontSize = 11.sp
        )
    }
}

// ==========================================================
// Theme-Safe Split Previews
// ==========================================================

@Preview
@Composable
fun GoalStatItemsLightPreview() {
    PocketGoalsTheme(themeMode = ThemeMode.LIGHT) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            GoalStatItem("Need", "₹ 82,500", Color(0xFF7C3AED))
            GoalStatItem("Save", "₹ 7,500 /mo", Color(0xFF7C3AED))
            GoalStatItem("Deadline", "Dec 2024", MaterialTheme.colorScheme.onSurface)
        }
    }
}

@Preview
@Composable
fun GoalStatItemsDarkPreview() {
    PocketGoalsTheme(themeMode = ThemeMode.DARK) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            GoalStatItem("Need", "₹ 82,500", Color(0xFFA78BFA))
            GoalStatItem("Save", "₹ 7,500 /mo", Color(0xFFA78BFA))
            GoalStatItem("Deadline", "Dec 2024", MaterialTheme.colorScheme.onSurface)
        }
    }
}
