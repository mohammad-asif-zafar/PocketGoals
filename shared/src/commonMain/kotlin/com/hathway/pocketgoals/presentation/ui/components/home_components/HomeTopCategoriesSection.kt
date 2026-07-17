package com.hathway.pocketgoals.presentation.ui.components.home_components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.TrendingUp
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Kitchen
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hathway.pocketgoals.domain.model.ThemeMode
import com.hathway.pocketgoals.presentation.ui.components.analytics_components.AnalyticsCategoryData
import com.hathway.pocketgoals.presentation.ui.theme.PocketGoalsTheme
import org.jetbrains.compose.resources.stringResource
import pocketgoals.shared.generated.resources.*

@Composable
fun HomeTopCategoriesSection(
    categories: List<AnalyticsCategoryData>
) {
    if (categories.isEmpty()) return

    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(Res.string.top_categories),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = stringResource(Res.string.see_all),
                style = MaterialTheme.typography.bodySmall,
                color = Color(0xFF0F766E),
                fontWeight = FontWeight.SemiBold
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        categories.take(4).forEach { category ->
            val percentageMatch = "\\((\\d+)%\\)".toRegex().find(category.value)
            val percentage = percentageMatch?.groupValues?.get(1)?.toDoubleOrNull() ?: 0.0
            val amount = category.value.split(" ").getOrNull(0) ?: "0" // Adjusted split

            HomeCategoryRow(
                category.name,
                percentage,
                amount,
                category.icon,
                category.color
            )
        }
    }
}

@Preview(name = "Home Top Categories - Light Mode")
@Composable
private fun HomeTopCategoriesSectionLightPreview() {
    val mockCategories = listOf(
        AnalyticsCategoryData("Investment", "₹31,029 (34%)", Color(0xFF3B82F6), Icons.AutoMirrored.Rounded.TrendingUp),
        AnalyticsCategoryData("Basic Needs", "₹25,029 (27%)", Color(0xFF10B981), Icons.Rounded.Home)
    )
    PocketGoalsTheme(themeMode = ThemeMode.LIGHT) {
        Surface(color = MaterialTheme.colorScheme.background, modifier = Modifier.padding(16.dp)) {
            HomeTopCategoriesSection(categories = mockCategories)
        }
    }
}

@Preview(name = "Home Top Categories - Dark Mode")
@Composable
private fun HomeTopCategoriesSectionDarkPreview() {
    val mockCategories = listOf(
        AnalyticsCategoryData("Investment", "₹31,029 (34%)", Color(0xFF3B82F6), Icons.AutoMirrored.Rounded.TrendingUp),
        AnalyticsCategoryData("Basic Needs", "₹25,029 (27%)", Color(0xFF10B981), Icons.Rounded.Home)
    )
    PocketGoalsTheme(themeMode = ThemeMode.DARK) {
        Surface(color = MaterialTheme.colorScheme.background, modifier = Modifier.padding(16.dp)) {
            HomeTopCategoriesSection(categories = mockCategories)
        }
    }
}
