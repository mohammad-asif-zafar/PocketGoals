package com.hathway.pocketgoals.domain

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.TrendingUp
import androidx.compose.material.icons.rounded.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

data class IncomeType(
    val name: String,
    val description: String,
    val icon: ImageVector,
    val color: Color
) {
    companion object {
        val defaultTypes = listOf(
            IncomeType("Salary", "Regular salary income", Icons.Rounded.Work, Color(0xFF10B981)),
            IncomeType("Business", "Business or freelance income", Icons.Rounded.BusinessCenter, Color(0xFF3B82F6)),
            IncomeType("Freelance", "Freelance / contract work", Icons.Rounded.Person, Color(0xFFF59E0B)),
            IncomeType("Investment", "Returns from investments", Icons.AutoMirrored.Rounded.TrendingUp, Color(0xFF8B5CF6)),
            IncomeType("Rental", "Income from rent", Icons.Rounded.Home, Color(0xFFEC4899)),
            IncomeType("Interest", "Bank interest income", Icons.Rounded.AccountBalance, Color(0xFF14B8A6)),
            IncomeType("Other Income", "Other sources of income", Icons.Rounded.AddCard, Color(0xFF64748B))
        )
    }
}