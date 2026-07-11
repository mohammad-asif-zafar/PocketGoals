package com.hathway.pocketgoals.presentation.ui.navigation_content

import androidx.compose.runtime.Composable
import com.hathway.pocketgoals.presentation.ui.screens.GoalsScreen
import com.hathway.pocketgoals.presentation.ui.viewmodel.GoalsViewModel

@Composable
fun GoalsContent(viewModel: GoalsViewModel,
                 onAddGoalClick: () -> Unit) {
    GoalsScreen(viewModel = viewModel, onAddGoalClick ={onAddGoalClick()})
}
