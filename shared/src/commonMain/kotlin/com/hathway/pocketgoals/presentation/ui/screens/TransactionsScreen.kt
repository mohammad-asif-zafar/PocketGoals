package com.hathway.pocketgoals.presentation.ui.screens

import androidx.compose.runtime.Composable
import com.hathway.pocketgoals.presentation.ui.navigation_content.TransactionContent
import com.hathway.pocketgoals.presentation.ui.viewmodel.TransactionsViewModel

@Composable
fun TransactionsScreen(viewModel: TransactionsViewModel) {
    TransactionContent(viewModel = viewModel)
}