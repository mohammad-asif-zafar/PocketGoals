package com.hathway.pocketgoals.presentation.ui.components.add_expense_components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hathway.pocketgoals.domain.model.ThemeMode
import com.hathway.pocketgoals.presentation.ui.localization.CurrencyConfig
import com.hathway.pocketgoals.presentation.ui.theme.PocketGoalsTheme
import org.jetbrains.compose.resources.stringResource
import pocketgoals.shared.generated.resources.Res
import pocketgoals.shared.generated.resources.enter_amount

@Composable
fun AmountInputStep(
    amount: String,
    onAmountChange: (String) -> Unit,
    onDone: () -> Unit,
    currencyConfig: CurrencyConfig = CurrencyConfig.fromSystemLocale(),
    maxLength: Int = 12 // Keeps layout safe
) {
    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(5.dp))

        Text(
            text = stringResource(Res.string.enter_amount),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = currencyConfig.toString(),
                fontSize = 48.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(end = 8.dp)
            )
            Text(
                text = amount,
                maxLines = 2,
                fontSize = if (amount.length > 7) 30.sp else 56.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
        }

        Spacer(modifier = Modifier.weight(1.2f))

        CustomNumberPad(
            onNumberClick = { num ->
                val hasDot = amount.contains(".")

                when {
                    // 1. Prevent a second decimal point completely
                    num == "." && hasDot -> { /* Do nothing */ }

                    // 2. Turn leading dot entry like "." into "0." for clean input
                    num == "." && amount == "0" -> onAmountChange("0.")

                    // 3. Regular initial character replacement if current amount is just zero
                    amount == "0" && num != "." -> onAmountChange(num)

                    // 4. Append number safely under character length limits
                    amount.length < maxLength -> onAmountChange(amount + num)
                }
            },
            onDelete = {
                if (amount.length > 1) onAmountChange(amount.dropLast(1))
                else onAmountChange("0")
            },
            onDone = onDone
        )
    }
}

// ==========================================================
// Theme-Safe Split Previews
// ==========================================================

@Preview
@Composable
fun AmountInputStepLightPreview() {
    PocketGoalsTheme(themeMode = ThemeMode.LIGHT) {
        Surface(
            modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
        ) {
            AmountInputStep(amount = "1250", onAmountChange = {}, onDone = {})
        }
    }
}

@Preview
@Composable
fun AmountInputStepDarkPreview() {
    PocketGoalsTheme(themeMode = ThemeMode.DARK) {
        Surface(
            modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
        ) {
            AmountInputStep(amount = "4800", onAmountChange = {}, onDone = {})
        }
    }
}