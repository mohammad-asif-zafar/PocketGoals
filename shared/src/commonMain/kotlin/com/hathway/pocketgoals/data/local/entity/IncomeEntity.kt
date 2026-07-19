package com.hathway.pocketgoals.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Entity(tableName = "incomes")
@Serializable
data class IncomeEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val amount: Double,
    val date: String,
    val time: String,
    val category: String,
    val paymentMethod: String,
    val note: String,
    val iconName: String,
    val categoryId: Long = 0,
    val accountId: Long = 0,
    val createdAt: Long = 0
)
