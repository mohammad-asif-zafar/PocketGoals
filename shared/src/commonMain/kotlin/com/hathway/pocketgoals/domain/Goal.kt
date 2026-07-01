package com.hathway.pocketgoals.domain

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

data class Goal(
    val id: String,
    val name: String,
    val targetAmount: Double,
    val savedAmount: Double,
    val deadline: String,
    val icon: ImageVector,
    val color: Color
) {
    val progress: Float get() = (savedAmount / targetAmount).toFloat().coerceIn(0f, 1f)
    val percentage: Int get() = (progress * 100).toInt()
    val remainingAmount: Double get() = targetAmount - savedAmount
    
    // Simple calculation for monthly saving needed (mock logic)
    val monthlySavingNeeded: Double get() = remainingAmount / 12 
}
