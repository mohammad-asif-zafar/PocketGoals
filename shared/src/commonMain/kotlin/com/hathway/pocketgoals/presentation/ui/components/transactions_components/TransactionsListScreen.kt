package com.hathway.pocketgoals.presentation.ui.components.transactions_components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountBalance
import androidx.compose.material.icons.rounded.DirectionsCar
import androidx.compose.material.icons.rounded.Lightbulb
import androidx.compose.material.icons.rounded.ShoppingCart
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.hathway.pocketgoals.domain.Transaction
import com.hathway.pocketgoals.domain.TransactionType

@Composable
fun TransactionsListScreen(onTransactionClick: (Transaction) -> Unit) {
    var selectedTab by remember { mutableIntStateOf(0) }
    val tabs = listOf("All", "Income", "Expense")
    var isSearchActive by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf("") }

    val mockTransactions = remember {
        listOf(
            Transaction("1", "Groceries", 2500.0, TransactionType.EXPENSE, "18 May 2024", "10:30 AM", "Groceries", "Cash", "Weekly groceries from supermarket", Icons.Rounded.ShoppingCart),
            Transaction("2", "Salary", 125000.0, TransactionType.INCOME, "15 May 2024", "09:00 AM", "Salary", "Bank Transfer", "", Icons.Rounded.AccountBalance),
            Transaction("3", "Electricity Bill", 1200.0, TransactionType.EXPENSE, "14 May 2024", "08:00 PM", "Bills", "UPI", "", Icons.Rounded.Lightbulb),
            Transaction("4", "Fuel", 4500.0, TransactionType.EXPENSE, "14 May 2024", "04:45 PM", "Transport", "Card", "", Icons.Rounded.DirectionsCar)
        )
    }

    Scaffold(
        topBar = {
            Column(modifier = Modifier.background(MaterialTheme.colorScheme.surface)) {
                if (isSearchActive) {
                    SearchTopBar(query = searchQuery, onQueryChange = { searchQuery = it }, onClose = { isSearchActive = false })
                } else {
                    TransactionsTopBar(onSearchClick = { isSearchActive = true })
                }
                TabRow(
                    selectedTabIndex = selectedTab,
                    containerColor = MaterialTheme.colorScheme.surface,
                    contentColor = Color(0xFF0F766E),
                    divider = {}
                ) {
                    tabs.forEachIndexed { index, title ->
                        Tab(
                            selected = selectedTab == index,
                            onClick = { selectedTab = index },
                            text = { Text(text = title, fontWeight = if (selectedTab == index) FontWeight.Bold else FontWeight.Medium) }
                        )
                    }
                }
            }
        }
    ) { paddingValues ->
        Box(modifier = Modifier.fillMaxSize().padding(paddingValues)) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item { SectionHeader("Today") }
                items(mockTransactions) { transaction ->
                    TransactionListItem(transaction = transaction, onClick = { onTransactionClick(transaction) })
                }
                item { SectionHeader("This Week") }
                items(mockTransactions.take(2)) { transaction ->
                    TransactionListItem(transaction = transaction, onClick = { onTransactionClick(transaction) })
                }
                item { Spacer(modifier = Modifier.height(80.dp)) }
            }
            Box(modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 16.dp)) {
                TransactionsSummaryBar(income = "150,000", expense = "85,000", savings = "65,000")
            }
        }
    }
}