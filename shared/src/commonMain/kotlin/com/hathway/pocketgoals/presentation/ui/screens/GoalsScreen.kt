package com.hathway.pocketgoals.presentation.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.rounded.AirplanemodeActive
import androidx.compose.material.icons.rounded.DirectionsCar
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Security
import androidx.compose.material.icons.rounded.Work
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.hathway.pocketgoals.domain.Goal

@Composable
fun GoalsScreen() {
    val mockGoals = remember {
        listOf(
            Goal(
                id = "1",
                name = "Foreign Job Fund",
                targetAmount = 6000000.0,
                savedAmount = 23000.0,
                deadline = "2025-12-31",
                icon = Icons.Rounded.Work,
                color = Color(0xFF64748B)
            ),
            Goal(
                id = "2",
                name = "Home Renovation",
                targetAmount = 1000000.0,
                savedAmount = 4000.0,
                deadline = "2025-12-31",
                icon = Icons.Rounded.Home,
                color = Color(0xFFF97316)
            ),
            Goal(
                id = "3",
                name = "Vacation Trip",
                targetAmount = 1800000.0,
                savedAmount = 7500.0,
                deadline = "2025-12-31",
                icon = Icons.Rounded.AirplanemodeActive,
                color = Color(0xFF3B82F6)
            ),
            Goal(
                id = "4",
                name = "Emergency Fund",
                targetAmount = 80000.0,
                savedAmount = 10000.0,
                deadline = "2025-12-31",
                icon = Icons.Rounded.Security,
                color = Color(0xFFEF4444)
            ),
            Goal(
                id = "5",
                name = "New Car",
                targetAmount = 500000.0,
                savedAmount = 120000.0,
                deadline = "2025-12-31",
                icon = Icons.Rounded.DirectionsCar,
                color = Color(0xFF0EA5E9)
            )
        )
    }

    Scaffold(
        topBar = {
            GoalsTopBar()
        },
        containerColor = Color.White
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color.White),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(mockGoals) { goal ->
                GoalItem(goal = goal)
            }
            item {
                Spacer(modifier = Modifier.height(100.dp))
            }
        }
    }
}

@Composable
fun GoalsTopBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Goals",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )
        
        Surface(
            onClick = { /* Add Goal */ },
            shape = RoundedCornerShape(8.dp),
            color = Color(0xFFF0FDF4),
            modifier = Modifier.height(36.dp)
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null,
                    tint = Color(0xFF16A34A),
                    modifier = Modifier.size(18.dp)
                )
                Text(
                    text = "Add Goal",
                    style = MaterialTheme.typography.labelLarge,
                    color = Color(0xFF16A34A),
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

@Composable
fun GoalItem(goal: Goal) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, Color(0xFFF1F5F9), RoundedCornerShape(16.dp)),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Icon
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .clip(CircleShape)
                    .background(goal.color.copy(alpha = 0.1f)),
                contentAlignment = Alignment.Center
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
                    fontWeight = FontWeight.Bold
                )
                
                Spacer(modifier = Modifier.height(4.dp))
                
                Text(
                    text = "₹${formatMockAmount(goal.savedAmount)} / ₹${formatMockAmount(goal.targetAmount)}",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.DarkGray,
                    fontWeight = FontWeight.Medium
                )
                
                Spacer(modifier = Modifier.height(12.dp))
                
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    LinearProgressIndicator(
                        progress = { goal.progress },
                        modifier = Modifier
                            .weight(1f)
                            .height(8.dp)
                            .clip(CircleShape),
                        color = Color(0xFF10B981),
                        trackColor = Color(0xFFE2E8F0)
                    )
                    
                    Text(
                        text = "${goal.percentage}%",
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                }
            }
        }
    }
}

private fun formatMockAmount(amount: Double): String {
    // Simple manual formatting to match the Indian system style in the image for the mock
    val longVal = amount.toLong()
    return when {
        longVal >= 100000 -> {
            val lakhs = longVal / 100000
            val thousands = (longVal % 100000) / 1000
            val hundreds = longVal % 1000
            if (lakhs > 0) {
                if (thousands > 0) {
                    "$lakhs,${thousands.toString().padStart(2, '0')},${hundreds.toString().padStart(3, '0')}"
                } else {
                    "$lakhs,00,${hundreds.toString().padStart(3, '0')}"
                }
            } else {
                "$longVal"
            }
        }
        longVal >= 1000 -> {
            val thousands = longVal / 1000
            val hundreds = longVal % 1000
            "$thousands,${hundreds.toString().padStart(3, '0')}"
        }
        else -> longVal.toString()
    }
}
