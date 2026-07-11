package com.hathway.pocketgoals.data

sealed class AddGoalStep {
    data object CategorySelection : AddGoalStep()
    data object GoalDetails : AddGoalStep()
    data object CustomGoalDetails : AddGoalStep()
    data object GoalAmount : AddGoalStep()
    data object GoalTimeline : AddGoalStep()
    data object DateSelection : AddGoalStep()
    data object CustomGoalMotivation : AddGoalStep()
    data object GoalReview : AddGoalStep()
    data object GoalSuccess : AddGoalStep()
}