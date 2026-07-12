package com.hathway.pocketgoals.presentation.ui.components.add_income_components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.Notes
import androidx.compose.material.icons.rounded.AccountBalanceWallet
import androidx.compose.material.icons.rounded.CalendarToday
import androidx.compose.material.icons.rounded.Category
import androidx.compose.material.icons.rounded.Payments
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hathway.pocketgoals.domain.IncomeType
import com.hathway.pocketgoals.domain.model.ThemeMode
import com.hathway.pocketgoals.presentation.ui.components.add_expense_components.FormField
import com.hathway.pocketgoals.presentation.ui.theme.PocketGoalsTheme
import org.jetbrains.compose.resources.stringResource
import pocketgoals.shared.generated.resources.Res
import pocketgoals.shared.generated.resources.btn_next
import pocketgoals.shared.generated.resources.currency_symbol
import pocketgoals.shared.generated.resources.label_amount
import pocketgoals.shared.generated.resources.label_date
import pocketgoals.shared.generated.resources.label_income_type
import pocketgoals.shared.generated.resources.label_note_optional
import pocketgoals.shared.generated.resources.label_payment_method
import pocketgoals.shared.generated.resources.placeholder_amount
import pocketgoals.shared.generated.resources.placeholder_note
import pocketgoals.shared.generated.resources.placeholder_payment_method


@Composable
fun AddIncomeFormStep(
    incomeType: IncomeType,
    onTypeClick: () -> Unit,
    amount: String,
    onAmountClick: () -> Unit,
    date: String,
    onDateClick: () -> Unit,
    method: String?,
    onMethodClick: () -> Unit,
    note: String,
    onNoteClick: () -> Unit,
    isNextEnabled: Boolean,
    onNext: () -> Unit
) {
    val scrollState = rememberScrollState()
    val borderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)

    Column(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(scrollState)
                .padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            // Income Type Field
            FormField(label = stringResource(Res.string.label_income_type)) {
                FormItemRow(
                    onClick = onTypeClick,
                    icon = incomeType.icon,
                    iconColor = incomeType.color,
                    text = incomeType.name,
                    borderColor = borderColor
                )
            }

            // Amount Field
            FormField(label = stringResource(Res.string.label_amount)) {
                val isZeroAmount = amount == "0"
                val displayAmount = if (isZeroAmount) {
                    stringResource(Res.string.placeholder_amount)
                } else {
                    "${stringResource(Res.string.currency_symbol)} $amount"
                }

                FormItemRow(
                    onClick = onAmountClick,
                    icon = Icons.Rounded.Payments,
                    iconColor = MaterialTheme.colorScheme.primary,
                    text = displayAmount,
                    isPlaceholder = isZeroAmount,
                    borderColor = borderColor
                )
            }

            // Date Field
            FormField(label = stringResource(Res.string.label_date)) {
                FormItemRow(
                    onClick = onDateClick,
                    icon = Icons.Rounded.CalendarToday,
                    iconColor = MaterialTheme.colorScheme.primary,
                    text = date,
                    borderColor = borderColor
                )
            }

            // Payment Method
            FormField(label = stringResource(Res.string.label_payment_method)) {
                FormItemRow(
                    onClick = onMethodClick,
                    icon = Icons.Rounded.AccountBalanceWallet,
                    iconColor = MaterialTheme.colorScheme.primary,
                    text = method ?: stringResource(Res.string.placeholder_payment_method),
                    isPlaceholder = method == null,
                    borderColor = borderColor
                )
            }

            // Note
            FormField(label = stringResource(Res.string.label_note_optional)) {
                val isNoteEmpty = note.isEmpty()
                FormItemRow(
                    onClick = onNoteClick,
                    icon = Icons.AutoMirrored.Rounded.Notes,
                    iconColor = MaterialTheme.colorScheme.primary,
                    text = if (isNoteEmpty) stringResource(Res.string.placeholder_note) else note,
                    isPlaceholder = isNoteEmpty,
                    borderColor = borderColor
                )
            }
            Spacer(modifier = Modifier.height(24.dp))
        }

        Box(modifier = Modifier.padding(24.dp)) {
            Button(
                onClick = onNext,
                enabled = isNextEnabled,
                modifier = Modifier.fillMaxWidth().height(56.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                )
            ) {
                Text(
                    text = stringResource(Res.string.btn_next),
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

// ==========================================================
// Mock Data Helpers for Component Previews
// ==========================================================
data class IncomeTypeMock(
    val name: String,
    val icon: ImageVector,
    val color: Color
)

private val mockIncomeType = IncomeTypeMock(
    name = "Salary Job",
    icon = Icons.Rounded.Category,
    color = Color(0xFF10B981) // Green accent matching your system
)

// ==========================================================
// Light & Dark Mode Layout Rendering Canvas Panels
// ==========================================================

@Preview(name = "Add Income Form - Light Mode")
@Composable
private fun AddIncomeFormStepLightPreview() {
    PocketGoalsTheme(themeMode = ThemeMode.LIGHT) {
        Surface(color = MaterialTheme.colorScheme.background) {
            AddIncomeFormStep(
                incomeType = IncomeType.defaultTypes[2],
                onTypeClick = {},
                amount = "2500",
                onAmountClick = {},
                date = "July 12, 2026",
                onDateClick = {},
                method = "Bank Transfer",
                onMethodClick = {},
                note = "Monthly payment bonus received",
                onNoteClick = {},
                isNextEnabled = true,
                onNext = {}
            )
        }
    }
}

@Preview(name = "Add Income Form - Dark Mode")
@Composable
private fun AddIncomeFormStepDarkPreview() {
    PocketGoalsTheme(themeMode = ThemeMode.DARK) {
        Surface(color = MaterialTheme.colorScheme.background) {
            AddIncomeFormStep(
                incomeType = IncomeType.defaultTypes[1],
                onTypeClick = {},
                amount = "0", // Showcases your custom empty placeholder state color mechanics
                onAmountClick = {},
                date = "July 12, 2026",
                onDateClick = {},
                method = null, // Tests your nullable unselected payment placeholder rules
                onMethodClick = {},
                note = "", // Displays unpopulated notes description
                onNoteClick = {},
                isNextEnabled = false, // Disables button to check layout color opacity settings
                onNext = {}
            )
        }
    }
}
