package com.hathway.pocketgoals.data.repository

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.hathway.pocketgoals.data.local.dao.GoalDao
import com.hathway.pocketgoals.data.local.entity.GoalEntity
import com.hathway.pocketgoals.domain.Goal
import com.hathway.pocketgoals.domain.repository.GoalRepository
import com.hathway.pocketgoals.presentation.ui.icons.IconHelper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.Clock

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
        icon = IconHelper.getIconByName(icon),
        color = Color(color.toLong(16)), 
        targetAmount = targetAmount,
        savedAmount = currentAmount,
        deadline = deadline.toString()
    )
}

private fun Goal.toEntity(): GoalEntity {
    val rawId = id.toLongOrNull() ?: 0L
    return GoalEntity(
        id = if (rawId > 0) rawId else 0L,
        name = name,
        targetAmount = targetAmount,
        currentAmount = savedAmount,
        categoryId = 0,
        deadline = 0, // Should parse deadline string if needed
        color = color.toArgb().toUInt().toString(16).uppercase(),
        icon = IconHelper.getIconName(icon),
        createdAt = Clock.System.now().toEpochMilliseconds()
    )
}
