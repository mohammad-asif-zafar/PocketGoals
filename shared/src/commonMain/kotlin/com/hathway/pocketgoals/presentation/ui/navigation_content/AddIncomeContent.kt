package com.hathway.pocketgoals.presentation.ui.navigation_content

import androidx.compose.animation.*
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.hathway.pocketgoals.data.AddIncomeStep
import com.hathway.pocketgoals.domain.IncomeType
import com.hathway.pocketgoals.domain.model.Transaction
import com.hathway.pocketgoals.domain.model.TransactionType
import com.hathway.pocketgoals.presentation.ui.components.add_expense_components.*
import com.hathway.pocketgoals.presentation.ui.components.add_income_components.*
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.compose.resources.stringResource
import pocketgoals.shared.generated.resources.*
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
@Composable
fun AddIncomeContent(
    onBackClick: () -> Unit,
    onViewIncome: () -> Unit,
    onSaveTransaction: (Transaction) -> Unit
) {
    var currentStep by remember { mutableStateOf<AddIncomeStep>(AddIncomeStep.TypeSelection) }

    // State
    var selectedType by remember { mutableStateOf<IncomeType?>(null) }
    var amount by remember { mutableStateOf("0") }
    var selectedDateMillis by remember { mutableStateOf<Long?>(null) }
    var selectedMethod by remember { mutableStateOf<String?>(null) }
    var note by remember { mutableStateOf("") }

    val dateText = remember(selectedDateMillis) {
        if (selectedDateMillis == null) {
            // Fallback to the current system date
            val currentInstant = Clock.System.now()
            val dt = currentInstant.toLocalDateTime(TimeZone.currentSystemDefault())
            "${dt.dayOfMonth} ${dt.month.name.lowercase().replaceFirstChar { it.uppercase() }} ${dt.year}"
        } else {
            val instant = Instant.fromEpochMilliseconds(selectedDateMillis!!)
            val dt = instant.toLocalDateTime(TimeZone.currentSystemDefault())
            "${dt.dayOfMonth} ${dt.month.name.lowercase().replaceFirstChar { it.uppercase() }} ${dt.year}"
        }
    }

    val isFormValid = amount != "0" && selectedType != null && selectedMethod != null

    Scaffold(
        topBar = {
            if (currentStep !is AddIncomeStep.Success) {
                val title = when (currentStep) {
                    AddIncomeStep.TypeSelection -> stringResource(Res.string.add_income)
                    AddIncomeStep.Form -> stringResource(Res.string.income_details)
                    AddIncomeStep.AmountInput -> stringResource(Res.string.hint_enter_amount)
                    AddIncomeStep.DateSelection -> stringResource(Res.string.label_date)
                    AddIncomeStep.MethodSelection -> stringResource(Res.string.select_payment_method)
                    AddIncomeStep.NoteInput -> stringResource(Res.string.add_note)
                    AddIncomeStep.Summary -> stringResource(Res.string.summary)
                    else -> stringResource(Res.string.add_income)
                }

                AddExpenseTopBar(
                    title = title,
                    onBack = {
                        when (currentStep) {
                            AddIncomeStep.TypeSelection -> onBackClick()
                            AddIncomeStep.Form -> currentStep = AddIncomeStep.TypeSelection
                            AddIncomeStep.AmountInput,
                            AddIncomeStep.DateSelection,
                            AddIncomeStep.MethodSelection,
                            AddIncomeStep.NoteInput -> currentStep = AddIncomeStep.Form
                            AddIncomeStep.Summary -> currentStep = AddIncomeStep.Form
                            else -> onBackClick()
                        }
                    }
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
                val isForward = targetState != AddIncomeStep.Form && targetState != AddIncomeStep.TypeSelection
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
            label = "AddIncomeFlow"
        ) { step ->
            when (step) {
                is AddIncomeStep.TypeSelection -> {
                    IncomeTypeSelectionStep(
                        selectedType = selectedType,
                        onTypeSelected = { 
                            selectedType = it
                            currentStep = AddIncomeStep.Form
                        },
                        onNext = { currentStep = AddIncomeStep.Form }
                    )
                }

                is AddIncomeStep.Form -> {
                    AddIncomeFormStep(
                        incomeType = selectedType!!,
                        onTypeClick = { currentStep = AddIncomeStep.TypeSelection },
                        amount = amount,
                        onAmountClick = { currentStep = AddIncomeStep.AmountInput },
                        date = dateText,
                        onDateClick = { currentStep = AddIncomeStep.DateSelection },
                        method = selectedMethod,
                        onMethodClick = { currentStep = AddIncomeStep.MethodSelection },
                        note = note,
                        onNoteClick = { currentStep = AddIncomeStep.NoteInput },
                        isNextEnabled = isFormValid,
                        onNext = { currentStep = AddIncomeStep.Summary }
                    )
                }

                is AddIncomeStep.AmountInput -> {
                    AmountInputStep(
                        amount = amount,
                        onAmountChange = { amount = it },
                        onDone = { currentStep = AddIncomeStep.Form }
                    )
                }

                is AddIncomeStep.DateSelection -> {
                    DateSelectionStep(
                        selectedDateMillis = selectedDateMillis,
                        onDateSelected = { selectedDateMillis = it },
                        onDone = { currentStep = AddIncomeStep.Form }
                    )
                }

                is AddIncomeStep.MethodSelection -> {
                    MethodSelectionStep(
                        selectedMethod = selectedMethod,
                        onMethodSelected = { selectedMethod = it },
                        onDone = { currentStep = AddIncomeStep.Form }
                    )
                }

                is AddIncomeStep.NoteInput -> {
                    NoteInputStep(
                        note = note,
                        onNoteChange = { note = it },
                        onDone = { currentStep = AddIncomeStep.Form }
                    )
                }

                is AddIncomeStep.Summary -> {
                    AddIncomeSummaryStep(
                        amount = amount,
                        incomeType = selectedType!!,
                        date = dateText,
                        method = selectedMethod!!,
                        note = note,
                        onSave = {
                            // 🔹 Extract and prepare all items safely up top to avoid compilation errors
                            val generatedId = Clock.System.now().toEpochMilliseconds().toString()
                            val transactionTitle = selectedType!!.name
                            val parsedAmount = amount.toDoubleOrNull() ?: 0.0
                            val transactionType = TransactionType.INCOME
                            val transactionCategory = selectedType!!.name
                            val transactionMethod = selectedMethod!!
                            val transactionIcon = selectedType!!.icon
                            val currentInstant = Clock.System.now()
                            val localTime = currentInstant.toLocalDateTime(TimeZone.currentSystemDefault())
                            val hour12 = if (localTime.hour % 12 == 0) 12 else localTime.hour % 12
                            val amPm = if (localTime.hour < 12) "AM" else "PM"
                            val timeText = "${hour12.toString().padStart(2, '0')}:${localTime.minute.toString().padStart(2, '0')} $amPm"

                            // 🔹 Log all properties cleanly to the device console (Android Logcat & Xcode logs)
                            println("--- Saving Transaction ---")
                            println("ID: $generatedId")
                            println("Title: $transactionTitle")
                            println("Amount: $parsedAmount (Raw String Input: \"$amount\")")
                            println("Type: $transactionType")
                            println("Date: $dateText")
                            println("Time: $timeText") // Updated to reflect the real system time string
                            println("Category: $transactionCategory")
                            println("Payment Method: $transactionMethod")
                            println("Note: $note")
                            println("Icon Code/Name: $transactionIcon")
                            println("--------------------------")

                            // 🔹 Pass the clean variables directly into the object builder
                            val transaction = Transaction(
                                id = generatedId,
                                title = transactionTitle,
                                amount = parsedAmount,
                                type = transactionType,
                                date = dateText, // Uses your formatted string function value
                                time = timeText,
                                category = transactionCategory,
                                paymentMethod = transactionMethod,
                                note = note,
                                icon = transactionIcon
                            )

                            onSaveTransaction(transaction)
                            currentStep = AddIncomeStep.Success
                        }
                        ,

                                onCancel = { currentStep = AddIncomeStep.Form }
                    )
                }

                is AddIncomeStep.Success -> {
                    AddIncomeSuccessStep(
                        amount = amount,
                        typeName = selectedType?.name ?: "",
                        onViewIncome = onViewIncome,
                        onAddAnother = {
                            amount = "0"
                            selectedType = null
                            selectedMethod = null
                            note = ""
                            selectedDateMillis = null
                            currentStep = AddIncomeStep.TypeSelection
                        }
                    )
                }
            }
        }
    }
}
