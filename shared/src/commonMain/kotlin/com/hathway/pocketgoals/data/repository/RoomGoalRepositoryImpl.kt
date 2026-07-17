package com.hathway.pocketgoals.data.repository

import com.hathway.pocketgoals.data.local.dao.GoalDao
import com.hathway.pocketgoals.data.local.entity.GoalEntity
import com.hathway.pocketgoals.domain.Goal
import com.hathway.pocketgoals.domain.repository.GoalRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Home
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb

class RoomGoalRepositoryImpl(
    private val goalDao: GoalDao
) : GoalRepository {
    override fun getGoals(): Flow<List<Goal>> {
        return goalDao.getAllGoals().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun addGoal(goal: Goal) {
        goalDao.insertGoal(goal.toEntity())
    }

    override suspend fun deleteGoal(id: String) {
        id.toLongOrNull()?.let { goalDao.deleteGoal(it) }
    }
}

// Temporary mappers until we have a proper icon/color registry
private fun GoalEntity.toDomain(): Goal {
    return Goal(
        id = id.toString(),
        name = name,
        icon = Icons.Rounded.Home, // Placeholder
        color = Color(color.toLong(16)), 
        targetAmount = targetAmount,
        savedAmount = currentAmount,
        deadline = deadline.toString() // Needs formatting
    )
}

private fun Goal.toEntity(): GoalEntity {
    return GoalEntity(
        id = if (id.isEmpty()) 0 else id.toLong(),
        name = name,
        targetAmount = targetAmount,
        currentAmount = savedAmount,
        categoryId = 0,
        deadline = deadline.toLongOrNull() ?: 0,
        color = "FF000000", // Placeholder
        icon = "home" // Placeholder
    )
}
