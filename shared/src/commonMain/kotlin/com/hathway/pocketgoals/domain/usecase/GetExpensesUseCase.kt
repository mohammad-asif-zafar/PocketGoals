package com.hathway.pocketgoals.domain.usecase

import com.hathway.pocketgoals.domain.model.Transaction
import com.hathway.pocketgoals.domain.repository.ExpenseRepository
import kotlinx.coroutines.flow.Flow

class GetExpensesUseCase(
    private val repository: ExpenseRepository
) {
    operator fun invoke(): Flow<List<Transaction>> {
        return repository.getAllExpenses()
    }
}
