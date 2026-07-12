package com.hathway.pocketgoals.presentation.ui.components.add_income_components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.Notes
import androidx.compose.material.icons.rounded.CalendarToday
import androidx.compose.material.icons.rounded.CreditCard
import androidx.compose.material.icons.rounded.Payments
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hathway.pocketgoals.domain.IncomeType
import com.hathway.pocketgoals.domain.model.ThemeMode
import com.hathway.pocketgoals.presentation.ui.components.add_expense_components.SummaryItemRow
import com.hathway.pocketgoals.presentation.ui.theme.PocketGoalsTheme
import org.jetbrains.compose.resources.stringResource
import pocketgoals.shared.generated.resources.Res
import pocketgoals.shared.generated.resources.currency_symbol
import pocketgoals.shared.generated.resources.summary_btn_cancel
import pocketgoals.shared.generated.resources.summary_btn_save
import pocketgoals.shared.generated.resources.summary_lbl_amount
import pocketgoals.shared.generated.resources.summary_lbl_date
import pocketgoals.shared.generated.resources.summary_lbl_method
import pocketgoals.shared.generated.resources.summary_lbl_note
import pocketgoals.shared.generated.resources.summary_lbl_type

@Composable
fun AddIncomeSummaryStep(
    amount: String,
    incomeType: IncomeType,
    date: String,
    method: String,
    note: String,
    onSave: () -> Unit,
    onCancel: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()
    val dividerColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f)
    val currency = stringResource(Res.string.currency_symbol)

    Column(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.weight(1f).verticalScroll(scrollState)
                .padding(horizontal = 24.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Spacer(modifier = Modifier.height(8.dp))

            SummaryItemRow(
                icon = incomeType.icon,
                label = stringResource(Res.string.summary_lbl_type),
                value = incomeType.name
            )
            HorizontalDivider(color = dividerColor)

            SummaryItemRow(
                icon = Icons.Rounded.Payments,
                label = stringResource(Res.string.summary_lbl_amount),
                value = "$currency $amount"
            )
            HorizontalDivider(color = dividerColor)

            SummaryItemRow(
                icon = Icons.Rounded.CalendarToday,
                label = stringResource(Res.string.summary_lbl_date),
                value = date
            )
            HorizontalDivider(color = dividerColor)

            SummaryItemRow(
                icon = Icons.Rounded.CreditCard,
                label = stringResource(Res.string.summary_lbl_method),
                value = method
            )

            if (note.isNotEmpty()) {
                HorizontalDivider(color = dividerColor)
                SummaryItemRow(
                    icon = Icons.AutoMirrored.Rounded.Notes,
                    label = stringResource(Res.string.summary_lbl_note),
                    value = note
                )
            }
        }

        // Persistent bottom CTA interaction action block container layout
        Column(
            modifier = Modifier.padding(24.dp), verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Button(
                onClick = onSave,
                modifier = Modifier.fillMaxWidth().height(56.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                )
            ) {
                Text(
                    text = stringResource(Res.string.summary_btn_save), fontWeight = FontWeight.Bold
                )
            }

            OutlinedButton(
                onClick = onCancel,
                modifier = Modifier.fillMaxWidth().height(56.dp),
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary)
            ) {
                Text(
                    text = stringResource(Res.string.summary_btn_cancel),
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Preview(name = "Summary Step - Light Mode")
@Composable
private fun AddIncomeSummaryStepLightPreview() {
    PocketGoalsTheme(themeMode = ThemeMode.LIGHT) {
        Surface(color = MaterialTheme.colorScheme.background) {
            AddIncomeSummaryStep(
                amount = "65,000",
                incomeType = IncomeType.defaultTypes[0], // Passes the actual structural Salary object
                date = "July 12, 2026",
                method = "Direct Deposit",
                note = "Primary occupation base compensation check",
                onSave = {},
                onCancel = {})
        }
    }
}

@Preview(name = "Summary Step - Dark Mode")
@Composable
private fun AddIncomeSummaryStepDarkPreview() {
    PocketGoalsTheme(themeMode = ThemeMode.DARK) {
        Surface(color = MaterialTheme.colorScheme.background) {
            AddIncomeSummaryStep(
                amount = "4,200",
                incomeType = IncomeType.defaultTypes[3], // Passes the actual structural Investment object
                date = "July 12, 2026",
                method = "Brokerage Transfer",
                note = "", // Validates layout behavior when notes parameter string block is left unpopulated
                onSave = {},
                onCancel = {})
        }
    }
}