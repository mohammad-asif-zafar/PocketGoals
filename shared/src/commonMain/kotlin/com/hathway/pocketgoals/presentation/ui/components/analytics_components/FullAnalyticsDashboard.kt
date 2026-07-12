package com.hathway.pocketgoals.presentation.ui.components.analytics_components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hathway.pocketgoals.domain.model.ThemeMode
import com.hathway.pocketgoals.presentation.ui.theme.Danger
import com.hathway.pocketgoals.presentation.ui.theme.PocketGoalsTheme
import com.hathway.pocketgoals.presentation.ui.theme.Success
import org.jetbrains.compose.resources.stringResource
import pocketgoals.shared.generated.resources.Res
import pocketgoals.shared.generated.resources.analytics_breakdown
import pocketgoals.shared.generated.resources.analytics_title
import pocketgoals.shared.generated.resources.expense
import pocketgoals.shared.generated.resources.income
import pocketgoals.shared.generated.resources.period_this_month
import pocketgoals.shared.generated.resources.period_this_week
import pocketgoals.shared.generated.resources.period_this_year
import pocketgoals.shared.generated.resources.total

// Data representation structural definitions
data class DonutPieDataPoint(val name: String, val percentage: Float, val color: Color)

@Composable
fun FullAnalyticsDashboard(
    barData: List<BarChartDataPoint>,
    donutData: List<DonutPieDataPoint>,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    val periods = listOf(
        stringResource(Res.string.period_this_week),
        stringResource(Res.string.period_this_month),
        stringResource(Res.string.period_this_year)
    )
    var selectedPeriod by remember { mutableStateOf(periods[1]) }

    // Toggle state: 0 = Income, 1 = Expense
    var activeToggleIdx by remember { mutableStateOf(0) }
    val activeColor = if (activeToggleIdx == 0) Success else Danger

    Column(
        modifier = modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        // ==========================================================
        // 1. DYNAMIC BAR GRAPH WITH INCOME/EXPENSE SWITCHER
        // ==========================================================
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                // Header Panel Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(Res.string.analytics_title),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Box {
                        Surface(
                            modifier = Modifier.clip(RoundedCornerShape(12.dp))
                                .clickable { expanded = true },
                            shape = RoundedCornerShape(12.dp),
                            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
                            color = MaterialTheme.colorScheme.surfaceVariant
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = selectedPeriod,
                                    style = MaterialTheme.typography.labelMedium,
                                    fontWeight = FontWeight.SemiBold,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Icon(
                                    imageVector = Icons.Rounded.KeyboardArrowDown,
                                    contentDescription = null,
                                    modifier = Modifier.size(16.dp),
                                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                            periods.forEach { period ->
                                DropdownMenuItem(
                                    text = { Text(period) },
                                    onClick = { selectedPeriod = period; expanded = false })
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Income / Expense Interactive Sub-Tab Toggle Bar
                Row(
                    modifier = Modifier.fillMaxWidth().height(36.dp).clip(RoundedCornerShape(8.dp))
                        .background(MaterialTheme.colorScheme.surfaceVariant).padding(2.dp)
                ) {
                    listOf(
                        stringResource(Res.string.income), stringResource(Res.string.expense)
                    ).forEachIndexed { idx, label ->
                        val isSelected = activeToggleIdx == idx
                        Box(
                            modifier = Modifier.weight(1f).fillMaxHeight()
                                .clip(RoundedCornerShape(6.dp))
                                .background(if (isSelected) activeColor else Color.Transparent)
                                .clickable { activeToggleIdx = idx },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = label,
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.Bold,
                                color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Animated Color Bar Chart Canvas Elements
                val maxValue = barData.maxOfOrNull { it.value } ?: 1f
                Row(
                    modifier = Modifier.fillMaxWidth().height(140.dp).padding(horizontal = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Bottom
                ) {
                    barData.forEach { dataPoint ->
                        val normalizedProgress =
                            if (maxValue > 0) dataPoint.value / maxValue else 0f
                        var triggerAnimation by remember { mutableStateOf(false) }
                        LaunchedEffect(barData) { triggerAnimation = true }

                        val animatedHeight by animateFloatAsState(
                            targetValue = if (triggerAnimation) normalizedProgress else 0f,
                            animationSpec = tween(600)
                        )
                        val animatedBarColor by animateColorAsState(targetValue = activeColor)

                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.weight(1f)
                        ) {
                            Box(
                                modifier = Modifier.weight(1f).fillMaxWidth(),
                                contentAlignment = Alignment.BottomCenter
                            ) {
                                Box(
                                    modifier = Modifier.fillMaxWidth(0.4f)
                                        .fillMaxHeight(animatedHeight)
                                        .clip(RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp))
                                        .background(animatedBarColor)
                                )
                            }
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = dataPoint.label,
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }
        }

        // ==========================================================
        // 2. ADVANCED CATEGORY DONUT PIE CHART
        // ==========================================================
        Card(
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = stringResource(Res.string.analytics_breakdown),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(bottom = 24.dp)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // ==========================================================
                    // Left Side: Vector Donut Drawing Space Canvas
                    // ==========================================================
                    var animateDonut by remember { mutableStateOf(false) }
                    LaunchedEffect(donutData) { animateDonut = true }

                    val donutProgress by animateFloatAsState(
                        targetValue = if (animateDonut) 360f else 0f, animationSpec = tween(1000)
                    )

                    Box(
                        modifier = Modifier.size(130.dp), contentAlignment = Alignment.Center
                    ) {
                        Canvas(modifier = Modifier.size(120.dp)) {
                            var startAngle = -90f
                            donutData.forEach { dataPoint ->
                                val sweepAngle = (dataPoint.percentage / 100f) * donutProgress
                                drawArc(
                                    color = dataPoint.color,
                                    startAngle = startAngle,
                                    sweepAngle = sweepAngle,
                                    useCenter = false,
                                    style = Stroke(width = 14.dp.toPx(), cap = StrokeCap.Round)
                                )
                                startAngle += (dataPoint.percentage / 100f) * 360f
                            }
                        }

                        // Center Info Callout text details overlay
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = stringResource(Res.string.total),
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Text(
                                text = "100%",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(24.dp))

                    // ==========================================================
                    // Right Side: Dynamic Alignment Legend Metadata Columns
                    // ==========================================================
                    Column(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        donutData.forEach { item ->
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Box(
                                        modifier = Modifier.size(10.dp)
                                            .background(item.color, CircleShape)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = item.name,
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = MaterialTheme.colorScheme.onSurface,
                                        fontWeight = FontWeight.Medium
                                    )
                                }
                                Text(
                                    text = "${item.percentage.toInt()}%",
                                    style = MaterialTheme.typography.bodyMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

val previewBars = listOf(
    BarChartDataPoint("Mon", 1400f),
    BarChartDataPoint("Tue", 900f),
    BarChartDataPoint("Wed", 2800f),
    BarChartDataPoint("Thu", 1500f),
    BarChartDataPoint("Fri", 2200f),
    BarChartDataPoint("Sat", 600f),
    BarChartDataPoint("Sun", 1200f)
)

val previewDonut = listOf(
    DonutPieDataPoint("Food", 45f, Color(0xFFEF4444)),
    DonutPieDataPoint("Shopping", 30f, Color(0xFF3B82F6)),
    DonutPieDataPoint("Transport", 15f, Color(0xFFF59E0B)),
    DonutPieDataPoint("Bills", 10f, Color(0xFF10B981))
)

@Preview
@Composable
private fun FullAnalyticsModuleLightPreview() {
    PocketGoalsTheme(themeMode = ThemeMode.LIGHT) {
        Surface(color = MaterialTheme.colorScheme.background, modifier = Modifier.padding(16.dp)) {
            FullAnalyticsDashboard(barData = previewBars, donutData = previewDonut)
        }
    }
}

@Preview
@Composable
private fun FullAnalyticsModuleDarkPreview() {
    PocketGoalsTheme(themeMode = ThemeMode.DARK) {
        Surface(color = MaterialTheme.colorScheme.background, modifier = Modifier.padding(16.dp)) {
            FullAnalyticsDashboard(barData = previewBars, donutData = previewDonut)
        }
    }
}