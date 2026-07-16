package com.hathway.pocketgoals.presentation.ui.components.category_components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Restaurant
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hathway.pocketgoals.domain.ExpenseCategory
import com.hathway.pocketgoals.domain.model.ThemeMode
import com.hathway.pocketgoals.presentation.ui.theme.PocketGoalsTheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CategoryGridItem(
    category: ExpenseCategory,
    isSelected: Boolean,
    onCategoryClick: () -> Unit,
    onCategoryLongClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val borderColor =
        if (isSelected) MaterialTheme.colorScheme.primary.copy(alpha = 0.5f) else MaterialTheme.colorScheme.outline.copy(
            alpha = 0.1f
        )
    val backgroundColor = MaterialTheme.colorScheme.surface

    Column(
        modifier = modifier.clip(RoundedCornerShape(12.dp)).background(backgroundColor)
            .border(1.dp, borderColor, RoundedCornerShape(12.dp)).combinedClickable(
                onClick = onCategoryClick, onLongClick = onCategoryLongClick
            ).padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(contentAlignment = Alignment.TopEnd) {
            Box(
                modifier = Modifier.size(40.dp)
                    .background(category.color.copy(alpha = 0.1f), RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = category.icon,
                    contentDescription = category.name,
                    tint = category.color,
                    modifier = Modifier.size(20.dp)
                )
            }
            if (isSelected) {
                Box(
                    modifier = Modifier.size(14.dp).offset(x = 4.dp, y = (-4).dp)
                        .background(MaterialTheme.colorScheme.primary, CircleShape)
                        .border(1.dp, Color.White, CircleShape), contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Check,
                        contentDescription = "Selected",
                        tint = Color.White,
                        modifier = Modifier.size(10.dp)
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = category.name,
            style = MaterialTheme.typography.labelSmall,
            fontSize = 10.sp,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurface,
            maxLines = 1
        )
    }
}

@Preview(name = "Item Light Mode", showBackground = true)
@Composable
fun CategoryGridItemLightPreview() {
    PocketGoalsTheme(themeMode = ThemeMode.LIGHT) {
        CategoryItemPreviewRow()
    }
}

@Preview(name = "Item Dark Mode", showBackground = true)
@Composable
fun CategoryGridItemDarkPreview() {
    PocketGoalsTheme(themeMode = ThemeMode.DARK) {
        CategoryItemPreviewRow()
    }
}

@Composable
private fun CategoryItemPreviewRow() {
    val mockCategory = remember {
        ExpenseCategory(
            name = "Food | Dining", color = Color(0xFFF59E0B), icon = Icons.Rounded.Restaurant
        )
    }

    Row(
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Unselected Item Preview
        CategoryGridItem(
            category = mockCategory,
            isSelected = false,
            onCategoryClick = {},
            onCategoryLongClick = {},
            modifier = Modifier.width(76.dp)
        )

        // Selected Item Preview
        CategoryGridItem(
            category = mockCategory,
            isSelected = true,
            onCategoryClick = {},
            onCategoryLongClick = {},
            modifier = Modifier.width(76.dp)
        )
    }
}