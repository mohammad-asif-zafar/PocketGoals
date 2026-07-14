package com.hathway.pocketgoals.presentation.ui.components.goals_components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hathway.pocketgoals.domain.model.GoalCategory
import com.hathway.pocketgoals.domain.model.ThemeMode
import com.hathway.pocketgoals.presentation.ui.theme.PocketGoalsTheme
import org.jetbrains.compose.resources.stringResource
import pocketgoals.shared.generated.resources.*

@Composable
fun GoalCategorySelectionStep(
    selectedCategory: GoalCategory?,
    onCategorySelected: (GoalCategory) -> Unit,
    onCustomGoal: () -> Unit,
    onNext: () -> Unit
) {
    val categories = GoalCategory.defaultCategories
    val primaryColor = MaterialTheme.colorScheme.primary

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.weight(1f).padding(horizontal = 24.dp)
        ) {
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = stringResource(Res.string.top_categories),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.weight(1f)
            ) {
                items(categories) { category ->
                    val isSelected = category.name == selectedCategory?.name
                    GoalCategoryItem(
                        category = category,
                        isSelected = isSelected,
                        onClick = { onCategorySelected(category) })
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedButton(
                onClick = onCustomGoal,
                modifier = Modifier.fillMaxWidth().height(56.dp),
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = primaryColor
                )
            ) {
                Icon(imageVector = Icons.Rounded.Add, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = stringResource(Res.string.create_custom_goal),
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.height(24.dp))
        }

        Box(modifier = Modifier.padding(24.dp)) {
            Button(
                onClick = onNext,
                enabled = selectedCategory != null,
                modifier = Modifier.fillMaxWidth().height(56.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = primaryColor,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                )
            ) {
                Text(text = stringResource(Res.string.btn_next), fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun GoalCategoryItem(
    category: GoalCategory, isSelected: Boolean, onClick: () -> Unit
) {
    val isDark = isSystemInDarkTheme()

    val borderColor =
        if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline.copy(
            alpha = 0.1f
        )
    val itemBg =
        if (isSelected) MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.4f) else MaterialTheme.colorScheme.surface

    val iconBgAlpha = if (isDark) 0.22f else 0.1f

    Column(
        modifier = Modifier.clip(RoundedCornerShape(16.dp)).background(itemBg)
            .border(1.dp, borderColor, RoundedCornerShape(16.dp)).clickable(onClick = onClick)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier.size(48.dp)
                .background(category.color.copy(alpha = iconBgAlpha), RoundedCornerShape(12.dp)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = category.icon,
                contentDescription = stringResource(category.name),
                tint = category.color,
                modifier = Modifier.size(24.dp)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = stringResource(category.name),
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.SemiBold,
            color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
        )
    }
}

@Preview(name = "Category Selection Step - Light Mode")
@Composable
private fun GoalCategorySelectionStepLightPreview() {
    // Pulls the real "Travel" category reference directly out from your default data structure
    val mockSelectedCategory = GoalCategory.defaultCategories.firstOrNull()

    PocketGoalsTheme(themeMode = ThemeMode.LIGHT) {
        Surface(color = MaterialTheme.colorScheme.background) {
            GoalCategorySelectionStep(
                selectedCategory = mockSelectedCategory, // Tests active selection border highlights
                onCategorySelected = {}, onCustomGoal = {}, onNext = {})
        }
    }
}

@Preview(name = "Category Selection Step - Dark Mode")
@Composable
private fun GoalCategorySelectionStepDarkPreview() {
    PocketGoalsTheme(themeMode = ThemeMode.DARK) {
        Surface(color = MaterialTheme.colorScheme.background) {
            GoalCategorySelectionStep(
                selectedCategory = null, // Tests unselected visual states and disabled buttons
                onCategorySelected = {}, onCustomGoal = {}, onNext = {})
        }
    }
}
