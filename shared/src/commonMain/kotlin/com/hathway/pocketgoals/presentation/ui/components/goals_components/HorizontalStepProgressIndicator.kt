package com.hathway.pocketgoals.presentation.ui.components.goals_components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.Backspace
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hathway.pocketgoals.domain.model.ThemeMode
import com.hathway.pocketgoals.presentation.ui.theme.PocketGoalsTheme
import org.jetbrains.compose.resources.stringResource
import pocketgoals.shared.generated.resources.Res
import pocketgoals.shared.generated.resources.currency_symbol
import pocketgoals.shared.generated.resources.custom_amount_confirm
import pocketgoals.shared.generated.resources.custom_amount_title

// ==========================================================
// 1. HORIZONTAL STEP PROGRESS INDICATOR COMPONENT
// ==========================================================
@Composable
fun HorizontalStepProgressIndicator(
    currentStepIndex: Int, totalStepsCount: Int, modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth().padding(vertical = 12.dp, horizontal = 24.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        for (i in 0 until totalStepsCount) {
            val isCompleted = i < currentStepIndex
            val isActive = i == currentStepIndex

            val barColor = when {
                isCompleted || isActive -> MaterialTheme.colorScheme.primary
                else -> MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
            }

            val barAlpha = when {
                isActive -> 1f
                isCompleted -> 0.6f
                else -> 1f
            }

            Box(
                modifier = Modifier.weight(1f).height(6.dp).clip(CircleShape)
                    .background(barColor.copy(alpha = barAlpha))
            )
        }
    }
}

@Preview(name = "Horizontal Step Rail Progress Line - Light Mode")
@Composable
private fun StepProgressIndicatorLightPreview() {
    PocketGoalsTheme(themeMode = ThemeMode.LIGHT) {
        Surface {
            Column(modifier = Modifier.padding(vertical = 16.dp)) {
                // Simulates step 2 active out of 4 stages complete
                HorizontalStepProgressIndicator(currentStepIndex = 1, totalStepsCount = 4)
            }
        }
    }
}

@Preview(name = "Horizontal Step Rail Progress Line - Dark Mode")
@Composable
private fun StepProgressIndicatorDarkPreview() {
    PocketGoalsTheme(themeMode = ThemeMode.DARK) {
        Surface {
            Column(modifier = Modifier.padding(vertical = 16.dp)) {
                // Simulates step 2 active out of 4 stages complete
                HorizontalStepProgressIndicator(currentStepIndex = 1, totalStepsCount = 4)
            }
        }
    }
}