package com.hathway.pocketgoals.presentation.ui.navigation_content

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hathway.pocketgoals.domain.model.ThemeMode
import com.hathway.pocketgoals.presentation.ui.components.analytics_components.AnalyticsCategoryItem
import com.hathway.pocketgoals.presentation.ui.components.analytics_components.AnalyticsHeader
import com.hathway.pocketgoals.presentation.ui.components.analytics_components.SpendingTrendSection
import com.hathway.pocketgoals.presentation.ui.state.AnalyticsUiState
import com.hathway.pocketgoals.presentation.ui.theme.PocketGoalsTheme
import com.hathway.pocketgoals.presentation.ui.theme.Success
import org.jetbrains.compose.resources.stringResource
import pocketgoals.shared.generated.resources.Res
import pocketgoals.shared.generated.resources.analytics_see_all
import pocketgoals.shared.generated.resources.analytics_top_categories
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Fastfood
import androidx.compose.material.icons.rounded.LocalMall
import androidx.compose.material3.Surface
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import com.hathway.pocketgoals.presentation.ui.components.analytics_components.AnalyticsCategoryData
import pocketgoals.shared.generated.resources.spending_trend

import com.hathway.pocketgoals.domain.model.TransactionType
import com.hathway.pocketgoals.presentation.ui.components.analytics_components.FullAnalyticsDashboard

@Composable
fun AnalyticsContent(
    uiState: AnalyticsUiState,
    onPeriodChange: (String) -> Unit = {},
    onTypeChange: (TransactionType) -> Unit = {},
    onSeeAllClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background,
        contentWindowInsets = WindowInsets(0, 0, 0, 0)
    ) { paddingValues ->

        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(paddingValues)
                .background(MaterialTheme.colorScheme.background),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
        ) {
            item {
                AnalyticsHeader(
                    selectedPeriod = uiState.selectedPeriod, onPeriodClick = { /* Logic for period selection */ }
                )
            }

            item {
                FullAnalyticsDashboard(
                    barData = uiState.barData,
                    donutData = uiState.donutData,
                    selectedType = uiState.selectedType,
                    onTypeChange = onTypeChange,
                    selectedPeriod = uiState.selectedPeriod,
                    onPeriodChange = onPeriodChange
                )
            }

            item {
                // Only render the chart if we have data points to display
                if (uiState.trendPoints.isNotEmpty()) {
                    SpendingTrendSection(
                        sectionTitle = stringResource(Res.string.spending_trend),
                        points = uiState.trendPoints,
                        peakValueLabel = uiState.peakValueLabel,
                        xAxisLabels = uiState.xAxisLabels,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                }
            }

            if (uiState.isTransactionsEmpty) {
                item {
                    Box(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 60.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "UI is Empty",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "No analytics data available yet.",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            } else {
                // Inline the Top Categories Header Panel
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(top = 24.dp, bottom = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = stringResource(Res.string.analytics_top_categories),
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        Text(
                            text = stringResource(Res.string.analytics_see_all),
                            style = MaterialTheme.typography.bodySmall,
                            color = Success, // Uses custom project Success token color
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.clip(MaterialTheme.shapes.small)
                                .clickable { onSeeAllClick() }
                                .padding(horizontal = 8.dp, vertical = 4.dp))
                    }
                }

                // Efficiently recycle items linearly within the same scroll viewport layout
                items(
                    items = uiState.categories, key = { it.name }) { category ->
                    AnalyticsCategoryItem(
                        name = category.name,
                        value = category.value,
                        color = category.color,
                        icon = category.icon
                    )
                }
            }

            item {
                Spacer(modifier = Modifier.height(100.dp))
            }
        }
    }
}

@Composable
fun AnalyticsPreviewLanguageContainer(
    variantLabel: String, themeMode: ThemeMode, isRtl: Boolean, mockState: AnalyticsUiState
) {
    Column(modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp)) {
        Text(
            text = variantLabel,
            style = MaterialTheme.typography.labelMedium,
            color = if (themeMode == ThemeMode.DARK) Color(0xFFCBD5E1) else Color(0xFF334155),
            modifier = Modifier.padding(bottom = 6.dp)
        )
        PocketGoalsTheme(themeMode = themeMode) {
            CompositionLocalProvider(
                LocalLayoutDirection provides if (isRtl) LayoutDirection.Rtl else LayoutDirection.Ltr
            ) {
                Surface(
                    modifier = Modifier.fillMaxWidth().wrapContentHeight(),
                    color = MaterialTheme.colorScheme.background,
                    tonalElevation = 1.dp
                ) {
                    AnalyticsContent(uiState = mockState, onPeriodChange = {}, onSeeAllClick = {})
                }
            }
        }
    }
}

@Preview(name = "Analytics Studio Multiplatform Preview Grid")
@Composable
fun FullAnalyticsDashboardMatrixPreview() {
    val sampleCategories = listOf(
        AnalyticsCategoryData(
            name = "Food & Dining",
            value = "₹ 14,250",
            color = Color(0xFFEF4444),
            icon = Icons.Rounded.Fastfood
        ), AnalyticsCategoryData(
            name = "Shopping",
            value = "₹ 8,900",
            color = Color(0xFF3B82F6),
            icon = Icons.Rounded.LocalMall
        )
    )

    val baseMockUiState = AnalyticsUiState(
        selectedPeriod = "This Month",
        totalAmount = "₹ 23,150",
        categories = sampleCategories,
        isLoading = false
    )

    Column(
        modifier = Modifier.fillMaxSize().background(Color(0xFFF1F5F9))
            .verticalScroll(rememberScrollState()).padding(16.dp)
    ) {
        // ==========================================================
        // LIGHT MODES SECTION
        // ==========================================================
        Text(
            text = "☀️ LIGHT MODE MATRIX",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF0F172A),
            modifier = Modifier.padding(bottom = 8.dp)
        )

        AnalyticsPreviewLanguageContainer(
            "English", ThemeMode.LIGHT, isRtl = false, baseMockUiState
        )

        Spacer(modifier = Modifier.height(32.dp))

        // ==========================================================
        // DARK MODES SECTION
        // ==========================================================
        Text(
            text = "🌙 DARK MODE MATRIX",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF0F172A),
            modifier = Modifier.padding(bottom = 8.dp)
        )

        AnalyticsPreviewLanguageContainer(
            "English",
            ThemeMode.DARK,
            isRtl = false,
            baseMockUiState
        )

    }
}
