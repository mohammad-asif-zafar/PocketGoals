package com.hathway.pocketgoals.presentation.ui.components.goals_components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowRight
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hathway.pocketgoals.domain.model.ThemeMode
import com.hathway.pocketgoals.presentation.ui.components.add_expense_components.FormField
import com.hathway.pocketgoals.presentation.ui.theme.PocketGoalsTheme
import org.jetbrains.compose.resources.stringResource
import pocketgoals.shared.generated.resources.Res
import pocketgoals.shared.generated.resources.goal_btn_next
import pocketgoals.shared.generated.resources.goal_lbl_choose_color
import pocketgoals.shared.generated.resources.goal_lbl_choose_icon
import pocketgoals.shared.generated.resources.goal_lbl_name
import pocketgoals.shared.generated.resources.goal_lbl_select_icon
import pocketgoals.shared.generated.resources.goal_picker_title
import pocketgoals.shared.generated.resources.goal_placeholder_name
import pocketgoals.shared.generated.resources.goal_wizard_title


// ==========================================================
// 1. MASTER MULTI-STAGE WIZARD FLOW STATE NAVIGATION SCHEMAS
// ==========================================================
sealed interface GoalWizardState {
    data object DetailsSetup : GoalWizardState
    data class TargetAmountSetup(val name: String, val icon: ImageVector, val color: Color) :
        GoalWizardState

    data class SuccessSplash(val name: String) : GoalWizardState
}

@Composable
fun GoalWizardFlowCoordinator(
    onFlowComplete: () -> Unit, modifier: Modifier = Modifier
) {
    var currentStep by remember { mutableStateOf<GoalWizardState>(GoalWizardState.DetailsSetup) }

    // Dynamic component variables state tracking
    var goalName by remember { mutableStateOf("") }
    var selectedIcon by remember { mutableStateOf(Icons.Rounded.Savings) }
    var selectedColor by remember { mutableStateOf(Color(0xFF10B981)) }

    Scaffold(
        modifier = modifier.fillMaxSize(), topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        stringResource(Res.string.goal_wizard_title), fontWeight = FontWeight.Bold
                    )
                })
        }) { paddingValues ->
        AnimatedContent(
            targetState = currentStep,
            modifier = Modifier.fillMaxSize().padding(paddingValues),
            transitionSpec = {
                if (initialState is GoalWizardState.DetailsSetup) {
                    slideInHorizontally { it } + fadeIn() togetherWith slideOutHorizontally { -it } + fadeOut()
                } else {
                    slideInHorizontally { -it } + fadeIn() togetherWith slideOutHorizontally { it } + fadeOut()
                }
            },
            label = "GoalWizardLayoutTransitions"
        ) { state ->
            when (state) {
                is GoalWizardState.DetailsSetup -> {
                    CustomGoalDetailsStep(
                        goalName = goalName,
                        onGoalNameChange = { goalName = it },
                        selectedIcon = selectedIcon,
                        onIconSelected = { selectedIcon = it },
                        selectedColor = selectedColor,
                        onColorSelected = { selectedColor = it },
                        onNext = {
                            currentStep = GoalWizardState.TargetAmountSetup(
                                goalName, selectedIcon, selectedColor
                            )
                        })
                }

                is GoalWizardState.TargetAmountSetup -> {
                    // Fallback visual mock placement block step context
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Button(onClick = {
                            currentStep = GoalWizardState.SuccessSplash(state.name)
                        }) {
                            Text("Complete Goal Creation for ${state.name}")
                        }
                    }
                }

                is GoalWizardState.SuccessSplash -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Button(onClick = onFlowComplete) { Text("Finish") }
                    }
                }
            }
        }
    }
}

// ==========================================================
// 2. DETAILED STEP FORM WITH LIVE CHARACTER LIMIT TRACKING LABEL
// ==========================================================
@Composable
fun CustomGoalDetailsStep(
    goalName: String,
    onGoalNameChange: (String) -> Unit,
    selectedIcon: ImageVector,
    onIconSelected: (ImageVector) -> Unit,
    selectedColor: Color,
    onColorSelected: (Color) -> Unit,
    onNext: () -> Unit
) {
    var showIconPicker by remember { mutableStateOf(false) }
    val isDark = isSystemInDarkTheme()
    val maxCharLimit = 25

    val colors = listOf(
        Color(0xFF10B981),
        Color(0xFF3B82F6),
        Color(0xFFF59E0B),
        Color(0xFFEC4899),
        Color(0xFF6366F1),
        Color(0xFF8B5CF6),
        Color(0xFFEF4444),
        Color(0xFF14B8A6)
    )

    if (showIconPicker) {
        IconPickerSheet(
            selectedIcon = selectedIcon,
            selectedColor = selectedColor,
            onIconSelected = onIconSelected,
            onDismiss = { showIconPicker = false })
    }

    val tintAlpha = if (isDark) 0.2f else 0.1f
    val borderAlpha = if (isDark) 0.4f else 0.2f

    Column(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.weight(1f).padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Spacer(modifier = Modifier.height(12.dp))

            // Preview Node circle bubble
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                Box(
                    modifier = Modifier.size(80.dp)
                        .background(selectedColor.copy(alpha = tintAlpha), CircleShape)
                        .border(2.dp, selectedColor.copy(alpha = borderAlpha), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(selectedIcon, null, tint = selectedColor, modifier = Modifier.size(40.dp))
                }
            }

            // Choose Icon Pill Trigger field
            FormField(label = stringResource(Res.string.goal_lbl_choose_icon)) {
                Row(
                    modifier = Modifier.fillMaxWidth().height(56.dp).border(
                        1.dp,
                        MaterialTheme.colorScheme.outline.copy(alpha = 0.3f),
                        RoundedCornerShape(12.dp)
                    ).clip(RoundedCornerShape(12.dp)).clickable { showIconPicker = true }
                        .padding(horizontal = 12.dp),
                    verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier.size(36.dp).background(
                            selectedColor.copy(alpha = tintAlpha), RoundedCornerShape(8.dp)
                        ), contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            selectedIcon,
                            null,
                            tint = selectedColor,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        stringResource(Res.string.goal_lbl_select_icon),
                        fontWeight = FontWeight.Medium
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Icon(
                        Icons.AutoMirrored.Rounded.KeyboardArrowRight,
                        null,
                        tint = MaterialTheme.colorScheme.outline
                    )
                }
            }

            // Choose Color line Row items channel
            FormField(label = stringResource(Res.string.goal_lbl_choose_color)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    colors.forEach { color ->
                        val isSelected = selectedColor == color
                        Box(
                            modifier = Modifier.size(32.dp).clip(CircleShape).background(color)
                                .clickable { onColorSelected(color) }.border(
                                    width = if (isSelected) 3.dp else 0.dp,
                                    color = MaterialTheme.colorScheme.background,
                                    shape = CircleShape
                                ).then(
                                    if (isSelected) Modifier.border(
                                        1.dp,
                                        MaterialTheme.colorScheme.onBackground.copy(alpha = 0.4f),
                                        CircleShape
                                    ) else Modifier
                                )
                        )
                    }
                }
            }
            // Goal Input Box Field paired with Live Character Counter Tracking Labels
            FormField(label = stringResource(Res.string.goal_lbl_name)) {
                Column {
                    OutlinedTextField(
                        value = goalName,
                        onValueChange = { if (it.length <= maxCharLimit) onGoalNameChange(it) },
                        placeholder = { Text(stringResource(Res.string.goal_placeholder_name)) },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "${goalName.length} / $maxCharLimit",
                        style = MaterialTheme.typography.labelSmall,
                        color = if (goalName.length == maxCharLimit) {
                            MaterialTheme.colorScheme.error
                        } else {
                            MaterialTheme.colorScheme.onSurfaceVariant
                        },
                        textAlign = TextAlign.End,
                        modifier = Modifier.fillMaxWidth().padding(end = 4.dp)
                    )
                }
            }

            Box(modifier = Modifier.padding(24.dp)) {
                Button(
                    onClick = onNext,
                    enabled = goalName.isNotBlank(),
                    modifier = Modifier.fillMaxWidth().height(56.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(stringResource(Res.string.goal_btn_next), fontWeight = FontWeight.Bold)
                }
            }

            // ==========================================================
// 3. ICON SELECTOR GRID SHEET MODAL CONTAINER LAYOUT
// ==========================================================
            @OptIn(ExperimentalMaterial3Api::class)
            @Composable
            fun IconPickerSheet(
                selectedIcon: ImageVector,
                selectedColor: Color,
                onIconSelected: (ImageVector) -> Unit,
                onDismiss: () -> Unit
            ) {
                val iconsList = listOf(
                    Icons.Rounded.Savings,
                    Icons.Rounded.DirectionsCar,
                    Icons.Rounded.Home,
                    Icons.Rounded.Flight,
                    Icons.Rounded.School,
                    Icons.Rounded.Laptop,
                    Icons.Rounded.LocalMall,
                    Icons.Rounded.Favorite,
                    Icons.Rounded.Celebration,
                    Icons.Rounded.FitnessCenter,
                    Icons.Rounded.SportsEsports,
                    Icons.Rounded.MedicalServices
                )

                ModalBottomSheet(
                    onDismissRequest = onDismiss, containerColor = MaterialTheme.colorScheme.surface
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth()
                            .padding(start = 24.dp, end = 24.dp, bottom = 44.dp, top = 8.dp)
                    ) {
                        Text(
                            text = stringResource(Res.string.goal_picker_title),
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(bottom = 20.dp)
                        )

                        LazyVerticalGrid(
                            columns = GridCells.Fixed(4),
                            horizontalArrangement = Arrangement.spacedBy(16.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            items(iconsList) { icon ->
                                val isSelected = selectedIcon == icon

                                Box(
                                    modifier = Modifier.aspectRatio(1f)
                                        .clip(RoundedCornerShape(16.dp)).background(
                                            if (isSelected) selectedColor.copy(alpha = 0.15f)
                                            else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f)
                                        ).border(
                                            width = 2.dp,
                                            color = if (isSelected) selectedColor else Color.Transparent,
                                            shape = RoundedCornerShape(16.dp)
                                        ).clickable {
                                            onIconSelected(icon)
                                            onDismiss()
                                        }, contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = icon,
                                        contentDescription = null,
                                        tint = if (isSelected) selectedColor else MaterialTheme.colorScheme.onSurfaceVariant,
                                        modifier = Modifier.size(28.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(name = "Master Wizard Shell Flow - Light Mode")
@Composable
private fun GoalWizardCoordinatorLightPreview() {
    PocketGoalsTheme(themeMode = ThemeMode.LIGHT) {
        Surface {
            GoalWizardFlowCoordinator(onFlowComplete = {})
        }
    }
}

@Preview(name = "Icon Choice Sheet Grid Panel - Dark Mode")
@Composable
private fun IconPickerSheetGridDarkPreview() {
    PocketGoalsTheme(themeMode = ThemeMode.DARK) {
        // Renders the standalone active selection sheet frame cleanly inside layout drawers
        IconPickerSheet(
            selectedIcon = Icons.Rounded.Savings,
            selectedColor = Color(0xFF10B981),
            onIconSelected = {},
            onDismiss = {})
    }
}