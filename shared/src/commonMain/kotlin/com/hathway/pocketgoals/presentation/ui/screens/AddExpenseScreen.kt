package com.hathway.pocketgoals.presentation.ui.screens

import androidx.compose.runtime.Composable
import com.hathway.pocketgoals.presentation.ui.navigation_content.AddExpenseContent

@Composable
fun AddExpenseScreen(
    onBackClick: () -> Unit, onViewTransaction: () -> Unit

) {
    AddExpenseContent(onBackClick = onBackClick, onViewTransaction = onViewTransaction)
}