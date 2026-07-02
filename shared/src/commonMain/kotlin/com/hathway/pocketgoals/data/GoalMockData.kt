package com.hathway.pocketgoals.data

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AirplanemodeActive
import androidx.compose.material.icons.rounded.DirectionsCar
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Security
import androidx.compose.material.icons.rounded.Work
import androidx.compose.ui.graphics.Color
import com.hathway.pocketgoals.domain.Goal

object GoalMockData {
    val mockGoals = listOf(
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
