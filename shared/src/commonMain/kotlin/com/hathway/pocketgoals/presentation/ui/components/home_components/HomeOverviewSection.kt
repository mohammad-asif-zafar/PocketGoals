package com.hathway.pocketgoals.presentation.ui.components.home_components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hathway.pocketgoals.domain.model.ThemeMode
import com.hathway.pocketgoals.presentation.ui.localization.CurrencyConfig
import com.hathway.pocketgoals.presentation.ui.localization.CurrencyFormatter
import com.hathway.pocketgoals.presentation.ui.theme.Danger
import com.hathway.pocketgoals.presentation.ui.theme.Info
import com.hathway.pocketgoals.presentation.ui.theme.PocketGoalsTheme
import com.hathway.pocketgoals.presentation.ui.theme.Success
import org.jetbrains.compose.resources.stringResource
import pocketgoals.shared.generated.resources.*

@Composable
fun HomeOverviewSection(
    income: String,
    expenses: String,
    savings: String,
    modifier: Modifier = Modifier,
    currencyConfig: CurrencyConfig = CurrencyConfig.fromSystemLocale()
) {
    Column(modifier = modifier) {
        Text(
            text = stringResource(Res.string.overview_this_month),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(16.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // FIX: Swapped hardcoded hex values with the designated semantic brand color tokens
                HomeOverviewItem(
                    stringResource(Res.string.income),
                    CurrencyFormatter.formatAmount(income, currencyConfig),
                    Success,
                    modifier = Modifier.weight(1f)
                )
                VerticalDivider(modifier = Modifier.height(40.dp).padding(horizontal = 4.dp))

                HomeOverviewItem(
                    stringResource(Res.string.expense),
                    CurrencyFormatter.formatAmount(expenses, currencyConfig),
                    Danger,
                    modifier = Modifier.weight(1f)
                )
                VerticalDivider(modifier = Modifier.height(40.dp).padding(horizontal = 4.dp))

                HomeOverviewItem(
                    stringResource(Res.string.savings),
                    CurrencyFormatter.formatAmount(savings, currencyConfig),
                    Info,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Preview(name = "Home Overview - Light Theme")
@Composable
private fun HomeOverviewSectionLightPreview() {
    PocketGoalsTheme(themeMode = ThemeMode.LIGHT) {
        Surface(color = MaterialTheme.colorScheme.background, modifier = Modifier.padding(16.dp)) {
            HomeOverviewSection(income = "₹ 1,25,000", expenses = "₹ 42,300", savings = "₹ 82,700")
        }
    }
}

@Preview(name = "Home Overview - Dark Theme")
@Composable
private fun HomeOverviewSectionDarkPreview() {
    PocketGoalsTheme(themeMode = ThemeMode.DARK) {
        Surface(color = MaterialTheme.colorScheme.background, modifier = Modifier.padding(16.dp)) {
            HomeOverviewSection(income = "₹ 85,000", expenses = "₹ 61,000", savings = "₹ 24,000")
        }
    }
}
