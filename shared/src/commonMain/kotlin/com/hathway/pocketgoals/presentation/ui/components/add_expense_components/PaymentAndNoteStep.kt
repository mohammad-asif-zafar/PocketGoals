package com.hathway.pocketgoals.presentation.ui.components.add_expense_components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.Notes
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.hathway.pocketgoals.domain.model.PaymentMethodType
import com.hathway.pocketgoals.domain.model.ThemeMode
import com.hathway.pocketgoals.presentation.ui.theme.PocketGoalsTheme
import org.jetbrains.compose.resources.stringResource
import pocketgoals.shared.generated.resources.Res
import pocketgoals.shared.generated.resources.label_payment_method
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.resources.StringResource
import pocketgoals.shared.generated.resources.label_note_optional
import pocketgoals.shared.generated.resources.method_bank
import pocketgoals.shared.generated.resources.method_card
import pocketgoals.shared.generated.resources.method_cash
import pocketgoals.shared.generated.resources.method_upi

@Composable
fun PaymentAndNoteStep(
    selectedMethod: PaymentMethodType,
    onMethodSelected: (PaymentMethodType) -> Unit,
    noteText: String,
    onNoteChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize().padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        // Section A: Payment Selection Grid
        Text(
            text = stringResource(Res.string.label_payment_method),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(PaymentMethodType.values()) { method ->
                val isSelected = method == selectedMethod
                val stringRes = when (method) {
                    PaymentMethodType.CASH -> Res.string.method_cash
                    PaymentMethodType.CARD -> Res.string.method_card
                    PaymentMethodType.UPI -> Res.string.method_upi
                    PaymentMethodType.BANK -> Res.string.method_bank
                    else -> {}
                }

                Surface(
                    onClick = { onMethodSelected(method) },
                    shape = RoundedCornerShape(16.dp),
                    color = if (isSelected) MaterialTheme.colorScheme.primary.copy(alpha = 0.1f) else MaterialTheme.colorScheme.surface,
                    border = BorderStroke(
                        width = if (isSelected) 2.dp else 1.dp,
                        color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline.copy(
                            alpha = 0.3f
                        )
                    ),
                    modifier = Modifier.height(72.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Icon(
                            imageVector = method.icon,
                            contentDescription = null,
                            tint = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            text = stringResource(stringRes as StringResource),
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                            color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Section B: Note Area Input
        Text(
            text = stringResource(Res.string.label_note_optional),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )

        OutlinedTextField(
            value = noteText,
            onValueChange = onNoteChange,
            placeholder = { Text(text = stringResource(Res.string.label_note_optional)) },
            leadingIcon = {
                Icon(
                    Icons.AutoMirrored.Rounded.Notes,
                    null,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            },
            shape = RoundedCornerShape(16.dp),
            minLines = 3,
            maxLines = 5,
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = MaterialTheme.colorScheme.onSurface,
                unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f)
            ),
            modifier = Modifier.fillMaxWidth()
        )
    }
}


@Preview(name = "Payment Step Light", showBackground = true, widthDp = 360, heightDp = 640)
@Composable
fun PaymentAndNoteStepLightPreview() {
    PocketGoalsTheme(themeMode = ThemeMode.LIGHT) {
        PaymentAndNoteStepPreviewContainer()
    }
}

@Preview(name = "Payment Step Dark", showBackground = true, widthDp = 360, heightDp = 640)
@Composable
fun PaymentAndNoteStepDarkPreview() {
    PocketGoalsTheme(themeMode = ThemeMode.DARK) {
        PaymentAndNoteStepPreviewContainer()
    }
}

@Composable
private fun PaymentAndNoteStepPreviewContainer() {
    var selectedMethod by remember { mutableStateOf(PaymentMethodType.CASH) }
    var noteText by remember { mutableStateOf("") }

    PaymentAndNoteStep(
        selectedMethod = selectedMethod,
        onMethodSelected = { selectedMethod = it },
        noteText = noteText,
        onNoteChange = { noteText = it })
}
