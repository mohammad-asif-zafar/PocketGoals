package com.hathway.pocketgoals.presentation.ui.components.goals_components

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.ui.unit.dp
import com.hathway.pocketgoals.presentation.ui.components.add_expense_components.SummaryItemRow
import org.jetbrains.compose.resources.stringResource
import pocketgoals.shared.generated.resources.*

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
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.weight(1f).verticalScroll(scrollState)
                .padding(horizontal = 24.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
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

            SummaryItemRow(categoryIcon, stringResource(Res.string.goal_name_label), goalName, categoryColor)
            HorizontalDivider(color = dividerColor)

            SummaryItemRow(Icons.Rounded.Category, stringResource(Res.string.category), categoryName, categoryColor)
            HorizontalDivider(color = dividerColor)

            SummaryItemRow(Icons.Rounded.Payments, stringResource(Res.string.target_amount_label), "$targetAmount")
            HorizontalDivider(color = dividerColor)

            SummaryItemRow(Icons.Rounded.CalendarToday, stringResource(Res.string.target_date_label), targetDate)
            HorizontalDivider(color = dividerColor)

            SummaryItemRow(Icons.Rounded.Schedule, stringResource(Res.string.duration_label), duration)
        }

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
                Text(text = stringResource(Res.string.create_goal), fontWeight = FontWeight.Bold)
            }

            OutlinedButton(
                onClick = onBack,
                modifier = Modifier.fillMaxWidth().height(56.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = primaryColor
                ),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
            ) {
                Text(text = stringResource(Res.string.back), fontWeight = FontWeight.Bold)
            }
        }
    }
}
