package com.hathway.pocketgoals.presentation.ui.components.home_components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.CompareArrows
import androidx.compose.material.icons.rounded.AccountBalanceWallet
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.BarChart
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun HomeQuickActionsSection() {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Quick Actions",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Manage",
                style = MaterialTheme.typography.bodySmall,
                color = Color(0xFF0F766E),
                fontWeight = FontWeight.SemiBold
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
        ) {
            HomeQuickActionItem(
                "Add\nExpense", Icons.Rounded.Add, Color(0xFFCCFBF1), Color(0xFF0F766E)
            )
            HomeQuickActionItem(
                "Add\nIncome",
                Icons.Rounded.AccountBalanceWallet,
                Color(0xFFECFDF5),
                Color(0xFF10B981)
            )
            HomeQuickActionItem(
                "Transfer",
                Icons.AutoMirrored.Rounded.CompareArrows,
                Color(0xFFF5F3FF),
                Color(0xFF8B5CF6)
            )
            HomeQuickActionItem(
                "View\nReports", Icons.Rounded.BarChart, Color(0xFFFFF7ED), Color(0xFFF97316)
            )
        }
    }
}