package com.hathway.pocketgoals.presentation.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.hathway.pocketgoals.presentation.ui.components.transactions_components.AnalyticsHeader
import com.hathway.pocketgoals.presentation.ui.components.transactions_components.ExpenseOverviewSection
import com.hathway.pocketgoals.presentation.ui.components.transactions_components.SpendingTrendSection
import com.hathway.pocketgoals.presentation.ui.components.transactions_components.TopCategoriesAnalytics

@Composable
fun AnalyticsScreen() {
    Scaffold(
        containerColor = Color.White
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color.White)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            AnalyticsHeader()
            
            ExpenseOverviewSection(totalAmount = "₹90,000")
            
            SpendingTrendSection()
            
            TopCategoriesAnalytics()
            
            Spacer(modifier = Modifier.height(100.dp))
        }
    }
}
