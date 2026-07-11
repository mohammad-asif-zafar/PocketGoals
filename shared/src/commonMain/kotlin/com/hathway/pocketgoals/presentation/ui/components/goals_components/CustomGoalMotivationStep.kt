package com.hathway.pocketgoals.presentation.ui.components.goals_components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hathway.pocketgoals.domain.model.ThemeMode
import com.hathway.pocketgoals.presentation.ui.components.add_expense_components.FormField
import com.hathway.pocketgoals.presentation.ui.theme.PocketGoalsTheme

@Composable
fun CustomGoalMotivationStep(
    description: String,
    onDescriptionChange: (String) -> Unit,
    motivation: String,
    onMotivationChange: (String) -> Unit,
    onNext: () -> Unit
) {
    val scrollState = rememberScrollState()

    val borderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
    val activeBorderColor = MaterialTheme.colorScheme.primary
    val placeholderColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
    val textColor = MaterialTheme.colorScheme.onSurface

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // 1. Scrollable Content Layer
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(scrollState)
                .padding(16.dp)
        ) {
            Text(
                text = "Add Description",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            FormField(label = "Description (Optional)") {
                OutlinedTextField(
                    value = description,
                    onValueChange = onDescriptionChange,
                    placeholder = { Text("Add any notes about your goal", color = placeholderColor) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp),
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

            FormField(label = "Motivation (Optional)") {
                OutlinedTextField(
                    value = motivation,
                    onValueChange = onMotivationChange,
                    placeholder = { Text("Why is this goal important to you?", color = placeholderColor) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = activeBorderColor,
                        unfocusedBorderColor = borderColor,
                        focusedTextColor = textColor,
                        unfocusedTextColor = textColor
                    )
                )
            }
        }

        // 2. Fixed Button Panel
        Column(modifier = Modifier.padding(16.dp)) {
            Button(
                onClick = onNext,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = activeBorderColor,
                    contentColor = MaterialTheme.colorScheme.onPrimary
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
fun CustomGoalMotivationStepLightPreview() {
    PocketGoalsTheme(ThemeMode.LIGHT) {
        CustomGoalMotivationStep(
            description = "",
            onDescriptionChange = {},
            motivation = "",
            onMotivationChange = {},
            onNext = {}
        )
    }
}

@Preview
@Composable
fun CustomGoalMotivationStepDarkPreview() {
    PocketGoalsTheme(ThemeMode.DARK) {
        CustomGoalMotivationStep(
            description = "Saving up for an annual holiday trip.",
            onDescriptionChange = {},
            motivation = "Taking a much-needed break with family.",
            onMotivationChange = {},
            onNext = {}
        )
    }
}