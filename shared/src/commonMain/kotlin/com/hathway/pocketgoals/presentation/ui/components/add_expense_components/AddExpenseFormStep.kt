package com.hathway.pocketgoals.presentation.ui.components.add_expense_components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowRight
import androidx.compose.material.icons.automirrored.rounded.Notes
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hathway.pocketgoals.domain.ExpenseCategory
import com.hathway.pocketgoals.domain.model.ThemeMode
import com.hathway.pocketgoals.presentation.ui.theme.PocketGoalsTheme

@Composable
fun AddExpenseFormStep(
    amount: String,
    onAmountClick: () -> Unit,
    category: ExpenseCategory?,
    onCategoryClick: () -> Unit,
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

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(scrollState)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Category Field
            FormField(label = "Category") {
                FormItemRow(
                    onClick = onCategoryClick,
                    icon = category?.icon ?: Icons.Rounded.Category,
                    iconColor = category?.color ?: MaterialTheme.colorScheme.primary,
                    text = category?.name ?: "Select Category",
                    isPlaceholder = category == null,
                    borderColor = borderColor
                )
            }

            // Amount Field
            FormField(label = "Amount") {
                FormItemRow(
                    onClick = onAmountClick,
                    icon = Icons.Rounded.Payments,
                    iconColor = MaterialTheme.colorScheme.primary,
                    text = if (amount == "0") "Enter Amount" else "₹ $amount",
                    isPlaceholder = amount == "0",
                    borderColor = borderColor
                )
            }

            // Date Field
            FormField(label = "Date") {
                FormItemRow(
                    onClick = onDateClick,
                    icon = Icons.Rounded.CalendarToday,
                    iconColor = MaterialTheme.colorScheme.primary,
                    text = date,
                    borderColor = borderColor
                )
            }

            // Payment Method
            FormField(label = "Payment Method") {
                FormItemRow(
                    onClick = onMethodClick,
                    icon = Icons.Rounded.AccountBalanceWallet,
                    iconColor = MaterialTheme.colorScheme.primary,
                    text = method ?: "Select Method",
                    isPlaceholder = method == null,
                    borderColor = borderColor
                )
            }

            // Note
            FormField(label = "Note (Optional)") {
                FormItemRow(
                    onClick = onNoteClick,
                    icon = Icons.AutoMirrored.Rounded.Notes,
                    iconColor = MaterialTheme.colorScheme.primary,
                    text = if (note.isEmpty()) "Add a note" else note,
                    isPlaceholder = note.isEmpty(),
                    borderColor = borderColor
                )
            }
        }

        Box(modifier = Modifier.padding(16.dp)) {
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
                Text("Next", fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
private fun FormItemRow(
    onClick: () -> Unit,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    iconColor: Color,
    text: String,
    isPlaceholder: Boolean = false,
    borderColor: Color
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .border(1.dp, borderColor, RoundedCornerShape(12.dp))
            .clickable(onClick = onClick)
            .padding(horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .background(iconColor.copy(alpha = 0.1f), RoundedCornerShape(8.dp)),
            contentAlignment = Alignment.Center
        ) {
            Icon(icon, null, tint = iconColor, modifier = Modifier.size(20.dp))
        }
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = text,
            color = if (isPlaceholder) MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f) else MaterialTheme.colorScheme.onSurface,
            fontWeight = FontWeight.Medium,
            maxLines = 1
        )
        Spacer(modifier = Modifier.weight(1f))
        Icon(
            Icons.AutoMirrored.Rounded.KeyboardArrowRight,
            null,
            tint = MaterialTheme.colorScheme.outline
        )
    }
}

@Preview
@Composable
fun AddExpenseFormStepLightPreview() {
    PocketGoalsTheme(themeMode = ThemeMode.LIGHT) {
        AddExpenseFormStep(
            amount = "0",
            onAmountClick = {},
            category = null,
            onCategoryClick = {},
            date = "15 May 2024",
            onDateClick = {},
            method = null,
            onMethodClick = {},
            note = "",
            onNoteClick = {},
            isNextEnabled = true,
            onNext = {}
        )
    }
}

@Preview
@Composable
fun AddExpenseFormStepDarkPreview() {
    PocketGoalsTheme(themeMode = ThemeMode.DARK) {
        AddExpenseFormStep(
            amount = "2450",
            onAmountClick = {},
            category = ExpenseCategory(
                name = "Groceries", color = Color(0xFF10B981), icon = Icons.Rounded.ShoppingCart
            ),
            onCategoryClick = {},
            date = "15 May 2024",
            onDateClick = {},
            method = "Cash",
            onMethodClick = {},
            note = "Weekly groceries purchase",
            onNoteClick = {},
            isNextEnabled = true,
            onNext = {}
        )
    }
}