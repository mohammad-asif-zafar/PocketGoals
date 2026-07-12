package com.hathway.pocketgoals.data

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

// Mock data architecture matching your core models mapping
data class AnalyticsUiCategory(
    val name: String,
    val value: String,
    val color: Color,
    val icon: ImageVector
)
