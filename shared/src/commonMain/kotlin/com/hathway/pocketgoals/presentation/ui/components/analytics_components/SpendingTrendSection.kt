package com.hathway.pocketgoals.presentation.ui.components.analytics_components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.hathway.pocketgoals.domain.model.ThemeMode
import com.hathway.pocketgoals.presentation.ui.theme.PocketGoalsTheme


@Composable
fun SpendingTrendSection(
    sectionTitle: String,              // e.g., stringResource(Res.string.spending_trend)
    points: List<Float>,               // Dynamic float entries (0.0f to 1.0f)
    peakValueLabel: String,            // Pre-formatted highest value (e.g., "$12,450" or "١٢،٤٥٠")
    xAxisLabels: List<String>,         // Pre-formatted dates aligned to your active language
    modifier: Modifier = Modifier,
    lineColor: Color = MaterialTheme.colorScheme.primary // Matches your active Light/Dark theme color
) {
    val gridAndBorderColor = MaterialTheme.colorScheme.outlineVariant
    val labelColor = MaterialTheme.colorScheme.onSurfaceVariant
    val backgroundColor = MaterialTheme.colorScheme.background
    val density = LocalDensity.current

    var canvasSize by remember { mutableStateOf(IntSize.Zero) }

    // Dynamically calculate the highest point index based on the input list
    val maxPointIndex = remember(points) { points.indexOfMax() ?: -1 }

    Column(
        modifier = modifier.fillMaxWidth()
            .border(1.dp, gridAndBorderColor, RoundedCornerShape(16.dp)).padding(16.dp)
    ) {
        Text(
            text = sectionTitle,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(16.dp))

        Box(modifier = Modifier.fillMaxWidth().height(150.dp)) {
            Canvas(
                modifier = Modifier.fillMaxSize().onSizeChanged { canvasSize = it }) {
                val width = size.width
                val height = size.height

                // Protect layout math against division-by-zero or empty inputs
                val totalSteps = if (points.size > 1) points.size - 1 else 1
                val stepX = width / totalSteps

                // Draw Grid Lines
                for (i in 0..3) {
                    val y = height - (i * height / 3)
                    drawLine(
                        color = gridAndBorderColor,
                        start = Offset(0f, y),
                        end = Offset(width, y),
                        strokeWidth = 1.dp.toPx()
                    )
                }

                // Draw the Vertical Indicator Dash-Line for the highest peak
                if (maxPointIndex != -1 && points.isNotEmpty()) {
                    val maxTargetX = maxPointIndex * stepX
                    val maxTargetY = height - (points[maxPointIndex].coerceIn(0f, 1f) * height)

                    drawLine(
                        color = gridAndBorderColor.copy(alpha = 0.8f),
                        start = Offset(maxTargetX, maxTargetY),
                        end = Offset(maxTargetX, height),
                        strokeWidth = 1.5.dp.toPx(),
                        pathEffect = PathEffect.dashPathEffect(
                            intervals = floatArrayOf(10f, 10f), phase = 0f
                        )
                    )
                }

                // Build Paths for Smooth Curves
                val trendPath = Path()
                val fillPath = Path()

                if (points.isNotEmpty()) {
                    val startX = 0f
                    val startY = height - (points[0].coerceIn(0f, 1f) * height)

                    trendPath.moveTo(startX, startY)
                    fillPath.moveTo(startX, height)
                    fillPath.lineTo(startX, startY)

                    for (i in 0 until points.size - 1) {
                        val currentX = i * stepX
                        val currentY = height - (points[i].coerceIn(0f, 1f) * height)
                        val nextX = (i + 1) * stepX
                        val nextY = height - (points[i + 1].coerceIn(0f, 1f) * height)

                        val controlX1 = currentX + (stepX / 2f)
                        val controlY1 = currentY
                        val controlX2 = currentX + (stepX / 2f)
                        val controlY2 = nextY

                        trendPath.cubicTo(controlX1, controlY1, controlX2, controlY2, nextX, nextY)
                        fillPath.cubicTo(controlX1, controlY1, controlX2, controlY2, nextX, nextY)
                    }

                    val endX = (points.size - 1) * stepX
                    fillPath.lineTo(endX, height)
                    fillPath.close()
                }

                if (points.isNotEmpty()) {
                    // Draw Premium Dynamic Gradient Fill
                    drawPath(
                        path = fillPath, brush = Brush.verticalGradient(
                            colors = listOf(
                                lineColor.copy(alpha = 0.25f), backgroundColor.copy(alpha = 0.0f)
                            ), startY = 0f, endY = height
                        )
                    )

                    // Draw Smooth Stroke Line
                    drawPath(
                        path = trendPath,
                        color = lineColor,
                        style = Stroke(width = 3.dp.toPx(), cap = StrokeCap.Round)
                    )
                }

                // Draw Points
                points.forEachIndexed { index, p ->
                    val x = index * stepX
                    val y = height - (p.coerceIn(0f, 1f) * height)
                    val isMax = index == maxPointIndex
                    drawCircle(
                        color = lineColor,
                        radius = if (isMax) 5.5.dp.toPx() else 4.dp.toPx(),
                        center = Offset(x, y)
                    )
                }
            }

            // Floating Tooltip Overlay Rendering Layer
            if (maxPointIndex != -1 && canvasSize != IntSize.Zero && points.isNotEmpty()) {
                val totalSteps = if (points.size > 1) points.size - 1 else 1
                val stepX = canvasSize.width.toFloat() / totalSteps
                val peakX = maxPointIndex * stepX
                val peakY = canvasSize.height.toFloat() - (points[maxPointIndex].coerceIn(
                    0f, 1f
                ) * canvasSize.height.toFloat())

                with(density) {
                    val tooltipOffsetX = peakX.toDp()
                    val tooltipOffsetY = peakY.toDp()

                    Box(
                        modifier = Modifier.offset(
                            x = tooltipOffsetX - 40.dp, // Aligns tooltip center over the target element
                            y = tooltipOffsetY - 36.dp
                        ).background(
                            color = MaterialTheme.colorScheme.primaryContainer,
                            shape = RoundedCornerShape(6.dp)
                        ).padding(horizontal = 8.dp, vertical = 4.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = peakValueLabel,
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Dynamic horizontal X-Axis rendering
        Row(
            modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
        ) {
            xAxisLabels.forEach { label ->
                Text(
                    text = label, style = MaterialTheme.typography.labelSmall, color = labelColor
                )
            }
        }
    }
}

private fun List<Float>.indexOfMax(): Int? {
    if (isEmpty()) return null
    var maxIndex = 0
    var maxValue = this[0]
    for (i in 1 until size) {
        if (this[i] > maxValue) {
            maxValue = this[i]
            maxIndex = i
        }
    }
    return maxIndex
}


// ==========================================================
// Light Mode Preview (English Layout / LTR)
// ==========================================================
@Preview
@Composable
fun SpendingTrendSectionLightPreview() {
    PocketGoalsTheme(themeMode = ThemeMode.LIGHT) {
        Box(
            modifier = Modifier.background(MaterialTheme.colorScheme.background).padding(16.dp)
        ) {
            SpendingTrendSection(
                sectionTitle = "Spending Trend",
                points = listOf(0.1f, 0.4f, 0.6f, 0.5f, 0.65f, 0.55f, 0.9f),
                peakValueLabel = "$12,450",
                xAxisLabels = listOf("1 May", "8 May", "15 May", "22 May", "29 May")
            )
        }
    }
}

// ==========================================================
// Dark Mode Preview (Arabic or Urdu Layout / RTL Check)
// ==========================================================
@Preview
@Composable
fun SpendingTrendSectionDarkPreview() {
    // Demonstrating Dark Theme with Arabic locale constraints
    PocketGoalsTheme(themeMode = ThemeMode.DARK) {
        Box(
            modifier = Modifier.background(MaterialTheme.colorScheme.background).padding(16.dp)
        ) {
            SpendingTrendSection(
                sectionTitle = "نزعة الإنفاق", // Localized Title
                points = listOf(0.1f, 0.4f, 0.6f, 0.5f, 0.65f, 0.55f, 0.9f),
                peakValueLabel = "١٢،٤٥٠ د.إ", // Localized Arabic Numbers & Currency symbol
                xAxisLabels = listOf(
                    "١ مايو", "٨ مايو", "١٥ مايو", "٢٢ مايو", "٢٩ مايو"
                ) // Localized Dates
            )
        }
    }
}
