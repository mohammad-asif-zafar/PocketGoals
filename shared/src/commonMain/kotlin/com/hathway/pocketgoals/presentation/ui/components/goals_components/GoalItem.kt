package com.hathway.pocketgoals.presentation.ui.components.goals_components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hathway.pocketgoals.domain.Goal
import com.hathway.pocketgoals.domain.model.ThemeMode
import com.hathway.pocketgoals.domain.util.mockAmount.formatMockAmount
import com.hathway.pocketgoals.presentation.ui.theme.PocketGoalsTheme

@Composable
fun GoalItem(
    goal: Goal, onClick: () -> Unit, modifier: Modifier = Modifier
) {
    Card(modifier = modifier.fillMaxWidth().clip(RoundedCornerShape(16.dp)).clickable { onClick() }
        .border(
            width = 1.dp,
            color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f),
            shape = RoundedCornerShape(16.dp)
        ), shape = RoundedCornerShape(16.dp), colors = CardDefaults.cardColors(
        containerColor = MaterialTheme.colorScheme.surface
    ), elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)) {
        Row(
            modifier = Modifier.padding(16.dp).fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Icon Background frame using dynamic color blend opacity
            Box(
                modifier = Modifier.size(56.dp).clip(CircleShape)
                    .background(goal.color.copy(alpha = 0.15f)), contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = goal.icon,
                    contentDescription = null,
                    tint = goal.color,
                    modifier = Modifier.size(28.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = goal.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "₹${formatMockAmount(goal.savedAmount)} / ₹${formatMockAmount(goal.targetAmount)}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontWeight = FontWeight.Medium
                )

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    LinearProgressIndicator(
                        progress = { goal.progress },
                        modifier = Modifier.weight(1f).height(8.dp).clip(CircleShape),
                        color = goal.color, // Keeps visual theme color match across categories
                        trackColor = MaterialTheme.colorScheme.surfaceVariant
                    )

                    Text(
                        text = "${goal.percentage}%",
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }
    }
}

// Stub function to allow direct IDE parsing layout compilation
private fun formatMockAmount(amount: Double): String {
    return amount.toInt().toString()
}

// ==========================================================
// Theme-Safe Split Previews
// ==========================================================

@Preview
@Composable
fun GoalItemLightPreview() {
    val sampleGoal = Goal(
        name = "MacBook Pro",
        targetAmount = 150000.0,
        savedAmount = 90000.0,
        deadline = "2026-12-31",
        icon = Icons.Default.Star,
        color = Color(0xFF10B981)
    )

    PocketGoalsTheme(themeMode = ThemeMode.LIGHT) {
        Box(
            modifier = Modifier.background(MaterialTheme.colorScheme.background).padding(16.dp)
        ) {
            GoalItem(goal = sampleGoal, onClick = {})
        }
    }
}

@Preview
@Composable
fun GoalItemDarkPreview() {
    val sampleGoal = Goal(
        name = "MacBook Pro",
        targetAmount = 150000.0,
        savedAmount = 90000.0,
        deadline = "2026-12-31",
        icon = Icons.Default.Star,
        color = Color(0xFF10B981)
    )

    PocketGoalsTheme(themeMode = ThemeMode.DARK) {
        Box(
            modifier = Modifier.background(MaterialTheme.colorScheme.background).padding(16.dp)
        ) {
            GoalItem(goal = sampleGoal, onClick = {})
        }
    }
}
