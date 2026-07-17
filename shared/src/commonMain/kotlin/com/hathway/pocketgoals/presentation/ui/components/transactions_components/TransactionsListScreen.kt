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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
import com.hathway.pocketgoals.domain.model.Transaction
import com.hathway.pocketgoals.domain.model.TransactionType
import com.hathway.pocketgoals.presentation.ui.viewmodel.TransactionsViewModel
import org.jetbrains.compose.resources.stringResource
import pocketgoals.shared.generated.resources.*

@Composable
fun TransactionsListScreen(
    viewModel: TransactionsViewModel,
    onTransactionClick: (Transaction) -> Unit
) {
    val tabAll = stringResource(Res.string.home)
    val tabIncome = stringResource(Res.string.income)
    val tabExpense = stringResource(Res.string.expense)
    
    var selectedTab by remember { mutableIntStateOf(0) }
    val tabs = listOf(tabAll, tabIncome, tabExpense)
    var isSearchActive by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf("") }

    val transactions by viewModel.transactions.collectAsState()

    val filteredTransactions = remember(transactions, selectedTab, searchQuery) {
        transactions.filter {
            val matchesTab = when (selectedTab) {
                1 -> it.type == TransactionType.INCOME
                2 -> it.type == TransactionType.EXPENSE
                else -> true
            }
            val matchesSearch = it.title.contains(searchQuery, ignoreCase = true) ||
                    it.category.contains(searchQuery, ignoreCase = true)
            matchesTab && matchesSearch
        }
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
                item { SectionHeader(stringResource(Res.string.recent_activity)) }
                items(filteredTransactions) { transaction ->
                    TransactionListItem(transaction = transaction, onClick = { onTransactionClick(transaction) })
                }
                item { Spacer(modifier = Modifier.height(80.dp)) }
            }
            Box(modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 16.dp)) {
                val totalIncome = transactions.filter { it.type == TransactionType.INCOME }.sumOf { it.amount }
                val totalExpense = transactions.filter { it.type == TransactionType.EXPENSE }.sumOf { it.amount }
                val totalSavings = totalIncome - totalExpense

                TransactionsSummaryBar(
                    income = "₹ ${totalIncome.toInt()}",
                    expense = "₹ ${totalExpense.toInt()}",
                    savings = "₹ ${totalSavings.toInt()}"
                )
            }
        }
    }
}
