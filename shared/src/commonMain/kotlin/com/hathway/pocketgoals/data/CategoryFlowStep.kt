package com.hathway.pocketgoals.data

import com.hathway.pocketgoals.domain.ExpenseCategory

sealed class CategoryFlowStep {
    data object Selection : CategoryFlowStep()
    data object Add : CategoryFlowStep()
    data class Edit(val category: ExpenseCategory) : CategoryFlowStep()
    data class Delete(val category: ExpenseCategory) : CategoryFlowStep()
}