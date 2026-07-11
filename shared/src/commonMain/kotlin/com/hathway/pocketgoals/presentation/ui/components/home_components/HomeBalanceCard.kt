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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.rounded.BarChart
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hathway.pocketgoals.domain.model.ThemeMode
import com.hathway.pocketgoals.presentation.ui.theme.PocketGoalsTheme

@Composable
fun HomeBalanceCard(totalBalance: String, income: String, expenses: String) {
    var isBalanceVisible by remember { mutableStateOf(true) }
    val hiddenMask = "•••••"

    val fadeTransitionSpec = fadeIn(animationSpec = tween(220)) togetherWith fadeOut(animationSpec = tween(180))

    // 1. Create a synchronized transition state for the visibility toggle
    val visibilityTransition = updateTransition(targetState = isBalanceVisible, label = "VisibilityTransition")

    // 2. Animate rotation degrees smoothly based on the privacy state (0 to 180 degrees)
    val iconRotation by visibilityTransition.animateFloat(
        transitionSpec = { tween(durationMillis = 300) },
        label = "IconRotation"
    ) { visible ->
        if (visible) 0f else 180f
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color(0xFF0F766E), Color(0xFF042F2E))
                    )
                )
                .padding(20.dp)
        ) {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Total Balance",
                        color = Color.White.copy(alpha = 0.8f),
                        style = MaterialTheme.typography.bodyMedium
                    )

                    // 3. Render the animated rotating icon
                    Icon(
                        imageVector = if (isBalanceVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                        contentDescription = if (isBalanceVisible) "Hide balance" else "Show balance",
                        tint = Color.White.copy(alpha = 0.8f),
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                            .clickable { isBalanceVisible = !isBalanceVisible }
                            // Apply the dynamic rotation angle natively via the graphicsLayer property
                            .graphicsLayer(rotationZ = iconRotation)
                            .padding(6.dp)
                    )
                }

                AnimatedContent(
                    targetState = isBalanceVisible,
                    transitionSpec = { fadeTransitionSpec },
                    label = "MainBalanceAnim"
                ) { visible ->
                    Text(
                        text = if (visible) "₹$totalBalance" else "₹$hiddenMask",
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
                            text = "Income",
                            color = Color.White.copy(alpha = 0.8f),
                            style = MaterialTheme.typography.labelMedium
                        )
                        AnimatedContent(
                            targetState = isBalanceVisible,
                            transitionSpec = { fadeTransitionSpec },
                            label = "IncomeAnim"
                        ) { visible ->
                            Text(
                                text = if (visible) "₹$income" else "₹$hiddenMask",
                                color = Color.White,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }

                    Column(horizontalAlignment = Alignment.End) {
                        Text(
                            text = "Expenses",
                            color = Color.White.copy(alpha = 0.8f),
                            style = MaterialTheme.typography.labelMedium
                        )
                        AnimatedContent(
                            targetState = isBalanceVisible,
                            transitionSpec = { fadeTransitionSpec },
                            label = "ExpensesAnim"
                        ) { visible ->
                            Text(
                                text = if (visible) "₹$expenses" else "₹$hiddenMask",
                                color = Color.White,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }

                    Icon(
                        imageVector = Icons.Rounded.BarChart,
                        contentDescription = "Stats",
                        tint = Color.White.copy(alpha = 0.6f),
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun HomeBalanceCardLightPreview() {
    PocketGoalsTheme(themeMode = ThemeMode.LIGHT) {
        Box(modifier = Modifier.background(MaterialTheme.colorScheme.background).padding(16.dp)) {
            HomeBalanceCard(totalBalance = "1,45,000", income = "2,00,000", expenses = "55,000")
        }
    }
}


@Preview
@Composable
fun HomeBalanceCardDarkPreview() {
    PocketGoalsTheme(themeMode = ThemeMode.DARK) {
        Box(modifier = Modifier.background(MaterialTheme.colorScheme.background).padding(16.dp)) {
            HomeBalanceCard(totalBalance = "1,45,000", income = "2,00,000", expenses = "55,000")
        }
    }
}

