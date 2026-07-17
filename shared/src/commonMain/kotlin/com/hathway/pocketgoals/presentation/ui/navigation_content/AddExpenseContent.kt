package com.hathway.pocketgoals.presentation.ui.navigation_content

import androidx.compose.animation.*
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.hathway.pocketgoals.data.AddExpenseStep
import com.hathway.pocketgoals.data.CategoryFlowStep
import com.hathway.pocketgoals.domain.ExpenseCategory
import com.hathway.pocketgoals.domain.model.Transaction
import com.hathway.pocketgoals.domain.model.TransactionType
import com.hathway.pocketgoals.presentation.ui.components.add_expense_components.*
import com.hathway.pocketgoals.presentation.ui.theme.PocketGoalsTheme
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.compose.resources.stringResource
import pocketgoals.shared.generated.resources.*

@Composable
fun AddExpenseContent(
    onBackClick: () -> Unit,
    onViewTransaction: () -> Unit,
    onSaveTransaction: (Transaction) -> Unit
) {
    var currentStep by remember { mutableStateOf<AddExpenseStep>(AddExpenseStep.Form) }
    var categoryFlowStep by remember { mutableStateOf<CategoryFlowStep>(CategoryFlowStep.Selection) }

    // Form State
    var amount by remember { mutableStateOf("0") }
    var selectedCategory by remember { mutableStateOf<ExpenseCategory?>(null) }
    var selectedMethod by remember { mutableStateOf<String?>(null) }
    var note by remember { mutableStateOf("") }
    var selectedDateMillis by remember { mutableStateOf<Long?>(null) }

    val dateText = remember(selectedDateMillis) {
        if (selectedDateMillis == null) "11 July 2026"
        else {
            val instant = Instant.fromEpochMilliseconds(selectedDateMillis!!)
            val dt = instant.toLocalDateTime(TimeZone.currentSystemDefault())
            "${dt.dayOfMonth} ${dt.month.name.lowercase().replaceFirstChar { it.uppercase() }} ${dt.year}"
        }
    }

    val isFormValid = amount != "0" && selectedCategory != null && selectedMethod != null

    Scaffold(
        topBar = {
            if (currentStep !is AddExpenseStep.Success) {
                val title = when (currentStep) {
                    AddExpenseStep.Form -> stringResource(Res.string.add_expense)
                    AddExpenseStep.CategorySelection -> when (categoryFlowStep) {
                        CategoryFlowStep.Selection -> stringResource(Res.string.select_category)
                        CategoryFlowStep.Add -> stringResource(Res.string.add_new_category)
                        is CategoryFlowStep.Edit -> stringResource(Res.string.edit_category)
                        is CategoryFlowStep.Delete -> stringResource(Res.string.delete_category)
                    }
                    AddExpenseStep.AmountInput -> stringResource(Res.string.hint_enter_amount)
                    AddExpenseStep.DateSelection -> stringResource(Res.string.label_date)
                    AddExpenseStep.MethodSelection -> stringResource(Res.string.select_payment_method)
                    AddExpenseStep.NoteInput -> stringResource(Res.string.add_note)
                    AddExpenseStep.Summary -> stringResource(Res.string.summary)
                    else -> stringResource(Res.string.add_expense)
                }

                AddExpenseTopBar(
                    title = title,
                    onBack = {
                        when (currentStep) {
                            AddExpenseStep.Form -> onBackClick()
                            AddExpenseStep.CategorySelection -> {
                                when (categoryFlowStep) {
                                    CategoryFlowStep.Selection -> currentStep = AddExpenseStep.Form
                                    CategoryFlowStep.Add -> categoryFlowStep = CategoryFlowStep.Selection
                                    is CategoryFlowStep.Edit -> categoryFlowStep = CategoryFlowStep.Selection
                                    is CategoryFlowStep.Delete -> {
                                        val cat = (categoryFlowStep as CategoryFlowStep.Delete).category
                                        categoryFlowStep = CategoryFlowStep.Edit(cat)
                                    }
                                }
                            }
                            AddExpenseStep.AmountInput, 
                            AddExpenseStep.DateSelection, 
                            AddExpenseStep.MethodSelection, 
                            AddExpenseStep.NoteInput -> currentStep = AddExpenseStep.Form
                            AddExpenseStep.Summary -> currentStep = AddExpenseStep.Form
                            else -> onBackClick()
                        }
                    },
                    onDelete = if (currentStep == AddExpenseStep.CategorySelection && categoryFlowStep is CategoryFlowStep.Edit) {
                        { categoryFlowStep = CategoryFlowStep.Delete((categoryFlowStep as CategoryFlowStep.Edit).category) }
                    } else null
                )
            }
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { innerScaffoldPadding ->
        AnimatedContent(
            targetState = currentStep,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerScaffoldPadding),
            transitionSpec = {
                val isForward = targetState != AddExpenseStep.Form
                val enterTransition = if (isForward) {
                    slideInHorizontally { it } + fadeIn()
                } else {
                    slideInHorizontally { -it } + fadeIn()
                }
                val exitTransition = if (isForward) {
                    slideOutHorizontally { -it } + fadeOut()
                } else {
                    slideOutHorizontally { it } + fadeOut()
                }
                enterTransition togetherWith exitTransition using SizeTransform(clip = false)
            },
            label = "AddExpenseFlow"
        ) { step ->
            when (step) {
                is AddExpenseStep.Form -> {
                    AddExpenseFormStep(
                        amount = amount,
                        onAmountClick = { currentStep = AddExpenseStep.AmountInput },
                        category = selectedCategory,
                        onCategoryClick = { currentStep = AddExpenseStep.CategorySelection },
                        date = dateText,
                        onDateClick = { currentStep = AddExpenseStep.DateSelection },
                        method = selectedMethod,
                        onMethodClick = { currentStep = AddExpenseStep.MethodSelection },
                        note = note,
                        onNoteClick = { currentStep = AddExpenseStep.NoteInput },
                        isNextEnabled = isFormValid,
                        onNext = { currentStep = AddExpenseStep.Summary }
                    )
                }

                is AddExpenseStep.AmountInput -> {
                    AmountInputStep(
                        amount = amount,
                        onAmountChange = { amount = it },
                        onDone = { currentStep = AddExpenseStep.Form }
                    )
                }

                is AddExpenseStep.CategorySelection -> {
                    CategorySelectionContent(
                        selectedCategory = selectedCategory,
                        onCategorySelected = { selectedCategory = it },
                        onCategoryFinalized = {
                            selectedCategory = it
                            currentStep = AddExpenseStep.Form
                        },
                        currentStep = categoryFlowStep,
                        onStepChange = { categoryFlowStep = it }
                    )
                }

                is AddExpenseStep.DateSelection -> {
                    DateSelectionStep(
                        selectedDateMillis = selectedDateMillis,
                        onDateSelected = { selectedDateMillis = it },
                        onDone = { currentStep = AddExpenseStep.Form }
                    )
                }

                is AddExpenseStep.MethodSelection -> {
                    MethodSelectionStep(
                        selectedMethod = selectedMethod,
                        onMethodSelected = { selectedMethod = it },
                        onDone = { currentStep = AddExpenseStep.Form }
                    )
                }

                is AddExpenseStep.NoteInput -> {
                    NoteInputStep(
                        note = note,
                        onNoteChange = { note = it },
                        onDone = { currentStep = AddExpenseStep.Form }
                    )
                }

                is AddExpenseStep.Summary -> {
                    AddExpenseSummaryStep(
                        amount = amount,
                        category = selectedCategory!!,
                        date = dateText,
                        method = selectedMethod!!,
                        note = note,
                        onSave = { 
                            val transaction = Transaction(
                                id = Clock.System.now().toEpochMilliseconds().toString(),
                                title = selectedCategory!!.name,
                                amount = amount.toDoubleOrNull() ?: 0.0,
                                type = TransactionType.EXPENSE,
                                date = dateText,
                                time = "12:00 PM",
                                category = selectedCategory!!.name,
                                paymentMethod = selectedMethod!!,
                                note = note,
                                icon = selectedCategory!!.icon
                            )
                            onSaveTransaction(transaction)
                            currentStep = AddExpenseStep.Success 
                        },
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
                            selectedDateMillis = null
                            currentStep = AddExpenseStep.Form
                        }
                    )
                }
            }
        }
    }
}
