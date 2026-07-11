package com.hathway.pocketgoals.presentation.ui.components.add_expense_components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.Notes
import androidx.compose.material.icons.rounded.CalendarToday
import androidx.compose.material.icons.rounded.CreditCard
import androidx.compose.material.icons.rounded.Payments
import androidx.compose.material.icons.rounded.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
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
import com.hathway.pocketgoals.presentation.ui.theme.Primary

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
        // 1. Scrollable rows section
        Column(
            modifier = Modifier.weight(1f).verticalScroll(scrollState)
                .padding(horizontal = 24.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            SummaryItemRow(category.icon, "Category", category.name, category.color)
            HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f))

            SummaryItemRow(Icons.Rounded.Payments, "Amount", "₹ $amount")
            HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f))

            SummaryItemRow(Icons.Rounded.CalendarToday, "Date", date)
            HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f))

            SummaryItemRow(Icons.Rounded.CreditCard, "Payment Method", method)

            if (note.isNotEmpty()) {
                HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f))
                SummaryItemRow(Icons.AutoMirrored.Rounded.Notes, "Note", note)
            }
        }

        // 2. Fixed Button Panel at the bottom
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
                Text("Save Expense", fontWeight = FontWeight.Bold)
            }

            OutlinedButton(
                onClick = onCancel,
                modifier = Modifier.fillMaxWidth().height(56.dp),
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(1.dp, Primary)
            ) {
                Text("Cancel", color = Primary, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun SummaryItemRow(
    icon: ImageVector, label: String, value: String, customIconColor: Color? = null
) {
    // Dynamic color tint adjustments for both themes
    val baseIconColor = customIconColor ?: MaterialTheme.colorScheme.primary
    val iconContainerBg = baseIconColor.copy(alpha = 0.1f)

    Row(
        modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
    ) {
        // Rounded Square Icon Container on the Left
        Box(
            modifier = Modifier.size(48.dp).clip(RoundedCornerShape(12.dp))
                .background(iconContainerBg), contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = baseIconColor,
                modifier = Modifier.size(24.dp)
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        // Label and Value Stacked Text Info Fields
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
            )
            Text(
                text = value,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

// ==========================================================
// Theme-safe Split Previews
// ==========================================================

@Preview
@Composable
fun AddExpenseSummaryStepLightPreview() {
    PocketGoalsTheme(themeMode = ThemeMode.LIGHT) {
        AddExpenseSummaryStep(
            amount = "2,500",
            category = ExpenseCategory("Groceries", Color(0xFF10B981), Icons.Rounded.ShoppingCart),
            date = "15 May 2024",
            method = "Cash",
            note = "Weekly groceries from supermarket.",
            onSave = {},
            onCancel = {})
    }
}

@Preview
@Composable
fun AddExpenseSummaryStepDarkPreview() {
    PocketGoalsTheme(themeMode = ThemeMode.DARK) {
        AddExpenseSummaryStep(
            amount = "2,500",
            category = ExpenseCategory("Groceries", Color(0xFF2DD4BF), Icons.Rounded.ShoppingCart),
            date = "15 May 2024",
            method = "Cash",
            note = "Weekly groceries from supermarket.",
            onSave = {},
            onCancel = {})
    }
}

