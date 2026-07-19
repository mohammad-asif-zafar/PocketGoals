package com.hathway.pocketgoals.presentation.ui.components.analytics_components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Kitchen
import androidx.compose.material.icons.rounded.Work
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hathway.pocketgoals.domain.model.ThemeMode
import com.hathway.pocketgoals.presentation.ui.theme.PocketGoalsTheme
import org.jetbrains.compose.resources.stringResource
import pocketgoals.shared.generated.resources.Res
import pocketgoals.shared.generated.resources.analytics_see_all
import pocketgoals.shared.generated.resources.analytics_top_categories

@Composable
fun TopCategoriesAnalyticsList(
    categories: List<AnalyticsCategoryData>,
    onSeeAllClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(top = 24.dp, bottom = 16.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp) // Handles inner cell item spacing elegantly
    ) {
        // Sticky/Static Header inside the LazyColumn
        item {
            Row(
                modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(Res.string.analytics_top_categories),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    text = stringResource(Res.string.analytics_see_all),
                    style = MaterialTheme.typography.bodySmall,
                    color = Color(0xFF10B981),
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.clickable { onSeeAllClick() })
            }
        }

        // Efficiently scrolls and recycles lists of any scale size
        items(
            items = categories,
            key = { it.name } // Performance boost: helps Compose keep track of reordered items
        ) { category ->
            AnalyticsCategoryItem(
                name = category.name,
                value = category.value,
                color = category.color,
                icon = category.icon
            )
        }
    }
}

@Preview
@Composable
fun TopCategoriesAnalyticsListLightPreview() {
    val sampleData = listOf(
        AnalyticsCategoryData(
            "Investment", "₹31,023 (34.47%)", Color(0xFF3B82F6), Icons.Rounded.Work
        ), AnalyticsCategoryData(
            "Basic Needs", "₹25,029 (27.81%)", Color(0xFF10B981), Icons.Rounded.Home
        ), AnalyticsCategoryData(
            "Future Pay", "₹13,599 (15.11%)", Color(0xFFF59E0B), Icons.Rounded.Kitchen
        )
    )

    PocketGoalsTheme(themeMode = ThemeMode.LIGHT) {
        Box(
            modifier = Modifier.background(MaterialTheme.colorScheme.background)
                .padding(horizontal = 16.dp)
        ) {
            TopCategoriesAnalyticsList(categories = sampleData)
        }
    }
}

@Preview
@Composable
fun TopCategoriesAnalyticsListDarkPreview() {
    val sampleData = listOf(
        AnalyticsCategoryData(
            "Investment", "₹31,023 (34.47%)", Color(0xFF3B82F6), Icons.Rounded.Work
        ), AnalyticsCategoryData(
            "Basic Needs", "₹25,029 (27.81%)", Color(0xFF10B981), Icons.Rounded.Home
        ), AnalyticsCategoryData(
            "Future Pay", "₹13,599 (15.11%)", Color(0xFFF59E0B), Icons.Rounded.Kitchen
        )
    )

    PocketGoalsTheme(themeMode = ThemeMode.DARK) {
        Box(
            modifier = Modifier.background(MaterialTheme.colorScheme.background)
                .padding(horizontal = 16.dp)
        ) {
            TopCategoriesAnalyticsList(categories = sampleData)
        }
    }
}

data class AnalyticsCategoryData(
    val name: String,
    val value: String,
    val color: Color,
    val icon: ImageVector
)
