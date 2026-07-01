package com.hathway.pocketgoals.domain

import androidx.compose.ui.graphics.vector.ImageVector

enum class TransactionType {
    INCOME, EXPENSE
}

data class Transaction(
    val id: String,
    val title: String,
    val amount: Double,
    val type: TransactionType,
    val date: String,
    val time: String,
    val category: String,
    val paymentMethod: String,
    val note: String = "",
    val icon: ImageVector
)
