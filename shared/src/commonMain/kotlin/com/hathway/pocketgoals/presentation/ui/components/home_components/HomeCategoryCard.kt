package com.hathway.pocketgoals.presentation.ui.components.home_components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ChevronRight
import androidx.compose.material.icons.rounded.Fastfood
import androidx.compose.material.icons.rounded.ShoppingBag
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hathway.pocketgoals.domain.model.HomeCategoryData
import com.hathway.pocketgoals.domain.model.ThemeMode
import com.hathway.pocketgoals.presentation.ui.theme.PocketGoalsTheme

@Composable
fun HomeCategoryCard(
    title: String,
    categories: List<HomeCategoryData>,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        // Elevates slightly in light mode and uses surface tinted surfaces for dark mode depth
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Dynamically loop over rows inside the elevated card shell bounds
            categories.forEach { data ->
                HomeCategoryRow(
                    name = data.name,
                    percentage = data.percentage,
                    amount = data.amount,
                    icon = data.icon,
                    color = data.color
                )
            }
        }
    }
}

@Preview
@Composable
fun HomeCategoryCardLightPreview() {
    val sampleCategories = listOf(
        HomeCategoryData("Shopping", 42.5, "12,450", Icons.Rounded.ShoppingBag, Color(0xFFEF4444)),
        HomeCategoryData("Food & Dining", 28.0, "8,200", Icons.Rounded.Fastfood, Color(0xFFF59E0B)),
        HomeCategoryData("Entertainment", 15.0, "4,400", Icons.Rounded.ChevronRight, Color(0xFF3B82F6))
    )

    PocketGoalsTheme(themeMode = ThemeMode.LIGHT) {
        Box(modifier = Modifier.background(MaterialTheme.colorScheme.background).padding(16.dp)) {
            HomeCategoryCard(title = "Monthly Spending", categories = sampleCategories)
        }
    }
}


@Preview
@Composable
fun HomeCategoryCardDarkPreview() {
    val sampleCategories = listOf(
        HomeCategoryData("Shopping", 42.5, "12,450", Icons.Rounded.ShoppingBag, Color(0xFFEF4444)),
        HomeCategoryData("Food & Dining", 28.0, "8,200", Icons.Rounded.Fastfood, Color(0xFFF59E0B)),
        HomeCategoryData("Entertainment", 15.0, "4,400", Icons.Rounded.Warning, Color(0xFF3B82F6))
    )

    PocketGoalsTheme(themeMode = ThemeMode.DARK) {
        Box(modifier = Modifier.background(MaterialTheme.colorScheme.background).padding(16.dp)) {
            HomeCategoryCard(title = "Monthly Spending", categories = sampleCategories)
        }
    }
}
