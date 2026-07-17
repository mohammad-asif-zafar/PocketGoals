package com.hathway.pocketgoals.presentation.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.hathway.pocketgoals.domain.Goal
import com.hathway.pocketgoals.domain.repository.GoalRepository
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class GoalsViewModel(
    private val repository: GoalRepository
) : ViewModel() {
    val goals: StateFlow<List<Goal>> = repository.getGoals()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun addGoal(goal: Goal) {
        viewModelScope.launch {
            repository.addGoal(goal)
        }
    }

    fun removeGoal(goal: Goal) {
        viewModelScope.launch {
            repository.deleteGoal(goal.id)
        }
    }
}