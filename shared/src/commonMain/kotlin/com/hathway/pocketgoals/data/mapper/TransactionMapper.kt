package com.hathway.pocketgoals.data.mapper

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Image
import com.hathway.pocketgoals.data.local.entity.ExpenseEntity
import com.hathway.pocketgoals.data.local.entity.IncomeEntity
import com.hathway.pocketgoals.domain.model.Transaction
import com.hathway.pocketgoals.domain.model.TransactionType

import com.hathway.pocketgoals.presentation.ui.icons.IconHelper

fun ExpenseEntity.toDomain(): Transaction {
    return Transaction(
        id = "exp_$id",
        title = title,
        amount = amount,
        type = TransactionType.EXPENSE,
        date = date,
        time = time,
        category = category,
        paymentMethod = paymentMethod,
        note = note,
        icon = IconHelper.getIconByName(iconName),
        createdAt = createdAt
    )
}

fun IncomeEntity.toDomain(): Transaction {
    return Transaction(
        id = "inc_$id",
        title = title,
        amount = amount,
        type = TransactionType.INCOME,
        date = date,
        time = time,
        category = category,
        paymentMethod = paymentMethod,
        note = note,
        icon = IconHelper.getIconByName(iconName),
        createdAt = createdAt
    )
}

fun Transaction.toExpenseEntity(): ExpenseEntity {
    val rawId = id.removePrefix("exp_").removePrefix("goal_")
    return ExpenseEntity(
        id = if (rawId.isEmpty() || rawId.any { !it.isDigit() } || rawId.length > 15) 0 else rawId.toLong(),
        title = title,
        amount = amount,
        date = date,
        time = time,
        category = category,
        paymentMethod = paymentMethod,
        note = note,
        iconName = IconHelper.getIconName(icon),
        categoryId = 0,
        accountId = 0,
        createdAt = if (createdAt == 0L) kotlinx.datetime.Clock.System.now().toEpochMilliseconds() else createdAt
    )
}

fun Transaction.toIncomeEntity(): IncomeEntity {
    val rawId = id.removePrefix("inc_").removePrefix("goal_")
    return IncomeEntity(
        id = if (rawId.isEmpty() || rawId.any { !it.isDigit() } || rawId.length > 15) 0 else rawId.toLong(),
        title = title,
        amount = amount,
        date = date,
        time = time,
        category = category,
        paymentMethod = paymentMethod,
        note = note,
        iconName = IconHelper.getIconName(icon),
        categoryId = 0,
        accountId = 0,
        createdAt = if (createdAt == 0L) kotlinx.datetime.Clock.System.now().toEpochMilliseconds() else createdAt
    )
}
