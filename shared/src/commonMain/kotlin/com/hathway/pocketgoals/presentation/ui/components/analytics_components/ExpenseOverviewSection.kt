package com.hathway.pocketgoals.presentation.ui.components.analytics_components

import androidx.compose.foundation.Canvas
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hathway.pocketgoals.domain.model.ThemeMode
import com.hathway.pocketgoals.presentation.ui.theme.PocketGoalsTheme

@Composable
fun ExpenseOverviewSection(totalAmount: String = "₹90,000") {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Expense Overview",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(vertical = 16.dp),
            color = MaterialTheme.colorScheme.onBackground // Adapts header text to theme
        )

        Row(
            modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
        ) {
            // Donut Chart
            Box(
                modifier = Modifier.size(150.dp), contentAlignment = Alignment.Center
            ) {
                val data = listOf(
                    34.47f to Color(0xFF3B82F6),
                    27.81f to Color(0xFF10B981),
                    15.11f to Color(0xFFF59E0B),
                    9.27f to Color(0xFF8B5CF6),
                    5.34f to Color(0xFFEF4444),
                    4.29f to Color(0xFF64748B),
                    3.71f to Color(0xFF6366F1)
                )

                Canvas(modifier = Modifier.size(140.dp)) {
                    var startAngle = -90f
                    data.forEach { (percent, color) ->
                        val sweepAngle = (percent / 100f) * 360f
                        drawArc(
                            color = color,
                            startAngle = startAngle,
                            sweepAngle = sweepAngle,
                            useCenter = false,
                            style = Stroke(width = 25.dp.toPx(), cap = StrokeCap.Butt)
                        )
                        startAngle += sweepAngle
                    }
                }

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = totalAmount,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground // Dark/Light adaptive amount
                    )
                    Text(
                        text = "Total",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant // Replaced hardcoded Color.Gray
                    )
                }
            }

            Spacer(modifier = Modifier.width(24.dp))

            // Legend
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                LegendItem("Investment", "34.47%", Color(0xFF3B82F6))
                LegendItem("Basic Needs", "27.81%", Color(0xFF10B981))
                LegendItem("Future Pay", "15.11%", Color(0xFFF59E0B))
                LegendItem("Family Support", "9.27%", Color(0xFF8B5CF6))
                LegendItem("Want/Desire", "5.34%", Color(0xFFEF4444))
                LegendItem("Buffer", "4.29%", Color(0xFF64748B))
                LegendItem("Others", "3.71%", Color(0xFF6366F1))
            }
        }
    }
}

@Preview
@Composable
fun ExpenseOverviewSectionLightPreview() {
    PocketGoalsTheme(themeMode = ThemeMode.LIGHT) {
        Box(modifier = Modifier.background(MaterialTheme.colorScheme.background).padding(16.dp)) {
            ExpenseOverviewSection()
        }
    }
}

@Preview
@Composable
fun ExpenseOverviewSectionDarkPreview() {
    PocketGoalsTheme(themeMode = ThemeMode.DARK) {
        Box(modifier = Modifier.background(MaterialTheme.colorScheme.background).padding(16.dp)) {
            ExpenseOverviewSection()
        }
    }
}
