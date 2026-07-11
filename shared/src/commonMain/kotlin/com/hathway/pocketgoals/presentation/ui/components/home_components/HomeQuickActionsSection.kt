package com.hathway.pocketgoals.presentation.ui.components.home_components

import androidx.compose.foundation.isSystemInDarkTheme
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hathway.pocketgoals.domain.model.ThemeMode
import com.hathway.pocketgoals.presentation.ui.theme.PocketGoalsTheme
import com.hathway.pocketgoals.presentation.ui.theme.Vacation
import com.hathway.pocketgoals.presentation.ui.theme.Investment

@Composable
fun HomeQuickActionsSection(
    onAddExpenseClick: () -> Unit = {},
    onAddIncomeClick: () -> Unit = {},
    onTransferClick: () -> Unit = {},
    onViewReportsClick: () -> Unit = {},
    onManageClick: () -> Unit = {}
) {
    val isDark = isSystemInDarkTheme()

    // 1. Expense: Uses Theme Primary Containers
    val expenseBg = MaterialTheme.colorScheme.primaryContainer
    val expenseIcon = MaterialTheme.colorScheme.primary

    // 2. Income: Uses Investment/Success color system
    val incomeBg = if (isDark) Color(0xFF064E3B) else Color(0xFFECFDF5)
    val incomeIcon = Investment

    // 3. Transfer: Uses Theme Tertiary Containers
    val transferBg = MaterialTheme.colorScheme.tertiaryContainer
    val transferIcon = MaterialTheme.colorScheme.tertiary

    // 4. View Reports: Uses Vacation/Warning color system
    val reportsBg = if (isDark) Color(0xFF7C2D12) else Color(0xFFFFF7ED)
    val reportsIcon = Vacation

    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Quick Actions",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                text = "Manage",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.SemiBold
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            HomeQuickActionItem(
                label = "Add\nExpense",
                icon = Icons.Rounded.Add,
                bgColor = expenseBg,
                iconColor = expenseIcon,
                onClick = onAddExpenseClick
            )
            HomeQuickActionItem(
                label = "Add\nIncome",
                icon = Icons.Rounded.AccountBalanceWallet,
                bgColor = incomeBg,
                iconColor = incomeIcon,
                onClick = onAddIncomeClick
            )
            HomeQuickActionItem(
                label = "Transfer",
                icon = Icons.AutoMirrored.Rounded.CompareArrows,
                bgColor = transferBg,
                iconColor = transferIcon,
                onClick = onTransferClick
            )
            HomeQuickActionItem(
                label = "View\nReports",
                icon = Icons.Rounded.BarChart,
                bgColor = reportsBg,
                iconColor = reportsIcon,
                onClick = onViewReportsClick
            )
        }
    }
}

@Preview
@Composable
fun HomeQuickActionsSectionLightPreview() {
    PocketGoalsTheme(themeMode = ThemeMode.LIGHT) {
        HomeQuickActionsSection()
    }
}

@Preview
@Composable
fun HomeQuickActionsSectionDarkPreview() {
    PocketGoalsTheme(themeMode = ThemeMode.DARK) {
        HomeQuickActionsSection()
    }
}