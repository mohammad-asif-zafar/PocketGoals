package com.hathway.pocketgoals.presentation.ui.components.category_components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hathway.pocketgoals.domain.ExpenseCategory
import com.hathway.pocketgoals.domain.model.ThemeMode
import com.hathway.pocketgoals.presentation.ui.theme.PocketGoalsTheme
import org.jetbrains.compose.resources.stringResource
import pocketgoals.shared.generated.resources.Res
import pocketgoals.shared.generated.resources.add_new_category_action
import pocketgoals.shared.generated.resources.more_categories_title
import pocketgoals.shared.generated.resources.search_categories_hint
import pocketgoals.shared.generated.resources.see_all_action

@Composable
fun CategorySelectionGrid(
    categories: List<ExpenseCategory>,
    selectedCategory: ExpenseCategory?,
    onCategorySelected: (ExpenseCategory) -> Unit,
    onCategoryLongClick: (ExpenseCategory) -> Unit,
    onAddNewCategory: () -> Unit,
    modifier: Modifier = Modifier
) {
    var searchQuery by remember { mutableStateOf("") }
    val filteredCategories = categories.filter { it.name.contains(searchQuery, ignoreCase = true) }

    Column(modifier = modifier) {
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            placeholder = {
                Text(
                    text = stringResource(Res.string.search_categories_hint),
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
                )
            },
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
            shape = RoundedCornerShape(12.dp),
            leadingIcon = {
                Icon(
                    Icons.Rounded.Search, null, tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = MaterialTheme.colorScheme.onSurface,
                unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f),
                focusedBorderColor = MaterialTheme.colorScheme.primary
            ),
            singleLine = true
        )

        Row(
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(Res.string.more_categories_title),
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            TextButton(onClick = { /* See All */ }) {
                Text(
                    text = stringResource(Res.string.see_all_action),
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(4),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.weight(1f)
        ) {
            items(filteredCategories) { category ->
                CategoryGridItem(
                    category = category,
                    isSelected = category == selectedCategory,
                    onCategoryClick = { onCategorySelected(category) },
                    onCategoryLongClick = { onCategoryLongClick(category) })
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedButton(
            onClick = onAddNewCategory,
            modifier = Modifier.fillMaxWidth().height(56.dp),
            shape = MaterialTheme.shapes.medium,
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = MaterialTheme.colorScheme.primary
            )
        ) {
            Icon(Icons.Rounded.Add, null)
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = stringResource(Res.string.add_new_category_action),
                fontWeight = FontWeight.Bold
            )
        }
    }
}


@Preview(name = "Grid Light Mode", showBackground = true, widthDp = 360, heightDp = 640)
@Composable
fun CategorySelectionGridLightPreview() {
    PocketGoalsTheme(themeMode = ThemeMode.LIGHT) {
        CategoryGridPreviewContent()
    }
}

@Preview(name = "Grid Dark Mode", showBackground = true, widthDp = 360, heightDp = 640)
@Composable
fun CategorySelectionGridDarkPreview() {
    PocketGoalsTheme(themeMode = ThemeMode.DARK) {
        CategoryGridPreviewContent()
    }
}

@Composable
private fun CategoryGridPreviewContent() {
    val mockCategories = remember { ExpenseCategory.defaultCategories }
    var selectedCategory by remember { mutableStateOf<ExpenseCategory?>(mockCategories.firstOrNull()) }

    CategorySelectionGrid(
        categories = mockCategories,
        selectedCategory = selectedCategory,
        onCategorySelected = { selectedCategory = it },
        onCategoryLongClick = {},
        onAddNewCategory = {},
        modifier = Modifier.fillMaxSize().padding(16.dp)
    )
}