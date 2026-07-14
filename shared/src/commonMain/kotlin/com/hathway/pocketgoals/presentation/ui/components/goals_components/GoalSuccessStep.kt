package com.hathway.pocketgoals.presentation.ui.components.goals_components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hathway.pocketgoals.domain.model.ThemeMode
import com.hathway.pocketgoals.presentation.ui.theme.PocketGoalsTheme
import org.jetbrains.compose.resources.stringResource
import pocketgoals.shared.generated.resources.*

@Composable
fun GoalSuccessStep(
    goalName: String,
    onViewGoal: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .size(100.dp)
                .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                Icons.Rounded.Check,
                null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(48.dp)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = stringResource(Res.string.goal_created),
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = stringResource(Res.string.goal_created_desc, goalName),
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(48.dp))

        Button(
            onClick = onViewGoal,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            )
        ) {
            Text(stringResource(Res.string.view_goal), fontWeight = FontWeight.Bold)
        }
    }
}



@Preview(name = "Goal Timeline Selection - Light Mode")
@Composable
private fun GoalTimelineStepLightPreview() {
    PocketGoalsTheme(themeMode = ThemeMode.LIGHT) {
        Surface(color = MaterialTheme.colorScheme.background) {
            GoalTimelineStep(
                targetDate = "Dec 18, 2026",
                onDateClick = {},
                // FIX: Resolves the string dynamically via CMP resources to fix selection highlights
                duration = "10",
                onDurationSelected = {},
                onNext = {}
            )
        }
    }
}

@Preview(name = "Goal Timeline Selection - Dark Mode")
@Composable
private fun GoalTimelineStepDarkPreview() {
    PocketGoalsTheme(themeMode = ThemeMode.DARK) {
        Surface(color = MaterialTheme.colorScheme.background) {
            GoalTimelineStep(
                targetDate = "Select Date",
                onDateClick = {},
                // FIX: Matches localized resource tokens to satisfy strict conditional equality checks
                duration = "other",
                onDurationSelected = {},
                onNext = {}
            )
        }
    }
}
