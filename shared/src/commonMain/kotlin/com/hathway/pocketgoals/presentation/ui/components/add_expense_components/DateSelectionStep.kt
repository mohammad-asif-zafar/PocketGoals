package com.hathway.pocketgoals.presentation.ui.components.add_expense_components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import kotlinx.datetime.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateSelectionStep(
    selectedDateMillis: Long?,
    onDateSelected: (Long?) -> Unit,
    onDone: () -> Unit
) {
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = selectedDateMillis ?: Clock.System.now().toEpochMilliseconds()
    )

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        DatePicker(
            state = datePickerState,
            modifier = Modifier.weight(1f),
            showModeToggle = false,
            colors = DatePickerDefaults.colors(
                selectedDayContainerColor = MaterialTheme.colorScheme.primary,
                selectedDayContentColor = MaterialTheme.colorScheme.onPrimary,
                todayContentColor = MaterialTheme.colorScheme.primary,
                todayDateBorderColor = MaterialTheme.colorScheme.primary
            )
        )

        Box(modifier = Modifier.padding(24.dp)) {
            Button(
                onClick = {
                    onDateSelected(datePickerState.selectedDateMillis)
                    onDone()
                },
                modifier = Modifier.fillMaxWidth().height(56.dp),
                shape = MaterialTheme.shapes.medium,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                )
            ) {
                Text("Confirm Date", fontWeight = FontWeight.Bold)
            }
        }
    }
}