package com.hathway.pocketgoals.presentation.ui.navigation_content

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hathway.pocketgoals.presentation.ui.components.home_components.HomeBalanceCard
import com.hathway.pocketgoals.presentation.ui.components.home_components.HomeHeaderSection
import com.hathway.pocketgoals.presentation.ui.components.home_components.HomeOverviewSection
import com.hathway.pocketgoals.presentation.ui.components.home_components.HomeQuickActionsSection
import com.hathway.pocketgoals.presentation.ui.components.home_components.HomeTopCategoriesSection

@Composable
fun HomeContent() {
    LazyColumn(
        modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        // Header: Greeting & Profile
        item {
            HomeHeaderSection(name = "Ahsan")
        }

        // Balance Card
        item {
            HomeBalanceCard(
                totalBalance = "235,000", income = "150,000", expenses = "85,000"
            )
        }

        // Quick Actions
        item {
            HomeQuickActionsSection()
        }

        // Overview This Month
        item {
            HomeOverviewSection(
                income = "150,000", expenses = "85,000", savings = "65,000"
            )
        }

        // Top Categories
        item {
            HomeTopCategoriesSection()
        }
    }
}
