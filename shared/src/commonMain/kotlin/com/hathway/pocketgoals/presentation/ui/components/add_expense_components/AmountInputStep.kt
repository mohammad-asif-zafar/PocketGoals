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
import com.hathway.pocketgoals.presentation.ui.theme.PocketGoalsTheme

@Composable
fun AmountInputStep(
    amount: String,
    onAmountChange: (String) -> Unit,
    onDone: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(5.dp))

        Text(
            text = "Enter Amount",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "₹",
                fontSize = 48.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(end = 8.dp)
            )
            Text(
                text = amount,
                fontSize = 56.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
        }

        Spacer(modifier = Modifier.weight(1.2f))

        CustomNumberPad(
            onNumberClick = { num ->
                if (amount == "0") onAmountChange(num)
                else onAmountChange(amount + num)
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
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            AmountInputStep(
                amount = "1250",
                onAmountChange = {},
                onDone = {}
            )
        }
    }
}

@Preview
@Composable
fun AmountInputStepDarkPreview() {
    PocketGoalsTheme(themeMode = ThemeMode.DARK) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            AmountInputStep(
                amount = "4800",
                onAmountChange = {},
                onDone = {}
            )
        }
    }
}