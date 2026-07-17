package com.hathway.pocketgoals.presentation.ui.screens

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hathway.pocketgoals.data.GoalMockData
import com.hathway.pocketgoals.data.repository.MockGoalRepositoryImpl
import com.hathway.pocketgoals.domain.model.ThemeMode
import com.hathway.pocketgoals.presentation.ui.components.goals_components.GoalItem
import com.hathway.pocketgoals.presentation.ui.components.goals_components.GoalsTopBar
import com.hathway.pocketgoals.presentation.ui.theme.PocketGoalsTheme
import com.hathway.pocketgoals.presentation.ui.viewmodel.GoalsViewModel
import androidx.compose.runtime.collectAsState
import org.jetbrains.compose.resources.stringResource
import pocketgoals.shared.generated.resources.*

@Composable
fun GoalsScreen(
    viewModel: GoalsViewModel,
    onAddGoalClick: () -> Unit
) {
    val goals by viewModel.goals.collectAsState()

    Scaffold(
        topBar = {
            GoalsTopBar(onAddGoalClick = onAddGoalClick)
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        if (goals.isEmpty()) {
            EmptyGoalsState(onAddGoalClick = onAddGoalClick, modifier = Modifier.padding(paddingValues))
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(paddingValues)
                    .background(MaterialTheme.colorScheme.background),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(items = goals, key = { it.id }) { goal ->
                    val dismissState = rememberSwipeToDismissBoxState(
                        confirmValueChange = { dismissValue ->
                            if (dismissValue == SwipeToDismissBoxValue.EndToStart) {
                                viewModel.removeGoal(goal)
                                true
                            } else {
                                false
                            }
                        })

                    SwipeToDismissBox(
                        state = dismissState,
                        enableDismissFromStartToEnd = false,
                        backgroundContent = {
                            val backgroundTint by animateColorAsState(
                                targetValue = when (dismissState.targetValue) {
                                    SwipeToDismissBoxValue.EndToStart -> MaterialTheme.colorScheme.error
                                    else -> MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
                                }
                            )

                            Box(
                                modifier = Modifier.fillMaxSize()
                                    .background(backgroundTint, shape = MaterialTheme.shapes.large)
                                    .padding(horizontal = 20.dp), contentAlignment = Alignment.CenterEnd
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = "Remove Goal",
                                    tint = if (dismissState.targetValue == SwipeToDismissBoxValue.EndToStart) {
                                        MaterialTheme.colorScheme.onError
                                    } else {
                                        MaterialTheme.colorScheme.error
                                    }
                                )
                            }
                        },
                        content = {
                            GoalItem(
                                goal = goal, onClick = { /* Handle dashboard item clicking routing */ })
                        })
                }

                item {
                    Spacer(modifier = Modifier.height(100.dp))
                }
            }
        }
    }
}

@Composable
fun EmptyGoalsState(
    onAddGoalClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            stringResource(Res.string.no_goals_title),
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )
        Text(
            stringResource(Res.string.no_goals_desc),
            textAlign = androidx.compose.ui.text.style.TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(top = 8.dp, bottom = 32.dp)
        )
        
        Button(
            onClick = onAddGoalClick,
            modifier = Modifier.fillMaxWidth().height(56.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            Icon(Icons.Default.Add, null)
            Spacer(modifier = Modifier.width(8.dp))
            Text(stringResource(Res.string.btn_get_started), fontWeight = FontWeight.Bold)
        }
    }
}

@Preview
@Composable
fun GoalsScreenDashboardPreview() {
    PocketGoalsTheme(themeMode = ThemeMode.LIGHT) {
        GoalsScreen(
            viewModel = GoalsViewModel(MockGoalRepositoryImpl()),
            onAddGoalClick = {}
        )
    }
}
