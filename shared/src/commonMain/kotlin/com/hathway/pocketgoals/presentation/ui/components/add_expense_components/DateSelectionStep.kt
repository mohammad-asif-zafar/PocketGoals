package com.hathway.pocketgoals.presentation.ui.components.add_expense_components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hathway.pocketgoals.domain.model.ThemeMode
import com.hathway.pocketgoals.presentation.ui.theme.PocketGoalsTheme
import kotlinx.datetime.Clock
import org.jetbrains.compose.resources.stringResource
import pocketgoals.shared.generated.resources.Res
import pocketgoals.shared.generated.resources.confirm_date_action

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateSelectionStep(
    selectedDateMillis: Long?, onDateSelected: (Long?) -> Unit, onDone: () -> Unit
) {
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = selectedDateMillis ?: Clock.System.now().toEpochMilliseconds()
    )

    Column(
        modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        DatePicker(
            state = datePickerState,
            modifier = Modifier.weight(1f),
            showModeToggle = false,
            colors = DatePickerDefaults.colors(
                selectedDayContainerColor = MaterialTheme.colorScheme.primary,
                selectedDayContentColor = MaterialTheme.colorScheme.onPrimary,
                todayContentColor = MaterialTheme.colorScheme.primary,
                todayDateBorderColor = MaterialTheme.colorScheme.primary,
                containerColor = MaterialTheme.colorScheme.background,
                titleContentColor = MaterialTheme.colorScheme.onBackground,
                headlineContentColor = MaterialTheme.colorScheme.onBackground
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
                Text(
                    text = stringResource(Res.string.confirm_date_action),
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Preview(name = "DatePicker Light Mode", showBackground = true, widthDp = 360, heightDp = 640)
@Composable
fun DateSelectionStepLightPreview() {
    PocketGoalsTheme(themeMode = ThemeMode.LIGHT) {
        DateSelectionStep(selectedDateMillis = null, onDateSelected = {}, onDone = {})
    }
}

@Preview(name = "DatePicker Dark Mode", showBackground = true, widthDp = 360, heightDp = 640)
@Composable
fun DateSelectionStepDarkPreview() {
    PocketGoalsTheme(themeMode = ThemeMode.DARK) {
        DateSelectionStep(selectedDateMillis = null, onDateSelected = {}, onDone = {})
    }
}
