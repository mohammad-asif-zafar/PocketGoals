package com.hathway.pocketgoals.presentation.ui.components.goals_components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hathway.pocketgoals.domain.model.ThemeMode
import com.hathway.pocketgoals.presentation.ui.components.add_expense_components.CustomNumberPad
import com.hathway.pocketgoals.presentation.ui.components.add_expense_components.FormField
import com.hathway.pocketgoals.presentation.ui.theme.PocketGoalsTheme

@Composable
fun GoalAmountStep(
    amount: String, onAmountChange: (String) -> Unit, onNext: () -> Unit
) {
    val quickSelectOptions =
        listOf("₹ 1,000", "₹ 10,000", "₹ 25,000", "₹ 50,000", "₹ 1,00,000", "Other")

    val borderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
    val activeColor = MaterialTheme.colorScheme.primary
    val bodyTextColor = MaterialTheme.colorScheme.onSurface

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        // 1. Core Scrollable Grid Content Section
        Column(
            modifier = Modifier.weight(1f).padding(horizontal = 24.dp)
        ) {
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "Target Amount",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Current Amount Display Field
            Box(
                modifier = Modifier.fillMaxWidth().height(64.dp).clip(RoundedCornerShape(12.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f))
                    .border(1.dp, activeColor.copy(alpha = 0.5f), RoundedCornerShape(12.dp))
                    .padding(horizontal = 16.dp), contentAlignment = Alignment.CenterStart
            ) {
                Text(
                    text = if (amount == "0" || amount.isBlank()) "₹ 0" else "₹ $amount",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = if (amount == "0" || amount.isBlank()) MaterialTheme.colorScheme.onSurfaceVariant else bodyTextColor
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Quick Select",
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Selection Options Matrix
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(quickSelectOptions) { option ->
                    // Matches current numerical clean string to check if item is currently active
                    val cleanOption = option.replace("₹ ", "").replace(",", "")
                    val isSelected = amount == cleanOption && option != "Other"

                    Box(
                        modifier = Modifier.height(48.dp).clip(RoundedCornerShape(12.dp))
                            .background(if (isSelected) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surface)
                            .border(
                                width = if (isSelected) 1.5.dp else 1.dp,
                                color = if (isSelected) activeColor else borderColor,
                                shape = RoundedCornerShape(12.dp)
                            ).clickable {
                                if (option != "Other") {
                                    onAmountChange(cleanOption)
                                } else {
                                    onAmountChange("") // Clear out text space so number overlay triggers smoothly
                                }
                            }, contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = option,
                            fontWeight = FontWeight.Medium,
                            color = if (isSelected) MaterialTheme.colorScheme.onPrimaryContainer else bodyTextColor
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
        }

        // 2. Fixed Action Block Container
        Column(modifier = Modifier.padding(24.dp)) {
            Button(
                onClick = onNext,
                enabled = amount != "0" && amount.isNotBlank(),
                modifier = Modifier.fillMaxWidth().height(56.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = activeColor,
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
fun GoalAmountStepLightPreview() {
    PocketGoalsTheme(ThemeMode.LIGHT) {
        GoalAmountStep(amount = "0", onAmountChange = {}, onNext = {})
    }
}

@Preview
@Composable
fun GoalAmountStepDarkPreview() {
    PocketGoalsTheme(ThemeMode.DARK) {
        GoalAmountStep(amount = "25000", onAmountChange = {}, onNext = {})
    }
}