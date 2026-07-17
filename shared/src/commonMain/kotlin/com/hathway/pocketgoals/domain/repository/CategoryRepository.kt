package com.hathway.pocketgoals.domain.repository

import com.hathway.pocketgoals.domain.model.GoalCategory
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
    fun getAllCategories(): Flow<List<GoalCategory>>
    suspend fun insertCategory(category: GoalCategory)
    suspend fun deleteCategory(id: Long)
}
