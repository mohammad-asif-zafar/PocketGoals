package com.hathway.pocketgoals.presentation.ui.components.goals_components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hathway.pocketgoals.domain.model.ThemeMode
import com.hathway.pocketgoals.presentation.ui.theme.PocketGoalsTheme

@Composable
fun GoalsTopBar(
    onAddGoalClick: () -> Unit
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
            color = MaterialTheme.colorScheme.onBackground
        )

        Surface(
            onClick = onAddGoalClick, 
            shape = RoundedCornerShape(12.dp),
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.height(40.dp)
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Goal",
                    tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.size(20.dp)
                )
                Text(
                    text = "Add Goal",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Preview
@Composable
fun GoalsTopBarThemePreview() {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp), modifier = Modifier.padding(8.dp)
    ) {
        PocketGoalsTheme(themeMode = ThemeMode.LIGHT) {
            Box(modifier = Modifier.background(MaterialTheme.colorScheme.background)) {
                GoalsTopBar(onAddGoalClick = {})
            }
        }
        PocketGoalsTheme(themeMode = ThemeMode.DARK) {
            Box(modifier = Modifier.background(MaterialTheme.colorScheme.background)) {
                GoalsTopBar(onAddGoalClick = {})
            }
        }
    }
}
