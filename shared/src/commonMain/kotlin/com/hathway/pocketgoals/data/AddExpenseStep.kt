package com.hathway.pocketgoals.data

sealed class AddExpenseStep {
    data object Form : AddExpenseStep()
    data object CategorySelection : AddExpenseStep()
    data object AmountInput : AddExpenseStep()
    data object DateSelection : AddExpenseStep()
    data object MethodSelection : AddExpenseStep()
    data object NoteInput : AddExpenseStep()
    data object Summary : AddExpenseStep()
    data object Success : AddExpenseStep()
}