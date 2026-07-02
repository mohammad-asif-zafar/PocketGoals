package com.hathway.pocketgoals.presentation.ui.components.goals_components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import com.hathway.pocketgoals.domain.util.mockAmount.formatMockAmount
import com.hathway.pocketgoals.presentation.ui.theme.PocketGoalsTheme

@Composable
fun GoalItem(goal: Goal, onClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth()
            // Dynamic border color using outline variant or a subtle custom combination
            .border(
                1.dp,
                MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f),
                RoundedCornerShape(16.dp)
            ), shape = RoundedCornerShape(16.dp),
        // Dynamic card container color using surface tokens
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ), elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp).fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Icon Background
            Box(
                modifier = Modifier.size(56.dp).clip(CircleShape)
                    // Keeps goal's custom color but safely blends it over the current surface
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
                    // Uses dynamic onSurface text color (Dark color in light mode, Light color in dark mode)
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "₹${formatMockAmount(goal.savedAmount)} / ₹${formatMockAmount(goal.targetAmount)}",
                    style = MaterialTheme.typography.bodySmall,
                    // Uses secondary text token instead of hardcoded Color.DarkGray
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
                        // Primary or Tertiary theme color accentuates progress beautifully across light/dark
                        color = MaterialTheme.colorScheme.primary,
                        // Track automatically dims/brightens with surface variants
                        trackColor = MaterialTheme.colorScheme.surfaceVariant
                    )

                    Text(
                        text = "${goal.percentage}%",
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.Bold,
                        // Uses primary/onSurface color text to guarantee visibility contrast
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun GoalItemLightPreview() {
    val sampleGoal = Goal(
        id = "1",
        name = "MacBook Pro",
        targetAmount = 150000.0,
        savedAmount = 90000.0,
        deadline = "2026-12-31",
        icon = Icons.Default.Star,
        color = Color(0xFF10B981) // Green accent color
    )

    PocketGoalsTheme(darkTheme = false) {
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
        id = "1",
        name = "MacBook Pro",
        targetAmount = 150000.0,
        savedAmount = 90000.0,
        deadline = "2026-12-31",
        icon = Icons.Default.Star,
        color = Color(0xFF10B981)
    )

    PocketGoalsTheme(darkTheme = true) {
        Box(
            modifier = Modifier.background(MaterialTheme.colorScheme.background).padding(16.dp)
        ) {
            GoalItem(goal = sampleGoal, onClick = {})
        }
    }
}
