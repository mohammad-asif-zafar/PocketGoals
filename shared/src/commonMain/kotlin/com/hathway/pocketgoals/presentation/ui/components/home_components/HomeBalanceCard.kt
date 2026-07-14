package com.hathway.pocketgoals.presentation.ui.components.home_components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.rounded.BarChart
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.hathway.pocketgoals.domain.model.ThemeMode
import com.hathway.pocketgoals.presentation.ui.localization.CurrencyConfig
import com.hathway.pocketgoals.presentation.ui.localization.CurrencyFormatter
import com.hathway.pocketgoals.presentation.ui.theme.PocketGoalsTheme
import org.jetbrains.compose.resources.stringResource
import pocketgoals.shared.generated.resources.Res
import pocketgoals.shared.generated.resources.balance_expenses
import pocketgoals.shared.generated.resources.balance_hide
import pocketgoals.shared.generated.resources.balance_income
import pocketgoals.shared.generated.resources.balance_show
import pocketgoals.shared.generated.resources.balance_stats
import pocketgoals.shared.generated.resources.balance_total
import pocketgoals.shared.generated.resources.currency_symbol

@Composable
fun HomeBalanceCard(
    totalBalance: String,
    income: String,
    expenses: String,
    currencyConfig: CurrencyConfig = CurrencyConfig.fromSystemLocale(),
    onStatsClick: () -> Unit
) {
    var isBalanceVisible by remember { mutableStateOf(true) }
    val maskText = "•••••"

    // Multi-variant formatting hooks
    val displayBalance = if (isBalanceVisible) CurrencyFormatter.formatAmount(
        totalBalance, currencyConfig
    ) else maskText
    val displayIncome =
        if (isBalanceVisible) CurrencyFormatter.formatAmount(income, currencyConfig) else maskText
    val displayExpenses =
        if (isBalanceVisible) CurrencyFormatter.formatAmount(expenses, currencyConfig) else maskText

    val fadeTransitionSpec =
        fadeIn(animationSpec = tween(220)) togetherWith fadeOut(animationSpec = tween(180))
    val visibilityTransition =
        updateTransition(targetState = isBalanceVisible, label = "VisibilityTransition")

    val iconRotation by visibilityTransition.animateFloat(
        transitionSpec = { tween(durationMillis = 300) }, label = "IconRotation"
    ) { visible -> if (visible) 0f else 180f }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Box(
            modifier = Modifier.fillMaxWidth()
                .background(Brush.verticalGradient(listOf(Color(0xFF0F766E), Color(0xFF042F2E))))
                .padding(20.dp)
        ) {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(Res.string.balance_total),
                        color = Color.White.copy(alpha = 0.8f),
                        style = MaterialTheme.typography.bodyMedium
                    )

                    Icon(
                        imageVector = if (isBalanceVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                        contentDescription = stringResource(if (isBalanceVisible) Res.string.balance_hide else Res.string.balance_show),
                        tint = Color.White.copy(alpha = 0.8f),
                        modifier = Modifier.size(32.dp).clip(CircleShape)
                            .clickable { isBalanceVisible = !isBalanceVisible }
                            .graphicsLayer(rotationZ = iconRotation).padding(6.dp))
                }

                AnimatedContent(
                    targetState = displayBalance,
                    transitionSpec = { fadeTransitionSpec },
                    label = "Balance"
                ) { text ->
                    Text(
                        text = text,
                        color = Color.White,
                        style = MaterialTheme.typography.headlineLarge,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }

                HorizontalDivider(
                    color = Color.White.copy(alpha = 0.2f),
                    thickness = 1.dp,
                    modifier = Modifier.padding(vertical = 12.dp)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(
                            text = stringResource(Res.string.balance_income),
                            color = Color.White.copy(alpha = 0.8f),
                            style = MaterialTheme.typography.labelMedium
                        )
                        AnimatedContent(
                            targetState = displayIncome,
                            transitionSpec = { fadeTransitionSpec },
                            label = "Income"
                        ) { text ->
                            Text(
                                text = text,
                                color = Color.White,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }

                    Column(horizontalAlignment = Alignment.End) {
                        Text(
                            text = stringResource(Res.string.balance_expenses),
                            color = Color.White.copy(alpha = 0.8f),
                            style = MaterialTheme.typography.labelMedium
                        )
                        AnimatedContent(
                            targetState = displayExpenses,
                            transitionSpec = { fadeTransitionSpec },
                            label = "Expenses"
                        ) { text ->
                            Text(
                                text = text,
                                color = Color.White,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }

                    Icon(
                        imageVector = Icons.Rounded.BarChart,
                        contentDescription = stringResource(Res.string.balance_stats),
                        tint = Color.White.copy(alpha = 0.6f),
                        modifier = Modifier.align(Alignment.CenterVertically).size(28.dp)
                            .clip(CircleShape)
                            .clickable { onStatsClick() } // Triggers the navigation callback action
                            .padding(4.dp))
                }
            }
        }
    }
}

// ==========================================================
// Comprehensive Home Dashboard Preview Matrix
// ==========================================================

@Preview(name = "Balance - India (Light Mode)", group = "Currency Matrix")
@Composable
private fun BalanceCardIndiaLightPreview() {
    PocketGoalsTheme(themeMode = ThemeMode.LIGHT) {
        Surface(color = MaterialTheme.colorScheme.background, modifier = Modifier.padding(16.dp)) {
            HomeBalanceCard(
                totalBalance = "82700",
                income = "125000",
                expenses = "42300",
                currencyConfig = CurrencyConfig.INR,
                onStatsClick = {}
            )
        }
    }
}

@Preview(name = "Balance - Malaysia (Dark Mode)", group = "Currency Matrix")
@Composable
private fun BalanceCardMalaysiaDarkPreview() {
    PocketGoalsTheme(themeMode = ThemeMode.DARK) {
        Surface(color = MaterialTheme.colorScheme.background, modifier = Modifier.padding(16.dp)) {
            HomeBalanceCard(
                totalBalance = "24000",
                income = "85000",
                expenses = "61000",
                currencyConfig = CurrencyConfig.MYR,
                onStatsClick = {}
            )
        }
    }
}

@Preview(name = "Balance - Arabic/AED (RTL Layout)", group = "Currency Matrix")
@Composable
private fun BalanceCardArabicRtlPreview() {
    PocketGoalsTheme(themeMode = ThemeMode.DARK) {
        // Forces Right-To-Left direction pass to test the layout with the postfix currency symbol rule
        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
            Surface(color = MaterialTheme.colorScheme.background, modifier = Modifier.padding(16.dp)) {
                HomeBalanceCard(
                    totalBalance = "53400",
                    income = "90000",
                    expenses = "36600",
                    currencyConfig = CurrencyConfig.AED,
                    onStatsClick = {}
                )
            }
        }
    }
}

@Composable
fun CurrencyPreviewRowDark(
    title: String, isRtl: Boolean, config: CurrencyConfig, total: String, inc: String, exp: String
) {
    Column(modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp)) {
        Text(text = title, style = MaterialTheme.typography.labelMedium, color = Color(0xFF334155))
        PocketGoalsTheme(themeMode = ThemeMode.DARK) {
            CompositionLocalProvider(LocalLayoutDirection provides if (isRtl) LayoutDirection.Rtl else LayoutDirection.Ltr) {
                Surface(
                    modifier = Modifier.fillMaxWidth(), color = MaterialTheme.colorScheme.background
                ) {
                    Box(modifier = Modifier.padding(8.dp)) {
                        HomeBalanceCard(
                            totalBalance = total,
                            income = inc,
                            expenses = exp,
                            currencyConfig = config,
                            onStatsClick = {})
                    }
                }
            }
        }
    }
}


@Composable
fun CurrencyPreviewRow(
    title: String, isRtl: Boolean, config: CurrencyConfig, total: String, inc: String, exp: String
) {
    Column(modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp)) {
        Text(text = title, style = MaterialTheme.typography.labelMedium, color = Color(0xFF334155))
        PocketGoalsTheme(themeMode = ThemeMode.LIGHT) {
            CompositionLocalProvider(LocalLayoutDirection provides if (isRtl) LayoutDirection.Rtl else LayoutDirection.Ltr) {
                Surface(
                    modifier = Modifier.fillMaxWidth(), color = MaterialTheme.colorScheme.background
                ) {
                    Box(modifier = Modifier.padding(8.dp)) {
                        HomeBalanceCard(
                            totalBalance = total,
                            income = inc,
                            expenses = exp,
                            currencyConfig = config,
                            onStatsClick = {})
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun FullCurrencyMatrixPreview() {
    Column(
        modifier = Modifier.fillMaxSize().background(Color(0xFFE2E8F0))
            .verticalScroll(rememberScrollState()).padding(16.dp)
    ) {
        Text(
            "💰 DYNAMIC LOCALIZATION AND CURRENCY GRID",
            style = MaterialTheme.typography.titleMedium,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(12.dp))

        CurrencyPreviewRow(
            "Global - Default English (USD Prefix)",
            false,
            CurrencyConfig.USD,
            "5400",
            "1200",
            "450"
        )
    }
}


@Preview
@Composable
fun FullCurrencyMatrixPreviewDark() {
    Column(
        modifier = Modifier.fillMaxSize().background(Color(0xFFE2E8F0))
            .verticalScroll(rememberScrollState()).padding(16.dp)
    ) {
        Text(
            "💰 DYNAMIC LOCALIZATION AND CURRENCY GRID",
            style = MaterialTheme.typography.titleMedium,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(12.dp))


        CurrencyPreviewRowDark(
            "Global - Default English (USD Prefix)",
            false,
            CurrencyConfig.USD,
            "5400",
            "1200",
            "450"
        )
    }
}