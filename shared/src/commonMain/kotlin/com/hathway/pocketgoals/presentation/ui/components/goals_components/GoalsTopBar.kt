package com.hathway.pocketgoals.presentation.ui.components.goals_components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hathway.pocketgoals.presentation.ui.theme.PocketGoalsTheme


@Composable
fun GoalsTopBar(
    onAddGoalClick: () -> Unit // Feature: Exposes click event to open dialog/screen
) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Goals",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            // Automatically adjusts color for dark/light modes
            color = MaterialTheme.colorScheme.onBackground
        )

        Surface(
            onClick = onAddGoalClick, shape = RoundedCornerShape(8.dp),
            // Dynamically switches to secondary container tokens instead of a hardcoded mint green
            color = MaterialTheme.colorScheme.secondaryContainer, modifier = Modifier.height(36.dp)
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Goal",
                    // Pulls contrasting onSecondaryContainer text/icon color directly from your theme palette
                    tint = MaterialTheme.colorScheme.onSecondaryContainer,
                    modifier = Modifier.size(18.dp)
                )
                Text(
                    text = "Add Goal",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onSecondaryContainer,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}


@Composable
fun AddGoalDialog(
    onDismissRequest: () -> Unit,
    onGoalConfirm: (name: String, targetAmount: Double) -> Unit
) {
    var goalName by remember { mutableStateOf("") }
    var targetAmountString by remember { mutableStateOf("") }

    // Simple state-driven validation check
    val isFormValid = goalName.isNotBlank() && (targetAmountString.toDoubleOrNull() ?: 0.0) > 0.0

    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = {
            Text(
                text = "Create New Goal",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
        },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                // Goal Name input field
                OutlinedTextField(
                    value = goalName,
                    onValueChange = { goalName = it },
                    label = { Text("Goal Name") },
                    placeholder = { Text("e.g., MacBook Pro, Vacation") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                // Target Amount input field
                OutlinedTextField(
                    value = targetAmountString,
                    onValueChange = { input ->
                        // Restrict inputs strictly to digital numeric digits or fractional decimal values
                        if (input.isEmpty() || input.toDoubleOrNull() != null) {
                            targetAmountString = input
                        }
                    },
                    label = { Text("Target Amount (₹)") },
                    placeholder = { Text("e.g., 50000") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        dismissButton = {
            TextButton(onClick = onDismissRequest) {
                Text("Cancel")
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val amount = targetAmountString.toDoubleOrNull() ?: 0.0
                    onGoalConfirm(goalName, amount)
                },
                enabled = isFormValid // Disable button until typing is valid
            ) {
                Text("Save Goal")
            }
        }
    )
}

@Preview
@Composable
fun GoalsTopBarThemePreview() {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp), modifier = Modifier.padding(8.dp)
    ) {
        // --- 1. LIGHT THEME VARIATION ---
        PocketGoalsTheme(darkTheme = false) {
            Column(
                modifier = Modifier.background(MaterialTheme.colorScheme.background)
                    .padding(vertical = 8.dp)
            ) {
                GoalsTopBar(onAddGoalClick = {})
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // --- 2. DARK THEME VARIATION ---
        PocketGoalsTheme(darkTheme = true) {
            Column(
                modifier = Modifier.background(MaterialTheme.colorScheme.background)
                    .padding(vertical = 8.dp)
            ) {
                GoalsTopBar(onAddGoalClick = {})
            }
        }
    }
}
