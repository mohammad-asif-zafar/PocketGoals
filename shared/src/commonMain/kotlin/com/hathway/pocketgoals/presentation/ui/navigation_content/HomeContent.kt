package com.hathway.pocketgoals.presentation.ui.navigation_content

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.hathway.pocketgoals.domain.model.ThemeMode
import com.hathway.pocketgoals.presentation.ui.components.home_components.HomeBalanceCard
import com.hathway.pocketgoals.presentation.ui.components.home_components.HomeHeaderSection
import com.hathway.pocketgoals.presentation.ui.components.home_components.HomeOverviewSection
import com.hathway.pocketgoals.presentation.ui.components.home_components.HomeQuickActionsSection
import com.hathway.pocketgoals.presentation.ui.components.home_components.HomeTopCategoriesSection
import com.hathway.pocketgoals.presentation.ui.localization.CurrencyConfig
import com.hathway.pocketgoals.presentation.ui.state.HomeUiState
import com.hathway.pocketgoals.presentation.ui.theme.PocketGoalsTheme

@Composable
fun HomeContent(
    uiState: HomeUiState,
    onNotificationClick: () -> Unit,
    onProfileClick: () -> Unit,
    onAddExpenseClick: () -> Unit = {},
    onAddIncomeClick: () -> Unit = {},
    onTransferClick: () -> Unit = {},
    onViewReportsClick: () -> Unit = {},
    onManageClick: () -> Unit = {}
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
                expenses = uiState.expenses,
                // Automatically determines currency layout configurations dynamically from device local settings
                currencyConfig = CurrencyConfig.fromSystemLocale(),
                onStatsClick = {
                    // Trigger your navigation route engine call action block here
                    // e.g., navController.navigate(Screen.AnalyticsDashboard.route)
                })
        }

        // Quick Actions
        item {
            HomeQuickActionsSection(
                onAddExpenseClick = { onAddExpenseClick() },
                onAddIncomeClick = { onAddIncomeClick() },
                onTransferClick = { onTransferClick() },
                onViewReportsClick = { onViewReportsClick() },
                onManageClick = { onManageClick() })
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


// Mock representation of your actual HomeUiState data class structure
data class HomeUiState(
    val userName: String = "John Doe",
    val unreadNotificationCount: Int = 3,
    val totalBalance: String = "150300",
    val income: String = "45000",
    val expenses: String = "12000"
)

@Composable
fun HomeContentPreviewItem(
    previewLabel: String, themeMode: ThemeMode, isRtl: Boolean, mockState: HomeUiState
) {
    Column(
        modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp)
    ) {
        Text(
            text = previewLabel,
            style = MaterialTheme.typography.titleMedium,
            color = if (themeMode == ThemeMode.DARK) Color(0xFFCBD5E1) else Color(0xFF334155),
            modifier = Modifier.padding(bottom = 8.dp, start = 4.dp)
        )

        PocketGoalsTheme(themeMode = themeMode) {
            CompositionLocalProvider(
                LocalLayoutDirection provides if (isRtl) LayoutDirection.Rtl else LayoutDirection.Ltr
            ) {
                // Surface forces the correct system background colors onto the canvas
                Surface(
                    modifier = Modifier.fillMaxWidth()
                        .height(720.dp), // Set fixed height bounded to screen simulation expectations
                    color = MaterialTheme.colorScheme.background
                ) {
                    HomeContent(
                        uiState = mockState,
                        onNotificationClick = {},
                        onProfileClick = {},
                        onAddExpenseClick = {},
                        onAddIncomeClick = {},
                        onTransferClick = {},
                        onViewReportsClick = {},
                        onManageClick = {})
                }
            }
        }
    }
}

/**
 * Master Preview Matrix Container rendering your HomeScreen Layout Canvas.
 */
@Composable
fun HomeContentThemePreviewMatrix() {
    val englishMockState = HomeUiState(
        userName = "Alex Smith",
        unreadNotificationCount = 2,
        totalBalance = "84250",
        income = "12500",
        expenses = "3200"
    )

    Column(
        modifier = Modifier.fillMaxSize()
            .background(Color(0xFFE2E8F0)) // Canvas background boundary wrapper color
            .verticalScroll(rememberScrollState()).padding(16.dp)
    ) {
        // ==========================================================
        // ☀️ LIGHT THEME MATRICES
        // ==========================================================
        Text(
            text = "☀️ HOME CONTENT - LIGHT THEME",
            style = MaterialTheme.typography.headlineSmall,
            color = Color(0xFF0F172A),
            modifier = Modifier.padding(bottom = 8.dp, top = 8.dp)
        )

        HomeContentPreviewItem(
            previewLabel = "1. English Variant (LTR)",
            themeMode = ThemeMode.LIGHT,
            isRtl = false,
            mockState = englishMockState
        )
    }
}

@Preview(name = "Home Screen Matrix Preview", showBackground = true)
@Composable
fun AndroidHomeContentGridPreview() {
    HomeContentThemePreviewMatrix()
}