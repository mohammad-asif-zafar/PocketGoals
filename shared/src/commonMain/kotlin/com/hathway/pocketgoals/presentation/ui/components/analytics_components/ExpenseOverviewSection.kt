package com.hathway.pocketgoals.presentation.ui.components.analytics_components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hathway.pocketgoals.domain.model.ThemeMode
import com.hathway.pocketgoals.presentation.ui.localization.CurrencyConfig
import com.hathway.pocketgoals.presentation.ui.localization.CurrencyFormatter
import com.hathway.pocketgoals.presentation.ui.theme.PocketGoalsTheme
import org.jetbrains.compose.resources.stringResource
import pocketgoals.shared.generated.resources.Res
import pocketgoals.shared.generated.resources.expense
import pocketgoals.shared.generated.resources.overview
import pocketgoals.shared.generated.resources.total

@Composable
fun ExpenseOverviewSection(
    totalAmount: String = "0",
    categories: List<AnalyticsCategoryData> = emptyList(),
    currencyConfig: CurrencyConfig = CurrencyConfig.fromSystemLocale()
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = stringResource(Res.string.expense) + " "+stringResource(Res.string.overview),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(vertical = 16.dp),
            color = MaterialTheme.colorScheme.onBackground
        )

        if (categories.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxWidth().height(150.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No data available",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        } else {
            Row(
                modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
            ) {
                // Donut Chart
                Box(
                    modifier = Modifier.size(150.dp), contentAlignment = Alignment.Center
                ) {
                    Canvas(modifier = Modifier.size(140.dp)) {
                        var startAngle = -90f
                        categories.forEach { category ->
                            val percentageMatch = "\\((\\d+)%\\)".toRegex().find(category.value)
                            val percent =
                                percentageMatch?.groupValues?.get(1)?.toFloatOrNull() ?: 0f
                            val sweepAngle = (percent / 100f) * 360f
                            drawArc(
                                color = category.color,
                                startAngle = startAngle,
                                sweepAngle = sweepAngle,
                                useCenter = false,
                                style = Stroke(width = 25.dp.toPx(), cap = StrokeCap.Butt)
                            )
                            startAngle += sweepAngle
                        }
                    }

                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = CurrencyFormatter.formatAmount(
                                totalAmount, currencyConfig
                            ),
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        Text(
                            text = stringResource(Res.string.total),
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                Spacer(modifier = Modifier.width(24.dp))

                // Legend
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    categories.take(7).forEach { category ->
                        val percentageMatch = "\\((\\d+)%\\)".toRegex().find(category.value)
                        val percent = percentageMatch?.groupValues?.get(1) ?: "0"
                        LegendItem(category.name, "$percent%", category.color)
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun ExpenseOverviewSectionLightPreview() {
    PocketGoalsTheme(themeMode = ThemeMode.LIGHT) {
        Box(modifier = Modifier.background(MaterialTheme.colorScheme.background).padding(16.dp)) {
            ExpenseOverviewSection()
        }
    }
}

@Preview
@Composable
fun ExpenseOverviewSectionDarkPreview() {
    PocketGoalsTheme(themeMode = ThemeMode.DARK) {
        Box(modifier = Modifier.background(MaterialTheme.colorScheme.background).padding(16.dp)) {
            ExpenseOverviewSection()
        }
    }
}
