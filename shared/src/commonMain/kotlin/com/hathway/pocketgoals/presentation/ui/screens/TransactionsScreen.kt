package com.hathway.pocketgoals.presentation.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.hathway.pocketgoals.domain.Transaction
import com.hathway.pocketgoals.domain.TransactionType

sealed class TransactionFlowState {
    data object List : TransactionFlowState()
    data class Details(val transaction: Transaction) : TransactionFlowState()
    data class Edit(val transaction: Transaction) : TransactionFlowState()
}

@Composable
fun TransactionsScreen() {
    var flowState by remember { mutableStateOf<TransactionFlowState>(TransactionFlowState.List) }

    AnimatedContent(
        targetState = flowState,
        label = "TransactionFlow",
        transitionSpec = {
            if (targetState is TransactionFlowState.List) {
                slideInHorizontally { -it } + fadeIn() togetherWith slideOutHorizontally { it } + fadeOut()
            } else {
                slideInHorizontally { it } + fadeIn() togetherWith slideOutHorizontally { -it } + fadeOut()
            }
        }
    ) { state ->
        when (state) {
            is TransactionFlowState.List -> {
                TransactionsListScreen(
                    onTransactionClick = { flowState = TransactionFlowState.Details(it) }
                )
            }
            is TransactionFlowState.Details -> {
                TransactionDetailsScreen(
                    transaction = state.transaction,
                    onBack = { flowState = TransactionFlowState.List },
                    onEdit = { flowState = TransactionFlowState.Edit(state.transaction) }
                )
            }
            is TransactionFlowState.Edit -> {
                EditTransactionScreen(
                    transaction = state.transaction,
                    onBack = { flowState = TransactionFlowState.Details(state.transaction) },
                    onSave = { flowState = TransactionFlowState.List }
                )
            }
        }
    }
}

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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionDetailsScreen(transaction: Transaction, onBack: () -> Unit, onEdit: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Transaction Details", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) { Icon(Icons.AutoMirrored.Rounded.ArrowBack, null) }
                },
                actions = {
                    IconButton(onClick = {}) { Icon(Icons.Rounded.Share, null) }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier.fillMaxSize().padding(paddingValues).padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier.size(80.dp).background(Color(0xFFEF4444).copy(0.1f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(transaction.icon, null, tint = Color(0xFFEF4444), modifier = Modifier.size(40.dp))
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(transaction.title, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
            Text(
                text = "${if (transaction.type == TransactionType.INCOME) "+" else "-"} ₹${transaction.amount.toInt()}",
                style = MaterialTheme.typography.displaySmall,
                fontWeight = FontWeight.Bold,
                color = if (transaction.type == TransactionType.INCOME) Color(0xFF10B981) else Color(0xFFEF4444)
            )
            Surface(
                color = if (transaction.type == TransactionType.INCOME) Color(0xFF10B981).copy(0.1f) else Color(0xFFEF4444).copy(0.1f),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.padding(top = 8.dp)
            ) {
                Text(
                    transaction.type.name,
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                    style = MaterialTheme.typography.labelSmall,
                    color = if (transaction.type == TransactionType.INCOME) Color(0xFF10B981) else Color(0xFFEF4444)
                )
            }

            Spacer(modifier = Modifier.height(32.dp))
            
            DetailRow("Date", transaction.date)
            DetailRow("Time", transaction.time)
            DetailRow("Payment Method", transaction.paymentMethod)
            DetailRow("Category", transaction.category)
            DetailRow("Notes", transaction.note.ifEmpty { "No notes added" })
            
            Spacer(modifier = Modifier.weight(1f))
            
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                var showDeleteDialog by remember { mutableStateOf(false) }

                if (showDeleteDialog) {
                    AlertDialog(
                        onDismissRequest = { showDeleteDialog = false },
                        title = { Text("Delete Transaction?") },
                        text = { Text("Are you sure you want to delete this transaction? This action cannot be undone.") },
                        confirmButton = {
                            Button(
                                onClick = {
                                    showDeleteDialog = false
                                    onBack()
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFEF4444))
                            ) {
                                Text("Delete")
                            }
                        },
                        dismissButton = {
                            TextButton(onClick = { showDeleteDialog = false }) {
                                Text("Cancel")
                            }
                        }
                    )
                }

                OutlinedButton(onClick = onEdit, modifier = Modifier.weight(1f), shape = RoundedCornerShape(12.dp)) {
                    Text("Edit")
                }
                Button(
                    onClick = { showDeleteDialog = true },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFEF4444)),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Delete")
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditTransactionScreen(transaction: Transaction, onBack: () -> Unit, onSave: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Edit Transaction", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) { Icon(Icons.Rounded.Close, null) }
                },
                actions = {
                    TextButton(onClick = onSave) { Text("Save", color = Color(0xFF0F766E), fontWeight = FontWeight.Bold) }
                }
            )
        }
    ) { paddingValues ->
        Column(modifier = Modifier.fillMaxSize().padding(paddingValues).padding(16.dp)) {
            Text("Type", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
            Row(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                FilterChip(selected = false, onClick = {}, label = { Text("Income") }, modifier = Modifier.weight(1f))
                FilterChip(selected = true, onClick = {}, label = { Text("Expense") }, modifier = Modifier.weight(1f))
            }
            
            OutlinedTextField(
                value = transaction.amount.toString(),
                onValueChange = {},
                label = { Text("Amount") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = transaction.title,
                onValueChange = {},
                label = { Text("Category") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                trailingIcon = { Icon(Icons.Rounded.KeyboardArrowDown, null) }
            )
            
            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = onSave,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0F766E))
            ) {
                Text("Save Changes")
            }
        }
    }
}

@Composable
fun DetailRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Text(value, fontWeight = FontWeight.SemiBold)
    }
}

@Composable
fun SectionHeader(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleSmall,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(vertical = 8.dp)
    )
}

@Composable
fun TransactionListItem(transaction: Transaction, onClick: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth().clickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier.size(48.dp).background(MaterialTheme.colorScheme.surfaceVariant, RoundedCornerShape(12.dp)),
            contentAlignment = Alignment.Center
        ) {
            Icon(transaction.icon, null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(24.dp))
        }
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(transaction.title, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.SemiBold)
            Text("${transaction.date} • ${transaction.time}", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
        Column(horizontalAlignment = Alignment.End) {
            Text(
                text = "${if (transaction.type == TransactionType.INCOME) "+" else "-"} ₹${transaction.amount.toInt()}",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
                color = if (transaction.type == TransactionType.INCOME) Color(0xFF10B981) else Color(0xFFEF4444)
            )
            Text(transaction.paymentMethod, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}

@Composable
fun TransactionsTopBar(onSearchClick: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text("Transactions", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
        Row {
            IconButton(onClick = onSearchClick) { Icon(Icons.Rounded.Search, null) }
            IconButton(onClick = {}) { Icon(Icons.Rounded.FilterList, null) }
        }
    }
}

@Composable
fun SearchTopBar(query: String, onQueryChange: (String) -> Unit, onClose: () -> Unit) {
    TextField(
        value = query, onValueChange = onQueryChange, modifier = Modifier.fillMaxWidth(),
        placeholder = { Text("Search transactions...") },
        leadingIcon = { Icon(Icons.Rounded.Search, null) },
        trailingIcon = { IconButton(onClick = onClose) { Icon(Icons.Rounded.Close, null) } },
        colors = TextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.surface,
            unfocusedContainerColor = MaterialTheme.colorScheme.surface,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        singleLine = true
    )
}

@Composable
fun TransactionsSummaryBar(income: String, expense: String, savings: String) {
    Card(
        modifier = Modifier.padding(horizontal = 16.dp).fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(modifier = Modifier.padding(12.dp).fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            TransactionSummaryItem("Total Income", income, Color(0xFF10B981))
            TransactionSummaryItem("Total Expense", expense, Color(0xFFEF4444))
            TransactionSummaryItem("Net Savings", savings, Color(0xFF3B82F6))
        }
    }
}

@Composable
fun TransactionSummaryItem(label: String, amount: String, color: Color) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(label, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Text("₹$amount", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, color = color)
    }
}
