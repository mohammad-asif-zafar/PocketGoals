package com.hathway.pocketgoals.presentation.ui.navigation_content

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.hathway.pocketgoals.domain.model.ThemeMode
import com.hathway.pocketgoals.domain.model.Transaction
import com.hathway.pocketgoals.domain.model.TransactionType
import com.hathway.pocketgoals.presentation.ui.components.transactions_components.EditTransactionScreen
import com.hathway.pocketgoals.presentation.ui.components.transactions_components.TransactionDetailsScreen
import com.hathway.pocketgoals.presentation.ui.components.transactions_components.TransactionsListScreen
import com.hathway.pocketgoals.presentation.ui.state.TransactionFlowState
import com.hathway.pocketgoals.presentation.ui.theme.PocketGoalsTheme
import com.hathway.pocketgoals.presentation.ui.viewmodel.TransactionsViewModel
import org.koin.compose.koinInject

@Composable
fun TransactionContent(viewModel: TransactionsViewModel) {

    var flowState by remember { mutableStateOf<TransactionFlowState>(TransactionFlowState.List) }

    AnimatedContent(
        targetState = flowState, label = "TransactionFlow", transitionSpec = {
            if (targetState is TransactionFlowState.List) {
                slideInHorizontally { -it } + fadeIn() togetherWith slideOutHorizontally { it } + fadeOut()
            } else {
                slideInHorizontally { it } + fadeIn() togetherWith slideOutHorizontally { -it } + fadeOut()
            }
        }) { state ->
        when (state) {
            is TransactionFlowState.List -> {
                TransactionsListScreen(
                    viewModel = viewModel, onTransactionClick = {
                        flowState = TransactionFlowState.Details(it)
                    })
            }

            is TransactionFlowState.Details -> {
                TransactionDetailsScreen(
                    transaction = state.transaction,
                    onBack = { flowState = TransactionFlowState.List },
                    onEdit = { flowState = TransactionFlowState.Edit(state.transaction) },
                    onDelete = {
                        viewModel.deleteTransaction(state.transaction.id)
                        flowState = TransactionFlowState.List
                    })
            }

            is TransactionFlowState.Edit -> {
                EditTransactionScreen(
                    transaction = state.transaction,
                    onBack = { flowState = TransactionFlowState.Details(state.transaction) },
                    onSave = { updated ->
                        viewModel.updateTransaction(updated)
                        flowState = TransactionFlowState.List
                    })
            }
        }
    }
}

// ==========================================================
// 🔹 Global Mock Data Objects
// ==========================================================
val MockIncomeTransaction = Transaction(
    id = "tx_01",
    title = "Salary Credit",
    amount = 4500.0,
    type = TransactionType.INCOME,
    date = "19 July 2026",
    time = "10:00 AM",
    category = "Salary",
    paymentMethod = "Bank Transfer",
    note = "Monthly base payout",
    icon = Icons.Default.Home
)

val MockExpenseTransaction = Transaction(
    id = "tx_02",
    title = "Grocery Shopping",
    amount = 125.50,
    type = TransactionType.EXPENSE,
    date = "18 July 2026",
    time = "04:30 PM",
    category = "Food",
    paymentMethod = "Debit Card",
    note = "Weekly supermarket restocking",
    icon = Icons.Default.ShoppingCart
)

@Preview
@Composable
fun TransactionContentListLightPreview() {
    PocketGoalsTheme(themeMode = ThemeMode.LIGHT) {
        Box(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
            // Renders the main dashboard list layout view directly
            val transactionsViewModel: TransactionsViewModel = koinInject()
            TransactionsListScreen(
                onTransactionClick = {}, viewModel = transactionsViewModel

            )
        }
    }
}

@Preview
@Composable
fun TransactionDetailsDarkRtlPreview() {
    // Validating dynamic Arabic RTL mirror layout rendering for transaction details
    PocketGoalsTheme(themeMode = ThemeMode.DARK) {
        Box(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
            TransactionDetailsScreen(
                transaction = MockExpenseTransaction,
                onBack = {},
                onEdit = {},
                onDelete = {})
        }
    }
}

@Preview
@Composable
fun TransactionEditScreenLightPreview() {
    // Validating Urdu script scale alignments on text input fields
    PocketGoalsTheme(themeMode = ThemeMode.LIGHT) {
        Box(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
            EditTransactionScreen(
                transaction = MockIncomeTransaction,
                onBack = {},
                onSave = {})
        }
    }
}