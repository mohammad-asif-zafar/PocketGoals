package com.hathway.pocketgoals.presentation.ui.screens

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hathway.pocketgoals.data.GoalMockData
import com.hathway.pocketgoals.domain.Goal
import com.hathway.pocketgoals.presentation.ui.components.goals_components.GoalItem
import com.hathway.pocketgoals.presentation.ui.components.goals_components.GoalsTopBar
import com.hathway.pocketgoals.presentation.ui.theme.PocketGoalsTheme
import kotlin.time.Clock


@Composable
fun GoalsScreen() {
    // Reactive multi-platform list initialization
    val goals = remember { mutableStateListOf(*GoalMockData.mockGoals.toTypedArray()) }

    // Visibility flag for adding a new item dialog sheet
    var showAddDialog by remember { mutableStateOf(false) }

    // Renders the input pop-up overlay when active
    if (showAddDialog) {
        AddGoalDialog(
            onDismissRequest = { showAddDialog = false },
            // Fixed: Captured the third closure parameter 'icon' passed from the new dialog design
            onGoalConfirm = { name, targetAmount, icon ->
                val uniqueId = (Clock.System.now().toEpochMilliseconds()).toString()
                val dynamicGoal = Goal(
                    id = uniqueId,
                    name = name,
                    targetAmount = targetAmount,
                    savedAmount = 0.0,
                    deadline = "2026-12-31", // Fallback template date
                    icon = icon,             // Fixed: Assigned the user-selected icon here
                    color = Color(0xFF3B82F6) // Template visual color
                )
                goals.add(dynamicGoal)
                showAddDialog = false
            })
    }

    Scaffold(
        topBar = {
            GoalsTopBar(onAddGoalClick = { showAddDialog = true })
        }, containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(paddingValues)
                .background(MaterialTheme.colorScheme.background),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(
                items = goals, key = { it.id }) { goal ->

                // Swipe-to-delete state engine
                val dismissState = rememberSwipeToDismissBoxState(
                    confirmValueChange = { dismissValue ->
                        if (dismissValue == SwipeToDismissBoxValue.EndToStart) {
                            goals.remove(goal)
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

@Composable
fun AddGoalDialog(
    onDismissRequest: () -> Unit,
    onGoalConfirm: (name: String, targetAmount: Double, selectedIcon: ImageVector) -> Unit
) {
    var goalName by remember { mutableStateOf("") }
    var targetAmountString by remember { mutableStateOf("") }

    // Feature: Available choices for the goal icon
    val iconOptions = remember {
        listOf(
            Icons.Default.Star,
            Icons.Default.ShoppingCart,
            Icons.Default.Home,
            Icons.Default.LocationOn,
            Icons.Default.Lock,
            Icons.Default.List
        )
    }
    // Track chosen selection state (defaults to first item)
    var selectedIcon by remember { mutableStateOf(iconOptions.first()) }

    val isFormValid = goalName.isNotBlank() && (targetAmountString.toDoubleOrNull() ?: 0.0) > 0.0

    AlertDialog(onDismissRequest = onDismissRequest, title = {
        Text(
            text = "Create New Goal", style = MaterialTheme.typography.titleLarge
        )
    }, text = {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp), modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                value = goalName,
                onValueChange = { goalName = it },
                label = { Text("Goal Name") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = targetAmountString,
                onValueChange = { input ->
                    if (input.isEmpty() || input.toDoubleOrNull() != null) {
                        targetAmountString = input
                    }
                },
                label = { Text("Target Amount (₹)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            // Subtitle for Icon Selection Section
            Text(
                text = "Select Icon",
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            // Feature: Horizontal Grid container for choosing icons
            LazyVerticalGrid(
                columns = GridCells.Fixed(4), // 4 columns grid layout
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth().height(110.dp) // Keeps dialog boundaries safe
            ) {
                items(iconOptions) { icon ->
                    val isSelected = icon == selectedIcon

                    Box(
                        modifier = Modifier.size(48.dp).clip(RoundedCornerShape(12.dp))
                            // Highlight selected items with a subtle background accent
                            .background(
                                if (isSelected) MaterialTheme.colorScheme.primaryContainer
                                else Color.Transparent
                            ).border(
                                2.dp,
                                if (isSelected) MaterialTheme.colorScheme.primary
                                else MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f),
                                RoundedCornerShape(12.dp)
                            ).clickable { selectedIcon = icon }, contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = icon,
                            contentDescription = null,
                            tint = if (isSelected) MaterialTheme.colorScheme.primary
                            else MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            }
        }
    }, dismissButton = {
        TextButton(onClick = onDismissRequest) { Text("Cancel") }
    }, confirmButton = {
        Button(
            onClick = {
                val amount = targetAmountString.toDoubleOrNull() ?: 0.0
                onGoalConfirm(goalName, amount, selectedIcon)
            }, enabled = isFormValid
        ) {
            Text("Save Goal")
        }
    })
}


@Preview
@Composable
fun AddGoalDialogVisualPreview() {
    Column(
        verticalArrangement = Arrangement.spacedBy(32.dp),
        modifier = Modifier.background(Color(0xFF0F172A)) // Dark base container to frame the active dialog cards beautifully
            .padding(24.dp)
    ) {
        // --- 1. LIGHT SYSTEM DESIGN ENVIRONMENT ---
        Column {
            Text(
                text = "LIGHT THEME DIALOG OVERLAY",
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            PocketGoalsTheme(darkTheme = false) {
                Box(
                    modifier = Modifier.fillMaxWidth()
                        .height(380.dp) // Formats display space safely to preview nested elements
                ) {
                    AddGoalDialog(onDismissRequest = {}, onGoalConfirm = { _, _, _ -> })
                }
            }
        }

        // --- 2. DARK SYSTEM DESIGN ENVIRONMENT ---
        Column {
            Text(
                text = "DARK THEME DIALOG OVERLAY",
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            PocketGoalsTheme(darkTheme = true) {
                Box(
                    modifier = Modifier.fillMaxWidth().height(380.dp)
                ) {
                    AddGoalDialog(onDismissRequest = {}, onGoalConfirm = { _, _, _ -> })
                }
            }
        }
    }
}

@Preview
@Composable
fun GoalsScreenDashboardPreview() {
    Column(
        verticalArrangement = Arrangement.spacedBy(32.dp),
        modifier = Modifier.background(Color(0xFF1E293B)) // Slate background to separate screens visually
            .padding(16.dp)
    ) {
        // --- 1. LIGHT SYSTEM VARIATION ---
        Column {
            Text(
                text = "LIGHT MODE ACTIVE",
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            PocketGoalsTheme(darkTheme = false) {
                Box(
                    modifier = Modifier.fillMaxWidth()
                        .height(500.dp) // Bound height to fit both screen cards elegantly
                ) {
                    GoalsScreen()
                }
            }
        }

        // --- 2. DARK SYSTEM VARIATION ---
        Column {
            Text(
                text = "DARK MODE ACTIVE",
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            PocketGoalsTheme(darkTheme = true) {
                Box(
                    modifier = Modifier.fillMaxWidth().height(500.dp)
                ) {
                    GoalsScreen()
                }
            }
        }
    }
}
