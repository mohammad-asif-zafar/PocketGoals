package com.hathway.pocketgoals.presentation.ui.components.add_income_components

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
import androidx.compose.ui.unit.dp
import com.hathway.pocketgoals.domain.IncomeType
import com.hathway.pocketgoals.presentation.ui.components.add_expense_components.FormField

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
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Income Type Field
            FormField(label = "Income Type") {
                FormItemRow(
                    onClick = onTypeClick,
                    icon = incomeType.icon,
                    iconColor = incomeType.color,
                    text = incomeType.name,
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