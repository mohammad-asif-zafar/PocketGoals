package com.hathway.pocketgoals.presentation.ui.components.goals_components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowRight
import androidx.compose.material.icons.rounded.Category
import androidx.compose.material.icons.rounded.DirectionsCar
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hathway.pocketgoals.domain.model.ThemeMode
import com.hathway.pocketgoals.presentation.ui.components.add_expense_components.FormField
import com.hathway.pocketgoals.presentation.ui.components.category_components.IconPickerSheet
import com.hathway.pocketgoals.presentation.ui.theme.PocketGoalsTheme
@Composable
fun CustomGoalDetailsStep(
    goalName: String,
    onGoalNameChange: (String) -> Unit,
    selectedIcon: ImageVector,
    onIconSelected: (ImageVector) -> Unit,
    selectedColor: Color,
    onColorSelected: (Color) -> Unit,
    onNext: () -> Unit
) {
    var showIconPicker by remember { mutableStateOf(false) }
    val isDark = isSystemInDarkTheme()

    // 15 Palette tokens mapped directly from your brand design assets
    val colors = listOf(
        Color(0xFF10B981), Color(0xFF3B82F6), Color(0xFFF59E0B),
        Color(0xFFEC4899), Color(0xFF6366F1), Color(0xFF8B5CF6),
        Color(0xFFEF4444), Color(0xFF14B8A6), Color(0xFFF87171),
        Color(0xFFFB923C), Color(0xFF0EA5E9), Color(0xFF84CC16),
        Color(0xFFA855F7), Color(0xFFD946EF), Color(0xFFF43F5E)
    )

    // Fallback stub helper interaction if picker sheet logic requires a placeholder trigger
    if (showIconPicker) {
        // IconPickerSheet(
        //     selectedIcon = selectedIcon,
        //     selectedColor = selectedColor,
        //     onIconSelected = onIconSelected,
        //     onDismiss = { showIconPicker = false }
        // )
    }

    // Tweak transparency layer density dynamically depending on theme dark state
    val tintAlpha = if (isDark) 0.2f else 0.1f
    val borderAlpha = if (isDark) 0.4f else 0.2f

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // ==========================================
            // 1. Live Icon / Color Frame Preview Bubble
            // ==========================================
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .background(selectedColor.copy(alpha = tintAlpha), CircleShape)
                        .border(2.dp, selectedColor.copy(alpha = borderAlpha), CircleShape),
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

            // ==========================================
            // 2. Icon Sheet Selector Trigger
            // ==========================================
            FormField(label = "Choose Icon") {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .border(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.3f), RoundedCornerShape(12.dp))
                        .clip(RoundedCornerShape(12.dp))
                        .clickable { showIconPicker = true }
                        .padding(horizontal = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .background(selectedColor.copy(alpha = tintAlpha), RoundedCornerShape(8.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(selectedIcon, null, tint = selectedColor, modifier = Modifier.size(20.dp))
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = "Select Icon",
                        color = MaterialTheme.colorScheme.onSurface,
                        fontWeight = FontWeight.Medium
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Icon(
                        imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowRight,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
                    )
                }
            }

            // ==========================================
            // 3. Horizontal Color Row Picker
            // ==========================================
            FormField(label = "Choose Color") {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    colors.take(8).forEach { color ->
                        val isSelected = selectedColor == color
                        Box(
                            modifier = Modifier
                                .size(32.dp)
                                .clip(CircleShape)
                                .background(color)
                                .clickable { onColorSelected(color) }
                                .border(
                                    width = if (isSelected) 3.dp else 0.dp,
                                    color = if (isSelected) MaterialTheme.colorScheme.background else Color.Transparent,
                                    shape = CircleShape
                                )
                                .then(
                                    if (isSelected) {
                                        Modifier.border(1.dp, MaterialTheme.colorScheme.onBackground.copy(alpha = 0.4f), CircleShape)
                                    } else Modifier
                                )
                        )
                    }
                }
            }

            // ==========================================
            // 4. Goal Text Form Entry
            // ==========================================
            FormField(label = "Goal Name") {
                OutlinedTextField(
                    value = goalName,
                    onValueChange = onGoalNameChange,
                    placeholder = { Text("e.g. Europe Trip, New Laptop") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f),
                        focusedTextColor = MaterialTheme.colorScheme.onSurface,
                        unfocusedTextColor = MaterialTheme.colorScheme.onSurface
                    )
                )
            }
        }

        // ==========================================
        // 5. Fixed Next Navigation Button Row
        // ==========================================
        Box(modifier = Modifier.padding(16.dp)) {
            Button(
                onClick = onNext,
                enabled = goalName.isNotBlank(),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
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

@Preview
@Composable
fun CustomGoalDetailsStepDarkPreview() {
    PocketGoalsTheme(themeMode = ThemeMode.DARK) {
        CustomGoalDetailsStep(
            goalName = "Tesla Model 3",
            onGoalNameChange = {},
            selectedIcon = Icons.Rounded.DirectionsCar,
            onIconSelected = {},
            selectedColor = Color(0xFF3B82F6),
            onColorSelected = {},
            onNext = {})
    }
}

@Preview
@Composable
fun CustomGoalDetailsStepLightPreview() {
    PocketGoalsTheme(themeMode = ThemeMode.LIGHT) {
        CustomGoalDetailsStep(
            goalName = "Tesla Model 3",
            onGoalNameChange = {},
            selectedIcon = Icons.Rounded.DirectionsCar,
            onIconSelected = {},
            selectedColor = Color(0xFF3B82F6),
            onColorSelected = {},
            onNext = {})
    }
}