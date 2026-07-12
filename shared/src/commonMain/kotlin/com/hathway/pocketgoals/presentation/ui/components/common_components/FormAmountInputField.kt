package com.hathway.pocketgoals.presentation.ui.components.common_components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hathway.pocketgoals.domain.model.ThemeMode
import com.hathway.pocketgoals.presentation.ui.components.add_expense_components.FormField
import com.hathway.pocketgoals.presentation.ui.theme.PocketGoalsTheme
import com.hathway.pocketgoals.presentation.ui.util.CurrencyInputValidator


@Composable
fun FormAmountInputField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    errorMessage: String? = null,
    shakeTrigger: Any? = null // Pass error tracking toggle keys to rattle layout
) {
    FormField(
        label = label,
        // Apply the physical shake constraint modifier to the outermost element node
        modifier = modifier.shake(shakeTrigger), errorMessage = errorMessage
    ) {
        OutlinedTextField(
            value = value,
            onValueChange = { rawInput ->
                val fullySanitized = CurrencyInputValidator.formatAmountInput(rawInput)
                onValueChange(fullySanitized)
            },
            isError = errorMessage != null,
            singleLine = true,
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
            // Plugs the prefix text layout transformation logic array mapping directly here
            visualTransformation = CurrencyVisualTransformation(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.outline
            )
        )
    }
}


@Preview(name = "Amount Field Mask - Light Theme")
@Composable
private fun FormAmountInputFieldLightPreview() {
    var amountState by remember { mutableStateOf("4500") }

    PocketGoalsTheme(themeMode = ThemeMode.LIGHT) {
        Surface(color = MaterialTheme.colorScheme.background, modifier = Modifier.padding(16.dp)) {
            FormAmountInputField(
                label = "Target Goal Capital",
                value = amountState,
                onValueChange = { amountState = it })
        }
    }
}

@Preview(name = "Amount Field Shake Error - Dark Theme")
@Composable
private fun FormAmountInputFieldDarkPreview() {
    var amountState by remember { mutableStateOf("") }
    var errorToggleTrigger by remember { mutableStateOf(false) }

    PocketGoalsTheme(themeMode = ThemeMode.DARK) {
        Surface(color = MaterialTheme.colorScheme.background, modifier = Modifier.padding(16.dp)) {
            Column {
                FormAmountInputField(
                    label = "Target Goal Capital",
                    value = amountState,
                    onValueChange = { amountState = it },
                    errorMessage = "Initial opening balances cannot be empty",
                    shakeTrigger = errorToggleTrigger
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(onClick = { errorToggleTrigger = !errorToggleTrigger }) {
                    Text("Simulate Validation Submission Crash")
                }
            }
        }
    }
}
