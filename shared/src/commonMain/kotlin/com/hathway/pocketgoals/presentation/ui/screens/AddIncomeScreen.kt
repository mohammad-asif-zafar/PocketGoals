package com.hathway.pocketgoals.presentation.ui.screens

import androidx.compose.runtime.Composable
import com.hathway.pocketgoals.domain.Transaction
import com.hathway.pocketgoals.presentation.ui.navigation_content.AddIncomeContent

@Composable
fun AddIncomeScreen(
    onBackClick: () -> Unit,
    onViewIncome: () -> Unit,
    onSaveTransaction: (Transaction) -> Unit
) {
    AddIncomeContent(
        onBackClick = onBackClick,
        onViewIncome = onViewIncome,
        onSaveTransaction = onSaveTransaction
    )
}