package com.hathway.pocketgoals.domain

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Image
import androidx.compose.ui.graphics.vector.ImageVector

enum class TransactionType {
    INCOME, EXPENSE, GOAL_CREATED
}

data class Transaction(
    val id: String = "",
    val title: String = "",
    val amount: Double = 0.0,
    val type: TransactionType = TransactionType.INCOME,
    val date: String = "",
    val time: String = "",
    val category: String = "",
    val paymentMethod: String = "",
    val note: String = "",
    val icon: ImageVector = Icons.Default.Image
)
