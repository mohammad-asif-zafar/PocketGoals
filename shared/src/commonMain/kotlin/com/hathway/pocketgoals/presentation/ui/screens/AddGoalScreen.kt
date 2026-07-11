package com.hathway.pocketgoals.presentation.ui.screens

import androidx.compose.runtime.Composable
import com.hathway.pocketgoals.domain.Goal
import com.hathway.pocketgoals.domain.Transaction
import com.hathway.pocketgoals.presentation.ui.navigation_content.AddGoalContent
import com.hathway.pocketgoals.presentation.ui.viewmodel.GoalsViewModel

@Composable
fun AddGoalScreen(
    viewModel: GoalsViewModel,
    onBackClick: () -> Unit,
    onViewGoal: () -> Unit,
    onSaveTransaction: (Transaction) -> Unit
) {
    AddGoalContent(
        onBackClick = onBackClick,
        onViewGoal = onViewGoal,
        onSaveGoal = { viewModel.addGoal(it) },
        onSaveTransaction = onSaveTransaction
    )
}