package com.hathway.pocketgoals.presentation.ui.components.goals_components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hathway.pocketgoals.domain.model.ThemeMode
import com.hathway.pocketgoals.presentation.ui.theme.PocketGoalsTheme
import com.hathway.pocketgoals.presentation.ui.theme.Surface
import org.jetbrains.compose.resources.stringResource
import pocketgoals.shared.generated.resources.Res
import pocketgoals.shared.generated.resources.btn_next
import pocketgoals.shared.generated.resources.currency_symbol
import pocketgoals.shared.generated.resources.quick_select
import pocketgoals.shared.generated.resources.quick_select_other
import pocketgoals.shared.generated.resources.set_target_amount

@Composable
fun GoalAmountStep(
    amount: String,
    onAmountChange: (String) -> Unit,
    onOtherClick: () -> Unit, // Added handler to cleanly dispatch custom number keypad flows
    onNext: () -> Unit,
    modifier: Modifier = Modifier
) {
    val currencySymbol = stringResource(Res.string.currency_symbol)
    val otherLabel = stringResource(Res.string.quick_select_other)

    val quickSelectOptions = remember(currencySymbol, otherLabel) {
        listOf(
            "$currencySymbol 1,000",
            "$currencySymbol 10,000",
            "$currencySymbol 25,000",
            "$currencySymbol 50,000",
            "$currencySymbol 1,00,000",
            otherLabel
        )
    }

    val borderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)

    Column(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.weight(1f).padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.Top
        ) {
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = stringResource(Res.string.set_target_amount),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Live Display Output Canvas Card
            Box(
                modifier = Modifier.fillMaxWidth().height(64.dp).clip(RoundedCornerShape(12.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)).border(
                        1.dp,
                        MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
                        RoundedCornerShape(12.dp)
                    ).padding(horizontal = 16.dp), contentAlignment = Alignment.CenterStart
            ) {
                val isZero = amount == "0" || amount.isBlank()
                Text(
                    text = if (isZero) "$currencySymbol 0" else "$currencySymbol $amount",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = if (isZero) MaterialTheme.colorScheme.onSurfaceVariant else MaterialTheme.colorScheme.onSurface
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = stringResource(Res.string.quick_select),
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(12.dp))

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.weight(1f)
            ) {
                items(quickSelectOptions) { option ->
                    val cleanOptionValue = option.replace("$currencySymbol ", "").replace(",", "")
                    // Highlights grid card border if item match matches current data string
                    val isTileSelected = amount == cleanOptionValue && option != otherLabel

                    Box(
                        modifier = Modifier.height(48.dp).clip(RoundedCornerShape(12.dp))
                            .background(
                                if (isTileSelected) MaterialTheme.colorScheme.primary.copy(alpha = 0.08f)
                                else MaterialTheme.colorScheme.surface
                            ).border(
                                width = if (isTileSelected) 2.dp else 1.dp,
                                color = if (isTileSelected) MaterialTheme.colorScheme.primary else borderColor,
                                shape = RoundedCornerShape(12.dp)
                            ).clickable {
                                if (option != otherLabel) {
                                    onAmountChange(cleanOptionValue)
                                } else {
                                    onOtherClick()
                                }
                            }, contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = option,
                            fontWeight = if (isTileSelected) FontWeight.Bold else FontWeight.Medium,
                            color = if (isTileSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
        }

        Box(modifier = Modifier.padding(24.dp)) {
            Button(
                onClick = onNext,
                enabled = amount != "0" && amount.isNotBlank(),
                modifier = Modifier.fillMaxWidth().height(56.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                )
            ) {
                Text(text = stringResource(Res.string.btn_next), fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Preview(name = "Goal Capital Setup - Light Theme")
@Composable
private fun GoalAmountStepLightPreview() {
    PocketGoalsTheme(themeMode = ThemeMode.LIGHT) {
        Surface(color = MaterialTheme.colorScheme.background) {
            GoalAmountStep(
                amount = "25000", // Tests active selected feedback states highlights
                onAmountChange = {}, onOtherClick = {}, onNext = {})
        }
    }
}

@Preview(name = "Goal Capital Setup Empty - Dark Theme")
@Composable
private fun GoalAmountStepDarkPreview() {
    PocketGoalsTheme(themeMode = ThemeMode.DARK) {
        Surface(color = MaterialTheme.colorScheme.background) {
            GoalAmountStep(
                amount = "0", // Verifies placeholder styling configurations and disabled CTA buttons
                onAmountChange = {}, onOtherClick = {}, onNext = {})
        }
    }
}
