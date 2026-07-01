package com.hathway.pocketgoals.presentation.ui.components.home_components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.TrendingUp
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Kitchen
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun HomeTopCategoriesSection() {
    Column {
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
                color = Color(0xFF0F766E),
                fontWeight = FontWeight.SemiBold
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        HomeCategoryRow("Investment", 34.47, "31,029", Icons.AutoMirrored.Rounded.TrendingUp, Color(0xFF3B82F6))
        HomeCategoryRow("Basic Needs", 27.81, "25,029", Icons.Rounded.Home, Color(0xFF10B981))
        HomeCategoryRow("Future Pay", 15.11, "13,599", Icons.Rounded.Kitchen, Color(0xFFF59E0B))
        HomeCategoryRow("Family Support", 9.27, "8,343", Icons.Rounded.Star, Color(0xFF8B5CF6))
    }
}