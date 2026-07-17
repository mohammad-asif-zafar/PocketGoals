package com.hathway.pocketgoals.data.mapper

import com.hathway.pocketgoals.data.local.entity.ExpenseEntity
import com.hathway.pocketgoals.data.local.entity.IncomeEntity
import com.hathway.pocketgoals.domain.model.Transaction
import com.hathway.pocketgoals.domain.model.TransactionType

fun ExpenseEntity.toDomain(): Transaction {
    return Transaction(
        id = id.toString(),
        amount = amount,
        type = TransactionType.EXPENSE,
        date = date.toString(), // Needs proper formatting
        note = note
    )
}

fun IncomeEntity.toDomain(): Transaction {
    return Transaction(
        id = id.toString(),
        amount = amount,
        type = TransactionType.INCOME,
        date = date.toString(), // Needs proper formatting
        note = note
    )
}

fun Transaction.toExpenseEntity(): ExpenseEntity {
    return ExpenseEntity(
        id = if (id.isEmpty()) 0 else id.toLong(),
        amount = amount,
        categoryId = 0, // Needs mapping
        accountId = 0, // Needs mapping
        note = note,
        date = date.toLongOrNull() ?: 0
    )
}

fun Transaction.toIncomeEntity(): IncomeEntity {
    return IncomeEntity(
        id = if (id.isEmpty()) 0 else id.toLong(),
        amount = amount,
        categoryId = 0, // Needs mapping
        accountId = 0, // Needs mapping
        note = note,
        date = date.toLongOrNull() ?: 0
    )
}
