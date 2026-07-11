package com.hathway.pocketgoals.presentation.ui.components.category_components

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
import androidx.compose.ui.unit.dp
import com.hathway.pocketgoals.domain.ExpenseCategory

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
        // ... search field ...
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            placeholder = { Text("Search categories...") },
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
            shape = RoundedCornerShape(12.dp),
            leadingIcon = { Icon(Icons.Rounded.Search, null, tint = MaterialTheme.colorScheme.outline) },
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f),
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
                "More Categories",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            TextButton(onClick = { /* See All */ }) {
                Text("See All", style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
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
                    onCategoryLongClick = { onCategoryLongClick(category) }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedButton(
            onClick = onAddNewCategory,
            modifier = Modifier.fillMaxWidth().height(56.dp),
            shape = MaterialTheme.shapes.medium,
            border = ButtonDefaults.outlinedButtonBorder.copy(width = 1.dp)
        ) {
            Icon(Icons.Rounded.Add, null)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Add New Category", fontWeight = FontWeight.Bold)
        }
    }
}