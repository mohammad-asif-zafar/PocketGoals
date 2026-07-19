package com.hathway.pocketgoals.presentation.ui.components.add_expense_components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.Notes
import androidx.compose.material.icons.rounded.CalendarToday
import androidx.compose.material.icons.rounded.CreditCard
import androidx.compose.material.icons.rounded.Payments
import androidx.compose.material.icons.rounded.Restaurant
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hathway.pocketgoals.domain.ExpenseCategory
import com.hathway.pocketgoals.domain.model.ThemeMode
import com.hathway.pocketgoals.presentation.ui.theme.PocketGoalsTheme
import org.jetbrains.compose.resources.stringResource
import pocketgoals.shared.generated.resources.*

@Composable
fun AddExpenseSummaryStep(
    amount: String,
    category: ExpenseCategory,
    date: String,
    method: String,
    note: String,
    onSave: () -> Unit,
    onCancel: () -> Unit
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.weight(1f).verticalScroll(scrollState)
                .padding(horizontal = 24.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            SummaryItemRow(category.icon, stringResource(Res.string.category), category.name, category.color)
            HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))

            SummaryItemRow(Icons.Rounded.Payments, stringResource(Res.string.amount), " $amount")
            HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))

            SummaryItemRow(Icons.Rounded.CalendarToday, stringResource(Res.string.label_date), date)
            HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))

            SummaryItemRow(Icons.Rounded.CreditCard, stringResource(Res.string.label_payment_method), method)

            if (note.isNotEmpty()) {
                HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))
                SummaryItemRow(Icons.AutoMirrored.Rounded.Notes, stringResource(Res.string.label_note_optional), note)
            }
            Spacer(modifier = Modifier.height(24.dp))
        }

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
                Text(stringResource(Res.string.save_expense), fontWeight = FontWeight.Bold)
            }

            OutlinedButton(
                onClick = onCancel,
                modifier = Modifier.fillMaxWidth().height(56.dp),
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary)
            ) {
                Text(stringResource(Res.string.cancel), color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Preview(name = "Summary Step Light Mode", showBackground = true, widthDp = 360, heightDp = 640)
@Composable
fun AddExpenseSummaryStepLightPreview() {
    PocketGoalsTheme(themeMode = ThemeMode.LIGHT) {
        AddExpenseSummaryStepPreviewContent()
    }
}

@Preview(name = "Summary Step Dark Mode", showBackground = true, widthDp = 360, heightDp = 640)
@Composable
fun AddExpenseSummaryStepDarkPreview() {
    PocketGoalsTheme(themeMode = ThemeMode.DARK) {
        AddExpenseSummaryStepPreviewContent()
    }
}

@Composable
private fun AddExpenseSummaryStepPreviewContent() {
    val mockCategory = ExpenseCategory(
        name = "Food & Dining",
        color = Color(0xFFF59E0B),
        icon = Icons.Rounded.Restaurant
    )

    AddExpenseSummaryStep(
        amount = "1,250",
        category = mockCategory,
        date = "16 July 2026",
        method = "Credit Card",
        note = "Business dinner with clients",
        onSave = {},
        onCancel = {}
    )
}