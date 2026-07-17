package com.hathway.pocketgoals.data.repository

import com.hathway.pocketgoals.data.GoalMockData
import com.hathway.pocketgoals.domain.Goal
import com.hathway.pocketgoals.domain.repository.GoalRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MockGoalRepositoryImpl : GoalRepository {
    private val _goals = MutableStateFlow(GoalMockData.mockGoals)
    
    override fun getGoals(): Flow<List<Goal>> = _goals.asStateFlow()

    override suspend fun addGoal(goal: Goal) {
        _goals.update { current -> current + goal }
    }

    override suspend fun deleteGoal(id: String) {
        _goals.update { current -> current.filter { it.id != id } }
    }
}
