package com.hathway.pocketgoals.presentation.ui.components.category_components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowRight
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.hathway.pocketgoals.domain.ExpenseCategory
import com.hathway.pocketgoals.presentation.ui.components.add_expense_components.FormField

@Composable
fun AddEditCategoryForm(
    initialCategory: ExpenseCategory? = null,
    onSave: (ExpenseCategory) -> Unit,
    onDelete: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    var name by remember { mutableStateOf(initialCategory?.name ?: "") }
    var selectedColor by remember { mutableStateOf(initialCategory?.color ?: Color(0xFF10B981)) }
    var selectedIcon by remember { mutableStateOf(initialCategory?.icon ?: Icons.Rounded.Category) }
    var showIconPicker by remember { mutableStateOf(false) }

    val colors = listOf(
        Color(0xFF10B981), Color(0xFF3B82F6), Color(0xFFF59E0B),
        Color(0xFFEC4899), Color(0xFF6366F1), Color(0xFF8B5CF6),
        Color(0xFFEF4444), Color(0xFF14B8A6), Color(0xFFF87171),
        Color(0xFFFB923C), Color(0xFF0EA5E9), Color(0xFF84cc16),
        Color(0xFFa855f7), Color(0xFFd946ef), Color(0xFFf43f5e)
    )

    if (showIconPicker) {
        IconPickerSheet(
            selectedIcon = selectedIcon,
            selectedColor = selectedColor,
            onIconSelected = { selectedIcon = it },
            onDismiss = { showIconPicker = false }
        )
    }

    Column(
        modifier = modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        // Icon Preview & Upload
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .background(selectedColor.copy(alpha = 0.1f), CircleShape)
                    .border(2.dp, selectedColor.copy(alpha = 0.3f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = selectedIcon,
                    contentDescription = null,
                    tint = selectedColor,
                    modifier = Modifier.size(40.dp)
                )
            }
        }

        FormField(label = "Category Name") {
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                placeholder = { Text("e.g. Pet Care") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
                )
            )
        }

        FormField(label = "Icon") {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .border(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.3f), RoundedCornerShape(12.dp))
                    .clickable { showIconPicker = true }
                    .padding(horizontal = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier.size(36.dp).background(selectedColor.copy(alpha = 0.1f), RoundedCornerShape(8.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(selectedIcon, null, tint = selectedColor, modifier = Modifier.size(20.dp))
                }
                Spacer(modifier = Modifier.width(12.dp))
                Text("Select Icon", color = MaterialTheme.colorScheme.onSurface, fontWeight = FontWeight.Medium)
                Spacer(modifier = Modifier.weight(1f))
                Icon(Icons.AutoMirrored.Rounded.KeyboardArrowRight, null, tint = MaterialTheme.colorScheme.outline)
            }
        }

        FormField(label = "Color") {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                colors.take(8).forEach { color ->
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                            .background(color)
                            .clickable { selectedColor = color }
                            .border(
                                width = if (selectedColor == color) 3.dp else 0.dp,
                                color = if (selectedColor == color) Color.White else Color.Transparent,
                                shape = CircleShape
                            )
                            .then(
                                if (selectedColor == color) {
                                    Modifier.border(1.dp, color.copy(alpha = 0.5f), CircleShape)
                                } else Modifier
                            )
                    )
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = { if (name.isNotBlank()) onSave(ExpenseCategory(name, selectedColor, selectedIcon)) },
            modifier = Modifier.fillMaxWidth().height(56.dp),
            shape = RoundedCornerShape(12.dp),
            enabled = name.isNotBlank()
        ) {
            Text(if (initialCategory == null) "Save Category" else "Update Category", fontWeight = FontWeight.Bold)
        }

        if (onDelete != null) {
            TextButton(
                onClick = onDelete,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(Icons.Rounded.Delete, null, tint = MaterialTheme.colorScheme.error)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Delete Category", color = MaterialTheme.colorScheme.error)
            }
        }
    }
}