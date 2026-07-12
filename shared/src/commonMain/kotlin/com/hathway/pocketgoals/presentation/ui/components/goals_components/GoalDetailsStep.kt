package com.hathway.pocketgoals.presentation.ui.components.goals_components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowRight
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.icons.rounded.Flight
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hathway.pocketgoals.domain.GoalCategory
import com.hathway.pocketgoals.domain.model.ThemeMode
import com.hathway.pocketgoals.presentation.ui.components.add_expense_components.FormField
import com.hathway.pocketgoals.presentation.ui.theme.PocketGoalsTheme

@Composable
fun GoalDetailsStep(
    goalName: String,
    onGoalNameChange: (String) -> Unit,
    category: GoalCategory,
    onCategoryClick: () -> Unit,
    description: String,
    onDescriptionChange: (String) -> Unit,
    onNext: () -> Unit
) {
    val scrollState = rememberScrollState()
    val isDark = isSystemInDarkTheme()

    // Theme-safe color constants
    val borderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
    val activeBorderColor = MaterialTheme.colorScheme.primary
    val placeholderColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
    val textColor = MaterialTheme.colorScheme.onSurface

    // Smooth opacity contrast scale adjustment for dark canvases
    val iconBgAlpha = if (isDark) 0.22f else 0.1f

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        // 1. Core Scrollable Content Form Layer
        Column(
            modifier = Modifier.weight(1f).verticalScroll(scrollState).padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Spacer(modifier = Modifier.height(24.dp))
            // Icon Placeholder Box (Top center)
            Box(
                modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier.size(80.dp).background(
                        category.color.copy(alpha = iconBgAlpha), RoundedCornerShape(20.dp)
                    ), contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = category.icon,
                        contentDescription = null,
                        tint = category.color,
                        modifier = Modifier.size(40.dp)
                    )
                }
            }

            FormField(label = "Goal Name") {
                OutlinedTextField(
                    value = goalName,
                    onValueChange = onGoalNameChange,
                    placeholder = { Text("e.g. Buy a New Car", color = placeholderColor) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = activeBorderColor,
                        unfocusedBorderColor = borderColor,
                        focusedTextColor = textColor,
                        unfocusedTextColor = textColor
                    )
                )
            }

            FormField(label = "Goal Category") {
                Row(
                    modifier = Modifier.fillMaxWidth().height(56.dp)
                        .border(1.dp, borderColor, RoundedCornerShape(12.dp))
                        .clip(RoundedCornerShape(12.dp)).clickable(onClick = onCategoryClick)
                        .padding(horizontal = 16.dp), verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        category.icon, null, tint = category.color, modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = category.name, fontWeight = FontWeight.Medium, color = textColor
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Icon(
                        imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowRight,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
                    )
                }
            }

            FormField(label = "Description (Optional)") {
                OutlinedTextField(
                    value = description,
                    onValueChange = onDescriptionChange,
                    placeholder = { Text("Add a short description", color = placeholderColor) },
                    modifier = Modifier.fillMaxWidth().height(120.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = activeBorderColor,
                        unfocusedBorderColor = borderColor,
                        focusedTextColor = textColor,
                        unfocusedTextColor = textColor
                    )
                )
            }
            Spacer(modifier = Modifier.height(24.dp))
        }

        // 2. Fixed Button Action Panel Row
        Box(modifier = Modifier.padding(24.dp)) {
            Button(
                onClick = onNext,
                enabled = goalName.isNotBlank(),
                modifier = Modifier.fillMaxWidth().height(56.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = activeBorderColor,
                    contentColor = MaterialTheme.colorScheme.onPrimary,
                    disabledContainerColor = MaterialTheme.colorScheme.primaryContainer,
                    disabledContentColor = Color.White.copy(alpha = 0.65f)
                )
            ) {
                Text("Next", fontWeight = FontWeight.Bold)
            }
        }
    }
}

// ==========================================================
// Theme-Safe Split Previews
// ==========================================================

@Preview
@Composable
fun GoalDetailsStepLightPreview() {
    val mockCategory =
        GoalCategory("Vacation", color = Color(0xFFF97316), icon = Icons.Rounded.Flight)
    PocketGoalsTheme(ThemeMode.LIGHT) {
        GoalDetailsStep(
            goalName = "",
            onGoalNameChange = {},
            category = mockCategory,
            onCategoryClick = {},
            description = "",
            onDescriptionChange = {},
            onNext = {})
    }
}

@Preview
@Composable
fun GoalDetailsStepDarkPreview() {
    val mockCategory =
        GoalCategory("Vacation", color = Color(0xFFA78BFA), icon = Icons.Rounded.Flight)
    PocketGoalsTheme(ThemeMode.DARK) {
        GoalDetailsStep(
            goalName = "Japan Spring Trip",
            onGoalNameChange = {},
            category = mockCategory,
            onCategoryClick = {},
            description = "Saving money for bullet trains and food.",
            onDescriptionChange = {},
            onNext = {})
    }
}