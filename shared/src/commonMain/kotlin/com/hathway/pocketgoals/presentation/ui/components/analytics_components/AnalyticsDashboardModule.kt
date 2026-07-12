package com.hathway.pocketgoals.presentation.ui.components.analytics_components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hathway.pocketgoals.domain.model.ThemeMode
import com.hathway.pocketgoals.presentation.ui.theme.PocketGoalsTheme
import org.jetbrains.compose.resources.stringResource
import pocketgoals.shared.generated.resources.Res
import pocketgoals.shared.generated.resources.analytics_title
import pocketgoals.shared.generated.resources.period_this_month
import pocketgoals.shared.generated.resources.period_this_week
import pocketgoals.shared.generated.resources.period_this_year


// Data representation structural definitions for chart items
data class BarChartDataPoint(val label: String, val value: Float)

@Composable
fun AnalyticsDashboardModule(
    chartData: List<BarChartDataPoint>, modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    val periods = listOf(
        stringResource(Res.string.period_this_week),
        stringResource(Res.string.period_this_month),
        stringResource(Res.string.period_this_year)
    )
    var selectedPeriod by remember { mutableStateOf(periods[1]) }

    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            // 1. Header with integrated DropdownMenu Layout Selection
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
                                contentDescription = "Toggle Time Period Dropdown Menu Options",
                                modifier = Modifier.size(16.dp),
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }

                    DropdownMenu(
                        expanded = expanded, onDismissRequest = { expanded = false }) {
                        periods.forEach { period ->
                            DropdownMenuItem(text = {
                                Text(
                                    period, style = MaterialTheme.typography.bodyMedium
                                )
                            }, onClick = {
                                selectedPeriod = period
                                expanded = false
                            })
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // 2. Bar Graph Visualization Component Layout Render
            val maxValue = chartData.maxOfOrNull { it.value } ?: 1f

            Row(
                modifier = Modifier.fillMaxWidth().height(160.dp).padding(horizontal = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                chartData.forEach { dataPoint ->
                    val normalizedProgress = if (maxValue > 0) dataPoint.value / maxValue else 0f

                    // Trigger dynamic bar rise animation layout mapping frames on attachment lifecycle
                    var triggerAnimation by remember { mutableStateOf(false) }
                    LaunchedEffect(chartData) { triggerAnimation = true }

                    val animatedBarHeight by animateFloatAsState(
                        targetValue = if (triggerAnimation) normalizedProgress else 0f,
                        animationSpec = tween(durationMillis = 800)
                    )

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.weight(1f)
                    ) {
                        Box(
                            modifier = Modifier.weight(1f).fillMaxWidth(),
                            contentAlignment = Alignment.BottomCenter
                        ) {
                            Box(
                                modifier = Modifier.fillMaxWidth(0.45f) // Controls the relative individual visual bar fat width thickness layout ratio fraction scale
                                    .fillMaxHeight(animatedBarHeight)
                                    .clip(RoundedCornerShape(topStart = 6.dp, topEnd = 6.dp))
                                    .background(MaterialTheme.colorScheme.primary)
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = dataPoint.label,
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}

private val sampleAnalyticsData = listOf(
    BarChartDataPoint("Mon", 1200f),
    BarChartDataPoint("Tue", 800f),
    BarChartDataPoint("Wed", 2400f),
    BarChartDataPoint("Thu", 1600f),
    BarChartDataPoint("Fri", 1900f),
    BarChartDataPoint("Sat", 500f),
    BarChartDataPoint("Sun", 1100f)
)

@Preview
@Composable
private fun AnalyticsModuleLightPreview() {
    PocketGoalsTheme(themeMode = ThemeMode.LIGHT) {
        Surface(color = MaterialTheme.colorScheme.background, modifier = Modifier.padding(16.dp)) {
            AnalyticsDashboardModule(chartData = sampleAnalyticsData)
        }
    }
}

@Preview
@Composable
private fun AnalyticsModuleDarkPreview() {
    PocketGoalsTheme(themeMode = ThemeMode.DARK) {
        Surface(color = MaterialTheme.colorScheme.background, modifier = Modifier.padding(16.dp)) {
            AnalyticsDashboardModule(chartData = sampleAnalyticsData)
        }
    }
}