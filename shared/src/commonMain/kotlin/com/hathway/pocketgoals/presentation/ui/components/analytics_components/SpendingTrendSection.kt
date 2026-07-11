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
fun SpendingTrendSection() {
    val gridAndBorderColor = MaterialTheme.colorScheme.outlineVariant
    val labelColor = MaterialTheme.colorScheme.onSurfaceVariant
    val backgroundColor = MaterialTheme.colorScheme.background
    val density = LocalDensity.current

    // State to track canvas dimensions for calculating dynamic layout position
    var canvasSize by remember { mutableStateOf(IntSize.Zero) }

    val points = listOf(0.1f, 0.4f, 0.6f, 0.5f, 0.65f, 0.55f, 0.9f)
    val maxPointIndex = points.indexOfMax() ?: -1
    val peakValueLabel = "₹12,450" // Sample value for the highest spending day

    Column(
        modifier = Modifier.fillMaxWidth().padding(top = 24.dp)
            .border(1.dp, gridAndBorderColor, RoundedCornerShape(16.dp)).padding(16.dp)
    ) {
        Text(
            text = "Spending Trend",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Wrapped in an overall Box so we can absolute-position the Compose Tooltip Layer over the Canvas
        Box(
            modifier = Modifier.fillMaxWidth().height(150.dp)
        ) {
            Canvas(
                modifier = Modifier.fillMaxSize()
                    .onSizeChanged { canvasSize = it } // Track size updates
            ) {
                val width = size.width
                val height = size.height
                val stepX = width / (points.size - 1)

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
                if (maxPointIndex != -1) {
                    val maxTargetX = maxPointIndex * stepX
                    val maxTargetY = height - (points[maxPointIndex] * height)

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
                    val startY = height - (points[0] * height)

                    trendPath.moveTo(startX, startY)
                    fillPath.moveTo(startX, height)
                    fillPath.lineTo(startX, startY)

                    for (i in 0 until points.size - 1) {
                        val currentX = i * stepX
                        val currentY = height - (points[i] * height)
                        val nextX = (i + 1) * stepX
                        val nextY = height - (points[i + 1] * height)

                        val controlX1 = currentX + (stepX / 2f)
                        val controlY1 = currentY
                        val controlX2 = currentX + (stepX / 2f)
                        val controlY2 = nextY

                        trendPath.cubicTo(
                            x1 = controlX1,
                            y1 = controlY1,
                            x2 = controlX2,
                            y2 = controlY2,
                            x3 = nextX,
                            y3 = nextY
                        )
                        fillPath.cubicTo(
                            x1 = controlX1,
                            y1 = controlY1,
                            x2 = controlX2,
                            y2 = controlY2,
                            x3 = nextX,
                            y3 = nextY
                        )
                    }

                    val endX = (points.size - 1) * stepX
                    fillPath.lineTo(endX, height)
                    fillPath.close()
                }

                // Draw Premium Gradient Fill
                val brandGreen = Color(0xFF10B981)
                drawPath(
                    path = fillPath, brush = Brush.verticalGradient(
                        colors = listOf(
                            brandGreen.copy(alpha = 0.25f), backgroundColor.copy(alpha = 0.0f)
                        ), startY = 0f, endY = height
                    )
                )

                // Draw Smooth Stroke Line
                drawPath(
                    path = trendPath,
                    color = brandGreen,
                    style = Stroke(width = 3.dp.toPx(), cap = StrokeCap.Round)
                )

                // Draw Points
                points.forEachIndexed { index, p ->
                    val x = index * stepX
                    val y = height - (p * height)
                    val isMax = index == maxPointIndex
                    drawCircle(
                        color = brandGreen,
                        radius = if (isMax) 5.5.dp.toPx() else 4.dp.toPx(),
                        center = Offset(x, y)
                    )
                }
            }

            // 3. Floating Tooltip Overlay Rendering Layer
            if (maxPointIndex != -1 && canvasSize != IntSize.Zero) {
                val stepX = canvasSize.width.toFloat() / (points.size - 1)
                val peakX = maxPointIndex * stepX
                val peakY =
                    canvasSize.height.toFloat() - (points[maxPointIndex] * canvasSize.height.toFloat())

                with(density) {
                    // Convert pixel positions into layout density Dp offsets
                    val tooltipOffsetX = peakX.toDp()
                    val tooltipOffsetY = peakY.toDp()

                    Box(
                        modifier = Modifier.offset(
                            x = tooltipOffsetX - 40.dp, // Offsets half tooltip width to align center
                            y = tooltipOffsetY - 36.dp  // Floats exactly above the peak coordinate
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

        Row(
            modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
        ) {
            listOf("1 May", "8 May", "15 May", "22 May", "29 May").forEach {
                Text(
                    text = it, style = MaterialTheme.typography.labelSmall, color = labelColor
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

@Preview
@Composable
fun SpendingTrendSectionLightPreview() {
    PocketGoalsTheme(themeMode = ThemeMode.LIGHT) {
        Box(
            modifier = Modifier.background(MaterialTheme.colorScheme.background).padding(16.dp)
        ) { SpendingTrendSection() }
    }
}

@Preview
@Composable
fun SpendingTrendSectionDarkPreview() {
    PocketGoalsTheme(themeMode = ThemeMode.DARK) {
        Box(
            modifier = Modifier.background(MaterialTheme.colorScheme.background).padding(16.dp)
        ) { SpendingTrendSection() }
    }
}
