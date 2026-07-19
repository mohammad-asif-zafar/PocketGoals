package com.hathway.pocketgoals.presentation.ui.navigation_content

import androidx.compose.animation.*
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hathway.pocketgoals.data.AddGoalStep
import com.hathway.pocketgoals.domain.Goal
import com.hathway.pocketgoals.domain.model.Transaction
import com.hathway.pocketgoals.domain.model.TransactionType
import com.hathway.pocketgoals.domain.model.GoalCategory
import com.hathway.pocketgoals.domain.model.ThemeMode
import com.hathway.pocketgoals.presentation.ui.components.add_expense_components.AddExpenseTopBar
import com.hathway.pocketgoals.presentation.ui.components.add_expense_components.DateSelectionStep
import com.hathway.pocketgoals.presentation.ui.components.goals_components.*
import com.hathway.pocketgoals.presentation.ui.theme.PocketGoalsTheme
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.compose.resources.stringResource
import pocketgoals.shared.generated.resources.*
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
@Composable
fun AddGoalContent(
    onBackClick: () -> Unit,
    onViewGoal: () -> Unit,
    onSaveGoal: (Goal) -> Unit,
    onSaveTransaction: (Transaction) -> Unit
) {
    var currentStep by remember { mutableStateOf<AddGoalStep>(AddGoalStep.CategorySelection) }
    var isCustomFlow by remember { mutableStateOf(false) }

    // Goal State
    var goalName by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf<GoalCategory?>(null) }
    var targetAmount by remember { mutableStateOf("0") }
    var selectedDateMillis by remember { mutableStateOf<Long?>(null) }
    var duration by remember { mutableStateOf("1 Year") }
    var description by remember { mutableStateOf("") }
    var motivation by remember { mutableStateOf("") }

    // Custom Goal Specific State
    var customIcon by remember { mutableStateOf(GoalCategory.defaultCategories.first().icon) }
    var customColor by remember { mutableStateOf(GoalCategory.defaultCategories.first().color) }

    var showKeypad by remember { mutableStateOf(false) }

    if (showKeypad) {
        CustomKeypadBottomSheet(
            initialAmount = targetAmount,
            onAmountConfirmed = { targetAmount = it },
            onDismiss = { showKeypad = false }
        )
    }

    val defaultDate = "15 May 2024"
    val dateText = remember(selectedDateMillis) {
        if (selectedDateMillis == null) defaultDate
        else {
            val instant = Instant.fromEpochMilliseconds(selectedDateMillis!!)
            val dt = instant.toLocalDateTime(TimeZone.currentSystemDefault())
            "${dt.dayOfMonth} ${
                dt.month.name.lowercase().replaceFirstChar { it.uppercase() }
            } ${dt.year}"
        }
    }

    Scaffold(
        topBar = {
            if (currentStep !is AddGoalStep.GoalSuccess) {
                val title = when (currentStep) {
                    AddGoalStep.CategorySelection -> stringResource(Res.string.create_goal)
                    AddGoalStep.GoalDetails -> stringResource(Res.string.set_details)
                    AddGoalStep.CustomGoalDetails -> stringResource(Res.string.create_custom_goal)
                    AddGoalStep.GoalAmount -> stringResource(Res.string.set_target_amount)
                    AddGoalStep.GoalTimeline -> stringResource(Res.string.set_timeline)
                    AddGoalStep.DateSelection -> stringResource(Res.string.target_date_label)
                    AddGoalStep.CustomGoalMotivation -> stringResource(Res.string.add_description)
                    AddGoalStep.GoalReview -> stringResource(Res.string.review_goal)
                    else -> stringResource(Res.string.create_goal)
                }

                AddExpenseTopBar(
                    title = title, onBack = {
                        when (currentStep) {
                            AddGoalStep.CategorySelection -> onBackClick()
                            AddGoalStep.GoalDetails -> currentStep = AddGoalStep.CategorySelection
                            AddGoalStep.CustomGoalDetails -> currentStep =
                                AddGoalStep.CategorySelection

                            AddGoalStep.GoalAmount -> {
                                currentStep = if (isCustomFlow) AddGoalStep.CustomGoalDetails
                                else AddGoalStep.GoalDetails
                            }

                            AddGoalStep.GoalTimeline -> currentStep = AddGoalStep.GoalAmount
                            AddGoalStep.DateSelection -> currentStep = AddGoalStep.GoalTimeline
                            AddGoalStep.CustomGoalMotivation -> currentStep =
                                AddGoalStep.GoalTimeline

                            AddGoalStep.GoalReview -> {
                                currentStep = if (isCustomFlow) AddGoalStep.CustomGoalMotivation
                                else AddGoalStep.GoalTimeline
                            }

                            else -> onBackClick()
                        }
                    })
            }
        }, containerColor = MaterialTheme.colorScheme.background
    ) { padding ->
        AnimatedContent(
            targetState = currentStep,
            modifier = Modifier.padding(padding).fillMaxSize(),
            transitionSpec = {
                val isForward =
                    targetState != AddGoalStep.CategorySelection && targetState != AddGoalStep.GoalDetails && targetState != AddGoalStep.CustomGoalDetails
                val enter =
                    if (isForward) slideInHorizontally { it } + fadeIn() else slideInHorizontally { -it } + fadeIn()
                val exit =
                    if (isForward) slideOutHorizontally { -it } + fadeOut() else slideOutHorizontally { it } + fadeOut()
                enter togetherWith exit using SizeTransform(clip = false)
            },
            label = "AddGoalFlow"
        ) { step ->
            when (step) {
                is AddGoalStep.CategorySelection -> {
                    GoalCategorySelectionStep(
                        selectedCategory = selectedCategory,
                        onCategorySelected = {
                            selectedCategory = it
                            isCustomFlow = it.name == Res.string.category_custom
                            currentStep = if (isCustomFlow) {
                                AddGoalStep.CustomGoalDetails
                            } else {
                                AddGoalStep.GoalDetails
                            }
                        },
                        onCustomGoal = {
                            isCustomFlow = true
                            selectedCategory =
                                GoalCategory.defaultCategories.find { it.name == Res.string.category_custom }
                            currentStep = AddGoalStep.CustomGoalDetails
                        },
                        onNext = {
                            currentStep = if (isCustomFlow) AddGoalStep.CustomGoalDetails
                            else AddGoalStep.GoalDetails
                        })
                }

                is AddGoalStep.GoalDetails -> {
                    GoalDetailsStep(
                        goalName = goalName,
                        onGoalNameChange = { goalName = it },
                        category = selectedCategory!!,
                        onCategoryClick = { currentStep = AddGoalStep.CategorySelection },
                        description = description,
                        onDescriptionChange = { description = it },
                        onNext = { currentStep = AddGoalStep.GoalAmount })
                }

                is AddGoalStep.CustomGoalDetails -> {
                    CustomGoalDetailsStep(
                        goalName = goalName,
                        onGoalNameChange = { goalName = it },
                        selectedIcon = customIcon,
                        onIconSelected = { customIcon = it },
                        selectedColor = customColor,
                        onColorSelected = { customColor = it },
                        onNext = { currentStep = AddGoalStep.GoalAmount })
                }

                is AddGoalStep.GoalAmount -> {
                    GoalAmountStep(
                        amount = targetAmount,
                        onAmountChange = { targetAmount = it },
                        onNext = { currentStep = AddGoalStep.GoalTimeline },
                        onOtherClick = { showKeypad = true },
                        modifier = Modifier.padding(2.dp)
                    )

                }

                is AddGoalStep.GoalTimeline -> {
                    GoalTimelineStep(
                        targetDate = dateText,
                        onDateClick = { currentStep = AddGoalStep.DateSelection },
                        duration = duration,
                        onDurationSelected = { duration = it },
                        onNext = {
                            currentStep = if (isCustomFlow) AddGoalStep.CustomGoalMotivation
                            else AddGoalStep.GoalReview
                        })
                }

                is AddGoalStep.DateSelection -> {
                    DateSelectionStep(
                        selectedDateMillis = selectedDateMillis,
                        onDateSelected = { selectedDateMillis = it },
                        onDone = { currentStep = AddGoalStep.GoalTimeline })
                }

                is AddGoalStep.CustomGoalMotivation -> {
                    CustomGoalMotivationStep(
                        description = description,
                        onDescriptionChange = { description = it },
                        motivation = motivation,
                        onMotivationChange = { motivation = it },
                        onNext = { currentStep = AddGoalStep.GoalReview })
                }

                is AddGoalStep.GoalReview -> {
                    val customCategoryLabel = stringResource(Res.string.category_custom)

                    GoalReviewStep(
                        goalName = goalName,
                        categoryName = (if (isCustomFlow) customCategoryLabel else selectedCategory?.name
                            ?: "") as String,
                        categoryIcon = if (isCustomFlow) customIcon else selectedCategory?.icon
                            ?: GoalCategory.defaultCategories.first().icon,
                        categoryColor = if (isCustomFlow) customColor else selectedCategory?.color
                            ?: GoalCategory.defaultCategories.first().color,
                        targetAmount = targetAmount,
                        targetDate = dateText,
                        duration = duration,
                        onConfirm = {
                            val now = Clock.System.now()
                            val generatedId = now.toEpochMilliseconds().toString()
                            val newGoal = Goal(
                                id = generatedId,
                                name = goalName,
                                icon = if (isCustomFlow) customIcon else selectedCategory?.icon
                                    ?: GoalCategory.defaultCategories.first().icon,
                                color = if (isCustomFlow) customColor else selectedCategory?.color
                                    ?: GoalCategory.defaultCategories.first().color,
                                targetAmount = targetAmount.toDoubleOrNull() ?: 0.0,
                                savedAmount = 0.0,
                                deadline = dateText
                            )
                            onSaveGoal(newGoal)

                            // Activity Log
                            val transaction = Transaction(
                                id = "goal_$generatedId",
                                title = "Created Goal: $goalName",
                                amount = 0.0,
                                type = TransactionType.GOAL_CREATED,
                                date = dateText,
                                time = "12:00 PM",
                                category = "Goals",
                                paymentMethod = "System",
                                icon = newGoal.icon,
                                createdAt = now.toEpochMilliseconds()
                            )
                            onSaveTransaction(transaction)

                            currentStep = AddGoalStep.GoalSuccess
                        },
                        onBack = {
                            currentStep = if (isCustomFlow) AddGoalStep.CustomGoalMotivation
                            else AddGoalStep.GoalTimeline
                        })
                }

                is AddGoalStep.GoalSuccess -> {
                    GoalSuccessStep(
                        goalName = goalName, onViewGoal = onViewGoal
                    )
                }
            }
        }
    }
}

// ==========================================================
// Light & Dark Theme Coordinator Previews Matrix
// ==========================================================

@Preview(name = "Goal Flow - Light Mode", group = "Wizard Flows")
@Composable
private fun AddGoalContentLightPreview() {
    PocketGoalsTheme(themeMode = ThemeMode.LIGHT) {
        Surface(color = MaterialTheme.colorScheme.background) {
            AddGoalContent(
                onBackClick = {},
                onViewGoal = {},
                onSaveGoal = {},
                onSaveTransaction = {}
            )
        }
    }
}

@Preview(name = "Goal Flow - Dark Mode", group = "Wizard Flows")
@Composable
private fun AddGoalContentDarkPreview() {
    PocketGoalsTheme(themeMode = ThemeMode.DARK) {
        Surface(color = MaterialTheme.colorScheme.background) {
            AddGoalContent(
                onBackClick = {},
                onViewGoal = {},
                onSaveGoal = {},
                onSaveTransaction = {}
            )
        }
    }
}