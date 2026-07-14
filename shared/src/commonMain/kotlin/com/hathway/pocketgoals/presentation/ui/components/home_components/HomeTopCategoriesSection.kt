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
import com.hathway.pocketgoals.presentation.ui.theme.PocketGoalsTheme
import org.jetbrains.compose.resources.stringResource
import pocketgoals.shared.generated.resources.*

@Composable
fun HomeTopCategoriesSection() {
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

        HomeCategoryRow(stringResource(Res.string.investment), 34.47, "31,029", Icons.AutoMirrored.Rounded.TrendingUp, Color(0xFF3B82F6))
        HomeCategoryRow(stringResource(Res.string.basic_needs), 27.81, "25,029", Icons.Rounded.Home, Color(0xFF10B981))
        HomeCategoryRow(stringResource(Res.string.future_pay), 15.11, "13,599", Icons.Rounded.Kitchen, Color(0xFFF59E0B))
        HomeCategoryRow(stringResource(Res.string.family_support), 9.27, "8,343", Icons.Rounded.Star, Color(0xFF8B5CF6))
    }
}

@Preview(name = "Home Top Categories - Light Mode")
@Composable
private fun HomeTopCategoriesSectionLightPreview() {
    PocketGoalsTheme(themeMode = ThemeMode.LIGHT) {
        Surface(color = MaterialTheme.colorScheme.background, modifier = Modifier.padding(16.dp)) {
            HomeTopCategoriesSection()
        }
    }
}

@Preview(name = "Home Top Categories - Dark Mode")
@Composable
private fun HomeTopCategoriesSectionDarkPreview() {
    PocketGoalsTheme(themeMode = ThemeMode.DARK) {
        Surface(color = MaterialTheme.colorScheme.background, modifier = Modifier.padding(16.dp)) {
            HomeTopCategoriesSection()
        }
    }
}