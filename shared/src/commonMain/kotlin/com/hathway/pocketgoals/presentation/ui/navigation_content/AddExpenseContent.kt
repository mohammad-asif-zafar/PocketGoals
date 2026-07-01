package com.hathway.pocketgoals.presentation.ui.navigation_content

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.hathway.pocketgoals.data.AddExpenseStep
import com.hathway.pocketgoals.domain.ExpenseCategory
import com.hathway.pocketgoals.presentation.ui.components.add_expense_components.AddExpenseFormStep
import com.hathway.pocketgoals.presentation.ui.components.add_expense_components.AddExpenseSuccessStep
import com.hathway.pocketgoals.presentation.ui.components.add_expense_components.AddExpenseSummaryStep
import com.hathway.pocketgoals.presentation.ui.components.add_expense_components.AddExpenseTopBar

@Composable
fun AddExpenseContent(
    onBackClick: () -> Unit, onViewTransaction: () -> Unit
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
                    })
            }
        }, containerColor = Color.White
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
                        onNext = { currentStep = AddExpenseStep.Summary })
                }

                is AddExpenseStep.Summary -> {
                    AddExpenseSummaryStep(
                        amount = amount,
                        category = selectedCategory!!,
                        date = date,
                        method = selectedMethod!!,
                        note = note,
                        onSave = { currentStep = AddExpenseStep.Success },
                        onCancel = { currentStep = AddExpenseStep.Form })
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
                        })
                }
            }
        }
    }
}
