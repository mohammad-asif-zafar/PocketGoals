package com.hathway.pocketgoals.data

sealed class AddIncomeStep {
    data object TypeSelection : AddIncomeStep()
    data object Form : AddIncomeStep()
    data object AmountInput : AddIncomeStep()
    data object DateSelection : AddIncomeStep()
    data object MethodSelection : AddIncomeStep()
    data object NoteInput : AddIncomeStep()
    data object Summary : AddIncomeStep()
    data object Success : AddIncomeStep()
}