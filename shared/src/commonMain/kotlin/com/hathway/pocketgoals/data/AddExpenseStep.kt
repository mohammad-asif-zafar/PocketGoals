package com.hathway.pocketgoals.data

sealed class AddExpenseStep {
    data object Form : AddExpenseStep()
    data object Summary : AddExpenseStep()
    data object Success : AddExpenseStep()
}