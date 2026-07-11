package com.hathway.pocketgoals.presentation.ui.components.goals_components

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hathway.pocketgoals.domain.model.ThemeMode
import com.hathway.pocketgoals.presentation.ui.components.add_expense_components.SummaryItemRow
import com.hathway.pocketgoals.presentation.ui.theme.PocketGoalsTheme

@Composable
fun GoalReviewStep(
    goalName: String,
    categoryName: String,
    categoryIcon: ImageVector,
    categoryColor: Color,
    targetAmount: String,
    targetDate: String,
    duration: String,
    onConfirm: () -> Unit,
    onBack: () -> Unit
) {
    val scrollState = rememberScrollState()
    val isDark = isSystemInDarkTheme()

    val primaryColor = MaterialTheme.colorScheme.primary
    val dividerColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
    val iconBgAlpha = if (isDark) 0.22f else 0.1f

    Column(
        modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)
    ) {
        // 1. Scrollable Data Columns
        Column(
            modifier = Modifier.weight(1f).verticalScroll(scrollState)
                .padding(horizontal = 24.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Top Accent Icon Preview Badge Frame
            Box(
                modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier.size(100.dp).background(
                        categoryColor.copy(alpha = iconBgAlpha), RoundedCornerShape(24.dp)
                    ), contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = categoryIcon,
                        contentDescription = null,
                        tint = categoryColor,
                        modifier = Modifier.size(48.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            SummaryItemRow(categoryIcon, "Goal Name", goalName, categoryColor)
            HorizontalDivider(color = dividerColor)

            SummaryItemRow(Icons.Rounded.Category, "Category", categoryName, categoryColor)
            HorizontalDivider(color = dividerColor)

            SummaryItemRow(Icons.Rounded.Payments, "Target Amount", "₹ $targetAmount")
            HorizontalDivider(color = dividerColor)

            SummaryItemRow(Icons.Rounded.CalendarToday, "Target Date", targetDate)
            HorizontalDivider(color = dividerColor)

            SummaryItemRow(Icons.Rounded.Schedule, "Duration", duration)
        }

        // 2. Bottom Fixed Control Panel Section
        Column(
            modifier = Modifier.padding(24.dp), verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Button(
                onClick = onConfirm,
                modifier = Modifier.fillMaxWidth().height(56.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = primaryColor,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                )
            ) {
                Text(text = "Create Goal", fontWeight = FontWeight.Bold)
            }

            OutlinedButton(
                onClick = onBack,
                modifier = Modifier.fillMaxWidth().height(56.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = primaryColor
                ),
                border = ButtonDefaults.outlinedButtonBorder.copy(width = 1.dp)
            ) {
                Text(text = "Back", fontWeight = FontWeight.Bold)
            }
        }
    }
}

// ==========================================================
// Theme-Safe Split Previews
// ==========================================================

@Preview
@Composable
fun GoalReviewStepLightPreview() {
    PocketGoalsTheme(themeMode = ThemeMode.LIGHT) {
        GoalReviewStep(
            goalName = "Europe Summer Trip",
            categoryName = "Vacation",
            categoryIcon = Icons.Rounded.Flight,
            categoryColor = Color(0xFFF97316),
            targetAmount = "1,80,000",
            targetDate = "15 June 2027",
            duration = "12 Months",
            onConfirm = {},
            onBack = {})
    }
}

@Preview
@Composable
fun GoalReviewStepDarkPreview() {
    PocketGoalsTheme(themeMode = ThemeMode.DARK) {
        GoalReviewStep(
            goalName = "Europe Summer Trip",
            categoryName = "Vacation",
            categoryIcon = Icons.Rounded.Flight,
            categoryColor = Color(0xFFA78BFA),
            targetAmount = "1,80,000",
            targetDate = "15 June 2027",
            duration = "12 Months",
            onConfirm = {},
            onBack = {})
    }
}