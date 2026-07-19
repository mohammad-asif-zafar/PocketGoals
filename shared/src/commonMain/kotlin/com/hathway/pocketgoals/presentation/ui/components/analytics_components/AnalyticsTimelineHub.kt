package com.hathway.pocketgoals.presentation.ui.components.analytics_components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.PictureAsPdf
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hathway.pocketgoals.domain.model.ThemeMode
import com.hathway.pocketgoals.domain.model.TransactionType
import com.hathway.pocketgoals.presentation.ui.theme.Danger
import com.hathway.pocketgoals.presentation.ui.theme.PocketGoalsTheme
import com.hathway.pocketgoals.presentation.ui.theme.Success
import org.jetbrains.compose.resources.stringResource
import pocketgoals.shared.generated.resources.Res
import pocketgoals.shared.generated.resources.timeline_empty
import pocketgoals.shared.generated.resources.timeline_header

// Mock data architecture matching your core models mapping
data class TimelineTransaction(
    val id: String,
    val title: String,
    val category: String,
    val date: String,
    val amount: Double,
    val isIncome: Boolean
)

@Composable
fun AnalyticsTimelineHub(
    barData: List<BarChartDataPoint>,
    donutData: List<DonutPieDataPoint>,
    timelineTransactions: List<TimelineTransaction>,
    selectedType: TransactionType,
    onTypeChange: (TransactionType) -> Unit,
    selectedPeriod: String,
    onPeriodChange: (String) -> Unit,
    onExportPdfClick: () -> Unit,
    onTransactionClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background,
        contentWindowInsets = WindowInsets(0, 0, 0, 0),

        // 1. EXTENDED PDF REPORT EXPORT ACTION CONTROL FAB
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = onExportPdfClick,
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
                shape = RoundedCornerShape(16.dp),
                elevation = FloatingActionButtonDefaults.elevation(defaultElevation = 4.dp),
                icon = { Icon(Icons.Rounded.PictureAsPdf, contentDescription = null) },
                text = { Text("Export PDF", fontWeight = FontWeight.Bold) })
        }) { paddingValues ->

        // 2. SCROLLABLE DASHBOARD LAYOUT TIMELINE
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(paddingValues),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            // Nested Analytics Components
            item {
                FullAnalyticsDashboard(
                    barData = barData,
                    donutData = donutData,
                    selectedType = selectedType,
                    onTypeChange = onTypeChange,
                    selectedPeriod = selectedPeriod,
                    onPeriodChange = onPeriodChange
                )
            }

            item {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = stringResource(Res.string.timeline_header),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }

            if (timelineTransactions.isEmpty()) {
                item {
                    Box(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 32.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = stringResource(Res.string.timeline_empty),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            } else {
                itemsIndexed(timelineTransactions) { index, tx ->
                    // Cascade entrance effect on items attach
                    var isVisible by remember { mutableStateOf(false) }
                    LaunchedEffect(Unit) { isVisible = true }

                    AnimatedVisibility(
                        visible = isVisible,
                        enter = slideInVertically(animationSpec = tween(400 + (index * 50))) + fadeIn()
                    ) {
                        TimelineItemNode(
                            transaction = tx,
                            isLastItem = index == timelineTransactions.lastIndex,
                            onClick = { onTransactionClick(tx.id) })
                    }
                }
            }

            // Extra scrolling buffer room so the FloatingActionButton doesn't obscure text elements
            item { Spacer(modifier = Modifier.height(72.dp)) }
        }
    }
}

@Composable
private fun TimelineItemNode(
    transaction: TimelineTransaction,
    isLastItem: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val nodeColor = if (transaction.isIncome) Success else Danger
    val sign = if (transaction.isIncome) "+" else "-"

    Row(
        modifier = modifier.fillMaxWidth().clickable { onClick() }.padding(vertical = 4.dp),
        verticalAlignment = Alignment.Top
    ) {
        // Timeline Graphical Node Indicator Bullet Rail Pipeline
        Column(
            horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.width(24.0.dp)
        ) {
            Box(
                modifier = Modifier.size(12.dp)
                    .background(nodeColor.copy(alpha = 0.2f), CircleShape).padding(3.dp)
                    .background(nodeColor, CircleShape)
            )
            if (!isLastItem) {
                Box(
                    modifier = Modifier.width(2.dp).height(54.dp)
                        .background(MaterialTheme.colorScheme.outline.copy(alpha = 0.3f))
                )
            }
        }

        Spacer(modifier = Modifier.width(16.dp))

        // Transaction Info Text Row Meta
        Row(
            modifier = Modifier.weight(1f),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = transaction.title,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "${transaction.category} • ${transaction.date}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Text(
                text = "$sign ${transaction.amount.toInt()}",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
                color = nodeColor,
                textAlign = TextAlign.End
            )
        }
    }
}

private val mockTimelineItems = listOf(
    TimelineTransaction("1", "Client Retainer Payout", "Freelance", "July 12, 2026", 8500.0, true),
    TimelineTransaction("2", "Whole Foods Market", "Groceries", "July 11, 2026", 1250.0, false),
    TimelineTransaction("3", "Adidas Running Shoes", "Shopping", "July 09, 2026", 4300.0, false),
    TimelineTransaction("4", "Stock Portfolio Dividend", "Investment", "July 05, 2026", 620.0, true)
)

@Preview
@Composable
private fun TimelineHubLightPreview() {
    PocketGoalsTheme(themeMode = ThemeMode.LIGHT) {
        Surface {
            AnalyticsTimelineHub(
                barData = previewBars,
                donutData = previewDonut,
                timelineTransactions = mockTimelineItems,
                selectedType = TransactionType.EXPENSE,
                onTypeChange = {},
                selectedPeriod = "This Month",
                onPeriodChange = {},
                onExportPdfClick = {},
                onTransactionClick = {})
        }
    }
}

@Preview
@Composable
private fun TimelineHubDarkPreview() {
    PocketGoalsTheme(themeMode = ThemeMode.DARK) {
        Surface {
            AnalyticsTimelineHub(
                barData = previewBars,
                donutData = previewDonut,
                timelineTransactions = mockTimelineItems,
                selectedType = TransactionType.EXPENSE,
                onTypeChange = {},
                selectedPeriod = "This Month",
                onPeriodChange = {},
                onExportPdfClick = {},
                onTransactionClick = {})
        }
    }
}