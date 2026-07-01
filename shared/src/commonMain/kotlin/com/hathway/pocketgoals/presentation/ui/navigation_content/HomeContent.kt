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
import com.hathway.pocketgoals.presentation.ui.state.HomeUiState

@Composable
fun HomeContent(
    uiState: HomeUiState, onNotificationClick: () -> Unit, onProfileClick: () -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        // Header: Greeting & Profile
        item {
            // Bound directly to our streaming ViewModel pipeline state
            HomeHeaderSection(
                name = uiState.userName,
                unreadCount = uiState.unreadNotificationCount,
                onNotificationClick = onNotificationClick,
                onProfileClick = onProfileClick
            )
        }

        // Balance Card
        item {
            HomeBalanceCard(
                totalBalance = uiState.totalBalance,
                income = uiState.income,
                expenses = uiState.expenses
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
