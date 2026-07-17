package com.hathway.pocketgoals.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Entity(tableName = "incomes")
@Serializable
data class IncomeEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val amount: Double,
    val categoryId: Long,
    val accountId: Long,
    val note: String,
    val date: Long,
    val createdAt: Long = 0
)
