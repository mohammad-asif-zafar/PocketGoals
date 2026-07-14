package com.hathway.pocketgoals.domain.model

import androidx.compose.ui.graphics.Color

// Helper data structure for easy list mapping
data class HomeCategoryData(
    val name: String,
    val percentage: Double,
    val amount: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector,
    val color: Color
)