package com.hathway.pocketgoals.presentation.ui.components.goals_components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.Backspace
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hathway.pocketgoals.domain.model.ThemeMode
import com.hathway.pocketgoals.presentation.ui.theme.PocketGoalsTheme
import org.jetbrains.compose.resources.stringResource
import pocketgoals.shared.generated.resources.Res
import pocketgoals.shared.generated.resources.currency_symbol
import pocketgoals.shared.generated.resources.custom_amount_confirm
import pocketgoals.shared.generated.resources.custom_amount_title

// ==========================================================
// 1. HORIZONTAL STEP PROGRESS INDICATOR COMPONENT
// ==========================================================
@Composable
fun HorizontalStepProgressIndicator(
    currentStepIndex: Int, totalStepsCount: Int, modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth().padding(vertical = 12.dp, horizontal = 24.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        for (i in 0 until totalStepsCount) {
            val isCompleted = i < currentStepIndex
            val isActive = i == currentStepIndex

            val barColor = when {
                isCompleted || isActive -> MaterialTheme.colorScheme.primary
                else -> MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
            }

            val barAlpha = when {
                isActive -> 1f
                isCompleted -> 0.6f
                else -> 1f
            }

            Box(
                modifier = Modifier.weight(1f).height(6.dp).clip(CircleShape)
                    .background(barColor.copy(alpha = barAlpha))
            )
        }
    }
}

// ==========================================================
// 2. CUSTOM DATA ENTRY KEYPAD BOTTOM SHEET COMPONENT
// ==========================================================
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomKeypadBottomSheet(
    initialAmount: String,
    onAmountConfirmed: (String) -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    var customAmount by remember { mutableStateOf(if (initialAmount == "0") "" else initialAmount) }
    val currencySymbol = stringResource(Res.string.currency_symbol)

    val keypadKeys = remember {
        listOf("1", "2", "3", "4", "5", "6", "7", "8", "9", ".", "0", "BACKSPACE")
    }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        containerColor = MaterialTheme.colorScheme.surface,
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
                .padding(start = 24.dp, end = 24.dp, bottom = 40.dp, top = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(Res.string.custom_amount_title),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Real-Time Active Digit Text Counter Display Monitor
            Text(
                text = "$currencySymbol ${customAmount.ifEmpty { "0" }}",
                style = MaterialTheme.typography.displaySmall,
                fontWeight = FontWeight.Bold,
                color = if (customAmount.isEmpty()) MaterialTheme.colorScheme.onSurfaceVariant else MaterialTheme.colorScheme.primary,
                maxLines = 1,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // 3x4 Unified Keypad Numerical Matrix Layout Grid Pipeline
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth().wrapContentHeight()
            ) {
                items(keypadKeys) { key ->
                    Box(
                        modifier = Modifier.height(60.dp).clip(RoundedCornerShape(16.dp))
                            .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f))
                            .clickable {
                                when (key) {
                                    "BACKSPACE" -> {
                                        if (customAmount.isNotEmpty()) {
                                            customAmount = customAmount.dropLast(1)
                                        }
                                    }

                                    "." -> {
                                        if (!customAmount.contains(".") && customAmount.isNotEmpty()) {
                                            customAmount += "."
                                        }
                                    }

                                    else -> {
                                        // Restrict string limits to prevent layout text breaking bounds
                                        if (customAmount.length < 9) {
                                            customAmount += key
                                        }
                                    }
                                }
                            }, contentAlignment = Alignment.Center
                    ) {
                        if (key == "BACKSPACE") {
                            Icon(
                                imageVector = Icons.AutoMirrored.Rounded.Backspace,
                                contentDescription = "Delete last entry digit",
                                tint = MaterialTheme.colorScheme.onSurface
                            )
                        } else {
                            Text(
                                text = key,
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Save Confirmation Action Button Click Utility
            Button(
                onClick = {
                    onAmountConfirmed(customAmount.ifEmpty { "0" })
                    onDismiss()
                },
                enabled = customAmount.isNotEmpty() && customAmount != "0",
                modifier = Modifier.fillMaxWidth().height(56.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Text(
                    text = stringResource(Res.string.custom_amount_confirm),
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Preview(name = "Horizontal Step Rail Progress Line - Light Mode")
@Composable
private fun StepProgressIndicatorLightPreview() {
    PocketGoalsTheme(themeMode = ThemeMode.LIGHT) {
        Surface {
            Column(modifier = Modifier.padding(vertical = 16.dp)) {
                // Simulates step 2 active out of 4 stages complete
                HorizontalStepProgressIndicator(currentStepIndex = 1, totalStepsCount = 4)
            }
        }
    }
}

@Preview(name = "Custom Keypad Interface Board Drawer - Dark Mode")
@Composable
private fun CustomKeypadBottomSheetDarkPreview() {
    PocketGoalsTheme(themeMode = ThemeMode.DARK) {
        // Renders the input keypad window selector component drawer stand-alone context
        CustomKeypadBottomSheet(initialAmount = "1550", onAmountConfirmed = {}, onDismiss = {})
    }
}