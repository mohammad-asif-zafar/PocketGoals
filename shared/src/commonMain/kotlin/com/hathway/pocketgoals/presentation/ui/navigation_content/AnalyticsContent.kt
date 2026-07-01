package com.hathway.pocketgoals.presentation.ui.navigation_content

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.hathway.pocketgoals.presentation.ui.components.analytics_components.AnalyticsCategoryItem
import com.hathway.pocketgoals.presentation.ui.components.analytics_components.AnalyticsHeader
import com.hathway.pocketgoals.presentation.ui.components.analytics_components.ExpenseOverviewSection
import com.hathway.pocketgoals.presentation.ui.components.analytics_components.SpendingTrendSection
import com.hathway.pocketgoals.presentation.ui.state.AnalyticsUiState

@Composable
fun AnalyticsContent(
    uiState: AnalyticsUiState,
    onPeriodClick: () -> Unit
) {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->

        // Single master scrolling container removes infinite constraint errors completely
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(MaterialTheme.colorScheme.background),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
        ) {
            item {
                AnalyticsHeader(
                    selectedPeriod = uiState.selectedPeriod,
                    onPeriodClick = onPeriodClick
                )
            }

            item {
                ExpenseOverviewSection(totalAmount = uiState.totalAmount)
            }

            item {
                SpendingTrendSection()
            }

            // Inline the Top Categories Header
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 24.dp, bottom = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Top Categories",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Text(
                        text = "See All",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0xFF10B981),
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            // Efficiently recycle items linearly within the same scroll viewport
            items(
                items = uiState.categories,
                key = { it.name }
            ) { category ->
                AnalyticsCategoryItem(
                    name = category.name,
                    value = category.value,
                    color = category.color,
                    icon = category.icon
                )
            }

            item {
                Spacer(modifier = Modifier.height(100.dp))
            }
        }
    }
}
