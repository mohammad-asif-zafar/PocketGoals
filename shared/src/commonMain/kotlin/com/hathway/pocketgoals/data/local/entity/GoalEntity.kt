package com.hathway.pocketgoals.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Entity(tableName = "goals")
@Serializable
data class GoalEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val targetAmount: Double,
    val currentAmount: Double = 0.0,
    val categoryId: Long,
    val deadline: Long,
    val color: String, // Hex string
    val icon: String, // Icon identifier
    val createdAt: Long = 0
)
