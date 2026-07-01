package com.hathway.pocketgoals.presentation.ui.navigation_content

import androidx.compose.runtime.Composable
import com.hathway.pocketgoals.presentation.ui.screens.AddExpenseScreen

@Composable
fun AddExpenseContent() {
    AddExpenseScreen(
        onBackClick = {
            // Logic to handle back navigation if necessary
        },
        onViewTransaction = {
            // Logic to navigate to transactions view
        }
    )
}
