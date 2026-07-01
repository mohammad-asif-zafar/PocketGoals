package com.hathway.pocketgoals.presentation.ui.components.transactions_components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowRight
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material.icons.rounded.Kitchen
import androidx.compose.material.icons.rounded.Work
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AnalyticsHeader(
    selectedPeriod: String = "This Month",
    onPeriodClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Analytics",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )
        Surface(
            modifier = Modifier.clickable { onPeriodClick() },
            shape = RoundedCornerShape(12.dp),
            border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE2E8F0)),
            color = Color.White
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = selectedPeriod,
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Medium
                )
                Spacer(modifier = Modifier.width(4.dp))
                Icon(
                    imageVector = Icons.Rounded.KeyboardArrowDown,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp)
                )
            }
        }
    }
}

@Composable
fun ExpenseOverviewSection(totalAmount: String = "₹90,000") {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Expense Overview",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(vertical = 16.dp)
        )
        
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Donut Chart
            Box(
                modifier = Modifier.size(150.dp),
                contentAlignment = Alignment.Center
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
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Total",
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.Gray
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

@Composable
fun LegendItem(label: String, percentage: String, color: Color) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .background(color, CircleShape)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall,
                color = Color.DarkGray
            )
        }
        Text(
            text = percentage,
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun SpendingTrendSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 24.dp)
            .border(1.dp, Color(0xFFF1F5F9), RoundedCornerShape(16.dp))
            .padding(16.dp)
    ) {
        Text(
            text = "Spending Trend",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                val width = size.width
                val height = size.height
                val points = listOf(0.1f, 0.4f, 0.6f, 0.5f, 0.65f, 0.55f, 0.9f)
                val stepX = width / (points.size - 1)
                
                // Draw Grid Lines
                for (i in 0..3) {
                    val y = height - (i * height / 3)
                    drawLine(
                        color = Color(0xFFF1F5F9),
                        start = Offset(0f, y),
                        end = Offset(width, y),
                        strokeWidth = 1.dp.toPx()
                    )
                }
                
                // Draw Path
                val path = Path()
                points.forEachIndexed { index, p ->
                    val x = index * stepX
                    val y = height - (p * height)
                    if (index == 0) path.moveTo(x, y)
                    else path.lineTo(x, y)
                }
                
                drawPath(
                    path = path,
                    color = Color(0xFF10B981),
                    style = Stroke(width = 3.dp.toPx(), cap = StrokeCap.Round)
                )
                
                // Draw Points
                points.forEachIndexed { index, p ->
                    val x = index * stepX
                    val y = height - (p * height)
                    drawCircle(
                        color = Color(0xFF10B981),
                        radius = 4.dp.toPx(),
                        center = Offset(x, y)
                    )
                }
            }
        }
        
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            listOf("1 May", "8 May", "15 May", "22 May", "29 May").forEach {
                Text(it, style = MaterialTheme.typography.labelSmall, color = Color.Gray)
            }
        }
    }
}

@Composable
fun TopCategoriesAnalytics() {
    Column(modifier = Modifier.padding(top = 24.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Top Categories",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "See All",
                style = MaterialTheme.typography.bodySmall,
                color = Color(0xFF10B981),
                fontWeight = FontWeight.SemiBold
            )
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        AnalyticsCategoryItem("Investment", "₹31,023 (34.47%)", Color(0xFF3B82F6), Icons.Rounded.Work)
        AnalyticsCategoryItem("Basic Needs", "₹25,029 (27.81%)", Color(0xFF10B981), Icons.Rounded.Home)
        AnalyticsCategoryItem("Future Pay", "₹13,599 (15.11%)", Color(0xFFF59E0B), Icons.Rounded.Kitchen)
    }
}

@Composable
fun AnalyticsCategoryItem(name: String, value: String, color: Color, icon: ImageVector) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(color.copy(alpha = 0.1f), RoundedCornerShape(8.dp)),
            contentAlignment = Alignment.Center
        ) {
            Icon(imageVector = icon, contentDescription = null, tint = color, modifier = Modifier.size(20.dp))
        }
        
        Spacer(modifier = Modifier.width(12.dp))
        
        Text(
            text = name,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.weight(1f)
        )
        
        Text(
            text = value,
            style = MaterialTheme.typography.bodySmall,
            color = Color.DarkGray
        )
        
        Spacer(modifier = Modifier.width(8.dp))
        
        Icon(
            imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowRight,
            contentDescription = null,
            tint = Color.Gray,
            modifier = Modifier.size(16.dp)
        )
    }
}
