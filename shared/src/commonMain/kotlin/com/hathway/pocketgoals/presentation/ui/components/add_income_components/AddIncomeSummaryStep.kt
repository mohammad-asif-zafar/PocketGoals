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
import androidx.compose.ui.unit.dp
import com.hathway.pocketgoals.domain.IncomeType
import com.hathway.pocketgoals.presentation.ui.components.add_expense_components.SummaryItemRow

@Composable
fun AddIncomeSummaryStep(
    amount: String,
    incomeType: IncomeType,
    date: String,
    method: String,
    note: String,
    onSave: () -> Unit,
    onCancel: () -> Unit
) {
    val scrollState = rememberScrollState()

    Column(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.weight(1f).verticalScroll(scrollState)
                .padding(horizontal = 24.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            SummaryItemRow(incomeType.icon, "Income Type", incomeType.name, incomeType.color)
            HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))

            SummaryItemRow(Icons.Rounded.Payments, "Amount", "₹ $amount")
            HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))

            SummaryItemRow(Icons.Rounded.CalendarToday, "Date", date)
            HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))

            SummaryItemRow(Icons.Rounded.CreditCard, "Payment Method", method)

            if (note.isNotEmpty()) {
                HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))
                SummaryItemRow(Icons.AutoMirrored.Rounded.Notes, "Note", note)
            }
        }

        Column(
            modifier = Modifier.padding(24.dp), 
            verticalArrangement = Arrangement.spacedBy(12.dp)
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
                Text("Save Income", fontWeight = FontWeight.Bold)
            }

            OutlinedButton(
                onClick = onCancel,
                modifier = Modifier.fillMaxWidth().height(56.dp),
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary)
            ) {
                Text("Cancel", color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
            }
        }
    }
}