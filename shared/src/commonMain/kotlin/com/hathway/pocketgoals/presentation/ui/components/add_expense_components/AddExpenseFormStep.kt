package com.hathway.pocketgoals.presentation.ui.components.add_expense_components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowRight
import androidx.compose.material.icons.rounded.CalendarToday
import androidx.compose.material.icons.rounded.Category
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material.icons.rounded.Payments
import androidx.compose.material.icons.rounded.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.hathway.pocketgoals.domain.ExpenseCategory

@Composable
fun AddExpenseFormStep(
    amount: String,
    onAmountChange: (String) -> Unit,
    category: ExpenseCategory?,
    onCategoryClick: (ExpenseCategory) -> Unit,
    date: String,
    method: String?,
    onMethodClick: (String) -> Unit,
    note: String,
    onNoteChange: (String) -> Unit,
    isNextEnabled: Boolean,
    onNext: () -> Unit
) {
    var showNumPad by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Category Field
            FormField(label = "Category") {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .border(1.dp, Color(0xFFE2E8F0), RoundedCornerShape(12.dp))
                        .clickable {
                            onCategoryClick(ExpenseCategory("Groceries", Color(0xFF10B981), Icons.Rounded.ShoppingCart))
                        }
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (category != null) {
                        Icon(category.icon, null, tint = category.color, modifier = Modifier.size(24.dp))
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(category.name, fontWeight = FontWeight.Medium)
                    } else {
                        Icon(Icons.Rounded.Category, null, tint = Color(0xFF94A3B8))
                        Spacer(modifier = Modifier.width(12.dp))
                        Text("Select Category", color = Color(0xFF94A3B8))
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Icon(Icons.AutoMirrored.Rounded.KeyboardArrowRight, null, tint = Color(0xFF94A3B8))
                }
            }

            // Amount Field
            FormField(label = "Amount") {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .border(1.dp, if (showNumPad) Color(0xFF0F766E) else Color(0xFFE2E8F0), RoundedCornerShape(12.dp))
                        .clickable { showNumPad = true }
                        .padding(horizontal = 16.dp),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Text(
                        text = "₹ $amount",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold,
                        color = if (amount == "0") Color(0xFF94A3B8) else Color.Black
                    )
                }
            }

            // Date Field
            FormField(label = "Date") {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .border(1.dp, Color(0xFFE2E8F0), RoundedCornerShape(12.dp))
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(date, fontWeight = FontWeight.Medium)
                    Spacer(modifier = Modifier.weight(1f))
                    Icon(Icons.Rounded.CalendarToday, null, tint = Color(0xFF94A3B8), modifier = Modifier.size(20.dp))
                }
            }

            // Payment Method
            FormField(label = "Payment Method") {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .border(1.dp, Color(0xFFE2E8F0), RoundedCornerShape(12.dp))
                        .clickable { onMethodClick("Cash") }
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (method != null) {
                        Icon(Icons.Rounded.Payments, null, tint = Color(0xFF0F766E))
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(method, fontWeight = FontWeight.Medium)
                    } else {
                        Text("Select Method", color = Color(0xFF94A3B8))
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Icon(Icons.Rounded.KeyboardArrowDown, null, tint = Color(0xFF94A3B8))
                }
            }

            // Note
            FormField(label = "Note (Optional)") {
                OutlinedTextField(
                    value = note,
                    onValueChange = onNoteChange,
                    placeholder = { Text("Add a note", color = Color(0xFF94A3B8)) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedBorderColor = Color(0xFFE2E8F0),
                        focusedBorderColor = Color(0xFF0F766E)
                    )
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = onNext,
                enabled = isNextEnabled,
                modifier = Modifier.fillMaxWidth().height(56.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF0F766E),
                    disabledContainerColor = Color(0xFFCCFBF1).copy(alpha = 0.5f)
                )
            ) {
                Text("Next", fontWeight = FontWeight.Bold)
            }
        }

        // Custom Number Pad Overlay
        if (showNumPad) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.1f))
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) { showNumPad = false }
            )
            CustomNumberPad(
                modifier = Modifier.align(Alignment.BottomCenter),
                onNumberClick = { num ->
                    if (amount == "0") onAmountChange(num)
                    else onAmountChange(amount + num)
                },
                onDelete = {
                    if (amount.length > 1) onAmountChange(amount.dropLast(1))
                    else onAmountChange("0")
                },
                onDone = { showNumPad = false }
            )
        }
    }
}