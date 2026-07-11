package com.hathway.pocketgoals.domain

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.TrendingUp
import androidx.compose.material.icons.rounded.*

data class ExpenseCategory(
    val name: String,
    val color: androidx.compose.ui.graphics.Color,
    val icon: androidx.compose.ui.graphics.vector.ImageVector
) {
    companion object {
        val defaultCategories = listOf(
            ExpenseCategory("Food & Dining", androidx.compose.ui.graphics.Color(0xFFF59E0B), Icons.Rounded.Restaurant),
            ExpenseCategory("Groceries", androidx.compose.ui.graphics.Color(0xFF10B981), Icons.Rounded.ShoppingCart),
            ExpenseCategory("Transport", androidx.compose.ui.graphics.Color(0xFF3B82F6), Icons.Rounded.DirectionsCar),
            ExpenseCategory("Fuel", androidx.compose.ui.graphics.Color(0xFFEF4444), Icons.Rounded.LocalGasStation),
            ExpenseCategory("Home", androidx.compose.ui.graphics.Color(0xFF6366F1), Icons.Rounded.Home),
            ExpenseCategory("Bills & Utilities", androidx.compose.ui.graphics.Color(0xFFFACC15), Icons.Rounded.Lightbulb),
            ExpenseCategory("Healthcare", androidx.compose.ui.graphics.Color(0xFFEF4444), Icons.Rounded.Favorite),
            ExpenseCategory("Shopping", androidx.compose.ui.graphics.Color(0xFFEC4899), Icons.Rounded.ShoppingBag),
            ExpenseCategory("Entertainment", androidx.compose.ui.graphics.Color(0xFF8B5CF6), Icons.Rounded.PlayCircle),
            ExpenseCategory("Travel", androidx.compose.ui.graphics.Color(0xFF0EA5E9), Icons.Rounded.Flight),
            ExpenseCategory("Education", androidx.compose.ui.graphics.Color(0xFF14B8A6), Icons.Rounded.School),
            ExpenseCategory("Family", androidx.compose.ui.graphics.Color(0xFFF97316), Icons.Rounded.Groups),
            ExpenseCategory("Work", androidx.compose.ui.graphics.Color(0xFF64748B), Icons.Rounded.Work),
            ExpenseCategory("Finance", androidx.compose.ui.graphics.Color(0xFF475569), Icons.Rounded.AccountBalanceWallet),
            ExpenseCategory("Investment", androidx.compose.ui.graphics.Color(0xFF059669), Icons.AutoMirrored.Rounded.TrendingUp),
            ExpenseCategory("Savings", androidx.compose.ui.graphics.Color(0xFF22C55E), Icons.Rounded.Savings),
            ExpenseCategory("Gifts", androidx.compose.ui.graphics.Color(0xFFFB7185), Icons.Rounded.CardGiftcard),
            ExpenseCategory("Pets", androidx.compose.ui.graphics.Color(0xFFD946EF), Icons.Rounded.Pets),
            ExpenseCategory("Charity", androidx.compose.ui.graphics.Color(0xFFF43F5E), Icons.Rounded.VolunteerActivism),
            ExpenseCategory("Subscriptions", androidx.compose.ui.graphics.Color(0xFF6366F1), Icons.Rounded.Subscriptions),
            ExpenseCategory("Personal Care", androidx.compose.ui.graphics.Color(0xFF2DD4BF), Icons.Rounded.AutoAwesome),
            ExpenseCategory("Fitness", androidx.compose.ui.graphics.Color(0xFF84CC16), Icons.Rounded.FitnessCenter),
            ExpenseCategory("Maintenance", androidx.compose.ui.graphics.Color(0xFF78716C), Icons.Rounded.Build),
            ExpenseCategory("Income", androidx.compose.ui.graphics.Color(0xFF10B981), Icons.Rounded.AddCard),
            ExpenseCategory("Others", androidx.compose.ui.graphics.Color(0xFF94A3B8), Icons.Rounded.MoreHoriz)
        )
    }
}

