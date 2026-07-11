package com.hathway.pocketgoals.presentation.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.hathway.pocketgoals.data.GoalMockData
import com.hathway.pocketgoals.domain.Goal
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class GoalsViewModel : ViewModel() {
    private val _goals = MutableStateFlow(GoalMockData.mockGoals)
    val goals: StateFlow<List<Goal>> = _goals.asStateFlow()

    fun addGoal(goal: Goal) {
        _goals.update { currentGoals ->
            currentGoals + goal
        }
    }

    fun removeGoal(goal: Goal) {
        _goals.update { currentGoals ->
            currentGoals.filter { it.id != goal.id }
        }
    }
}