package com.hathway.pocketgoals.presentation.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.automirrored.rounded.Backspace
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowRight
import androidx.compose.material.icons.automirrored.rounded.Notes
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hathway.pocketgoals.domain.ExpenseCategory

sealed class AddExpenseStep {
    data object Form : AddExpenseStep()
    data object Summary : AddExpenseStep()
    data object Success : AddExpenseStep()
}

@Composable
fun AddExpenseScreen(
    onBackClick: () -> Unit,
    onViewTransaction: () -> Unit
) {
    var currentStep by remember { mutableStateOf<AddExpenseStep>(AddExpenseStep.Form) }
    
    // Form State
    var amount by remember { mutableStateOf("0") }
    var selectedCategory by remember { mutableStateOf<ExpenseCategory?>(null) }
    var selectedMethod by remember { mutableStateOf<String?>(null) }
    var note by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("15 May 2024") }

    val isFormValid = amount != "0" && selectedCategory != null && selectedMethod != null

    Scaffold(
        topBar = {
            if (currentStep !is AddExpenseStep.Success) {
                AddExpenseTopBar(
                    title = if (currentStep is AddExpenseStep.Form) "Add Expense" else "Summary",
                    onBack = {
                        if (currentStep is AddExpenseStep.Summary) currentStep = AddExpenseStep.Form
                        else onBackClick()
                    }
                )
            }
        },
        containerColor = Color.White
    ) { paddingValues ->
        AnimatedContent(
            targetState = currentStep,
            modifier = Modifier.padding(paddingValues),
            transitionSpec = {
                fadeIn() togetherWith fadeOut()
            },
            label = "AddExpenseFlow"
        ) { step ->
            when (step) {
                is AddExpenseStep.Form -> {
                    AddExpenseFormStep(
                        amount = amount,
                        onAmountChange = { amount = it },
                        category = selectedCategory,
                        onCategoryClick = { selectedCategory = it },
                        date = date,
                        method = selectedMethod,
                        onMethodClick = { selectedMethod = it },
                        note = note,
                        onNoteChange = { note = it },
                        isNextEnabled = isFormValid,
                        onNext = { currentStep = AddExpenseStep.Summary }
                    )
                }
                is AddExpenseStep.Summary -> {
                    AddExpenseSummaryStep(
                        amount = amount,
                        category = selectedCategory!!,
                        date = date,
                        method = selectedMethod!!,
                        note = note,
                        onSave = { currentStep = AddExpenseStep.Success },
                        onCancel = { currentStep = AddExpenseStep.Form }
                    )
                }
                is AddExpenseStep.Success -> {
                    AddExpenseSuccessStep(
                        amount = amount,
                        categoryName = selectedCategory?.name ?: "",
                        onViewExpense = onViewTransaction,
                        onAddAnother = {
                            amount = "0"
                            selectedCategory = null
                            selectedMethod = null
                            note = ""
                            currentStep = AddExpenseStep.Form
                        }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddExpenseTopBar(title: String, onBack: () -> Unit) {
    TopAppBar(
        title = { Text(title, fontWeight = FontWeight.Bold) },
        navigationIcon = {
            IconButton(onClick = onBack) {
                Icon(Icons.AutoMirrored.Rounded.ArrowBack, contentDescription = "Back")
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
    )
}

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

@Composable
fun FormField(label: String, content: @Composable () -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(label, style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold, color = Color(0xFF64748B))
        content()
    }
}

@Composable
fun CustomNumberPad(
    modifier: Modifier = Modifier,
    onNumberClick: (String) -> Unit,
    onDelete: () -> Unit,
    onDone: () -> Unit
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
        shadowElevation = 16.dp,
        color = Color.White
    ) {
        Column(modifier = Modifier.padding(24.dp)) {
            val numbers = listOf(
                listOf("1", "2", "3"),
                listOf("4", "5", "6"),
                listOf("7", "8", "9"),
                listOf(".", "0", "DEL")
            )
            
            numbers.forEach { row ->
                Row(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    row.forEach { char ->
                        Box(
                            modifier = Modifier
                                .size(64.dp)
                                .clip(RoundedCornerShape(16.dp))
                                .clickable {
                                    when (char) {
                                        "DEL" -> onDelete()
                                        else -> onNumberClick(char)
                                    }
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            if (char == "DEL") {
                                Icon(Icons.AutoMirrored.Rounded.Backspace, null)
                            } else {
                                Text(char, fontSize = 24.sp, fontWeight = FontWeight.SemiBold)
                            }
                        }
                    }
                }
            }
            
            Button(
                onClick = onDone,
                modifier = Modifier.fillMaxWidth().height(56.dp).padding(top = 8.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0F766E))
            ) {
                Text("Done", fontWeight = FontWeight.Bold)
            }
        }
    }
}

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
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        SummaryItem(Icons.Rounded.Category, "Category", category.name)
        SummaryItem(Icons.Rounded.Payments, "Amount", "₹ $amount")
        SummaryItem(Icons.Rounded.CalendarToday, "Date", date)
        SummaryItem(Icons.Rounded.CreditCard, "Payment Method", method)
        if (note.isNotEmpty()) {
            SummaryItem(Icons.AutoMirrored.Rounded.Notes, "Note", note)
        }
        
        Spacer(modifier = Modifier.weight(1f))
        
        Button(
            onClick = onSave,
            modifier = Modifier.fillMaxWidth().height(56.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0F766E))
        ) {
            Text("Save Expense", fontWeight = FontWeight.Bold)
        }
        
        OutlinedButton(
            onClick = onCancel,
            modifier = Modifier.fillMaxWidth().height(56.dp),
            shape = RoundedCornerShape(12.dp),
            border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF0F766E))
        ) {
            Text("Cancel", color = Color(0xFF0F766E), fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun SummaryItem(icon: ImageVector, label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier.size(48.dp).background(Color(0xFFF1F5F9), RoundedCornerShape(12.dp)),
            contentAlignment = Alignment.Center
        ) {
            Icon(icon, null, tint = Color(0xFF0F766E))
        }
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(label, style = MaterialTheme.typography.labelSmall, color = Color(0xFF64748B))
            Text(value, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun AddExpenseSuccessStep(
    amount: String,
    categoryName: String,
    onViewExpense: () -> Unit,
    onAddAnother: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier.size(100.dp).background(Color(0xFFECFDF5), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(Icons.Rounded.Check, null, tint = Color(0xFF10B981), modifier = Modifier.size(48.dp))
        }
        Spacer(modifier = Modifier.height(24.dp))
        Text("Expense Added!", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = "₹$amount has been added to\n$categoryName successfully.",
            textAlign = TextAlign.Center,
            color = Color(0xFF64748B)
        )
        
        Spacer(modifier = Modifier.height(48.dp))
        
        Button(
            onClick = onViewExpense,
            modifier = Modifier.fillMaxWidth().height(56.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0F766E))
        ) {
            Text("View Expense", fontWeight = FontWeight.Bold)
        }
        
        Spacer(modifier = Modifier.height(12.dp))
        
        TextButton(onClick = onAddAnother) {
            Text("Add Another Expense", color = Color(0xFF0F766E), fontWeight = FontWeight.Bold)
        }
    }
}
