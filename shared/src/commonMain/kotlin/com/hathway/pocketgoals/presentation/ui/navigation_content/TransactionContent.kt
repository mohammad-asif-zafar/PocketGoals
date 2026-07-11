package com.hathway.pocketgoals.presentation.ui.navigation_content

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.hathway.pocketgoals.presentation.ui.components.transactions_components.EditTransactionScreen
import com.hathway.pocketgoals.presentation.ui.components.transactions_components.TransactionDetailsScreen
import com.hathway.pocketgoals.presentation.ui.components.transactions_components.TransactionsListScreen
import com.hathway.pocketgoals.presentation.ui.state.TransactionFlowState
import com.hathway.pocketgoals.presentation.ui.viewmodel.TransactionsViewModel

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
                    viewModel = viewModel,
                    onTransactionClick = { flowState = TransactionFlowState.Details(it) })
            }

            is TransactionFlowState.Details -> {
                TransactionDetailsScreen(
                    transaction = state.transaction,
                    onBack = { flowState = TransactionFlowState.List },
                    onEdit = { flowState = TransactionFlowState.Edit(state.transaction) })
            }

            is TransactionFlowState.Edit -> {
                EditTransactionScreen(
                    transaction = state.transaction,
                    onBack = { flowState = TransactionFlowState.Details(state.transaction) },
                    onSave = { flowState = TransactionFlowState.List })
            }
        }
    }
}
