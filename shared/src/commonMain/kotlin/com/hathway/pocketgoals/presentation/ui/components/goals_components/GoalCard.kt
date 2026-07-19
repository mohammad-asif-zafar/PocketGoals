package com.hathway.pocketgoals.presentation.ui.components.goals_components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
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
import androidx.compose.material.icons.rounded.Flight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
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
import com.hathway.pocketgoals.presentation.ui.theme.PocketGoalsTheme

@Composable
fun GoalCard(
    goal: Goal, modifier: Modifier = Modifier
) {
    val isDark = isSystemInDarkTheme()

    // Combines surface with dynamic tone tinting for pristine dark mode card structures
    val cardBackground = MaterialTheme.colorScheme.surface
    val tintOverlay = goal.color.copy(alpha = if (isDark) 0.12f else 0.05f)
    val borderAlpha = if (isDark) 0.25f else 0.12f

    val bodyTextColor = MaterialTheme.colorScheme.onSurface
    val labelTextColor = MaterialTheme.colorScheme.onSurfaceVariant

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = cardBackground),
        border = BorderStroke(1.dp, goal.color.copy(alpha = borderAlpha))
    ) {
        // Overlay Box adds subtle tint without breaking native surface properties
        Box(modifier = Modifier.background(tintOverlay)) {
            Column(modifier = Modifier.padding(20.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier.size(60.dp).background(goal.color, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = goal.icon,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(32.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = goal.name,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = bodyTextColor
                            )
                            Text(
                                text = "${goal.percentage}%",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = goal.color
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column {
                                Text(
                                    text = "Target",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = labelTextColor
                                )
                                Text(
                                    text = " ${goal.targetAmount.toInt()}",
                                    style = MaterialTheme.typography.bodyMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = goal.color
                                )
                            }

                            Column(horizontalAlignment = Alignment.End) {
                                Text(
                                    text = "Saved",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = labelTextColor
                                )
                                Text(
                                    text = " ${goal.savedAmount.toInt()}",
                                    style = MaterialTheme.typography.bodyMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = goal.color
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                LinearProgressIndicator(
                    progress = { goal.progress },
                    modifier = Modifier.fillMaxWidth().height(10.dp).clip(CircleShape),
                    color = goal.color,
                    trackColor = goal.color.copy(alpha = if (isDark) 0.2f else 0.1f)
                )

                Spacer(modifier = Modifier.height(20.dp))

                HorizontalDivider(color = goal.color.copy(alpha = borderAlpha))

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    GoalStatItem("Need", "${goal.remainingAmount.toInt()}", goal.color)
                    GoalStatItem("Save", "${goal.monthlySavingNeeded.toInt()} /mo", goal.color)
                    GoalStatItem("Deadline", goal.deadline, bodyTextColor, modifier= Modifier)
                }
            }
        }
    }
}


// ==========================================================
// Theme-Safe Split Previews
// ==========================================================

@Preview
@Composable
fun GoalCardLightPreview() {
    val mockGoal = Goal(
        name = "Europe Trip",
        icon = Icons.Rounded.Flight,
        color = Color(0xFF7C3AED),
        targetAmount = 150000.0,
        savedAmount = 67500.0,
        deadline = "Dec 2024",
    )
    PocketGoalsTheme(ThemeMode.LIGHT) {
        Box(modifier = Modifier.padding(16.dp)) {
            GoalCard(goal = mockGoal)
        }
    }
}

@Preview
@Composable
fun GoalCardDarkPreview() {
    val mockGoal = Goal(
        name = "Europe Trip",
        icon = Icons.Rounded.Flight,
        color = Color(0xFFA78BFA),
        targetAmount = 150000.0,
        savedAmount = 67500.0,
        deadline = "Dec 2024",
    )
    PocketGoalsTheme(ThemeMode.DARK) {
        Box(modifier = Modifier.padding(16.dp)) {
            GoalCard(goal = mockGoal)
        }
    }
}