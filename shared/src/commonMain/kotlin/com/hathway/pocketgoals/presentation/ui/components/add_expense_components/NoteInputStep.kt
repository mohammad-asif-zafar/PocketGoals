package com.hathway.pocketgoals.presentation.ui.components.add_expense_components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hathway.pocketgoals.domain.model.ThemeMode
import com.hathway.pocketgoals.presentation.ui.theme.PocketGoalsTheme
import org.jetbrains.compose.resources.stringResource
import pocketgoals.shared.generated.resources.Res
import pocketgoals.shared.generated.resources.add_note_title
import pocketgoals.shared.generated.resources.done_action
import pocketgoals.shared.generated.resources.note_input_placeholder

@Composable
fun NoteInputStep(
    note: String, onNoteChange: (String) -> Unit, onDone: () -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.weight(1f).padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.Top
        ) {
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = stringResource(Res.string.add_note_title),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp),
                color = MaterialTheme.colorScheme.onBackground
            )

            OutlinedTextField(
                value = note,
                onValueChange = onNoteChange,
                placeholder = { Text(text = stringResource(Res.string.note_input_placeholder)) },
                modifier = Modifier.fillMaxWidth().height(200.dp),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = MaterialTheme.colorScheme.onSurface,
                    unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
                )
            )
            Spacer(modifier = Modifier.height(24.dp))
        }

        Box(modifier = Modifier.padding(24.dp)) {
            Button(
                onClick = onDone,
                modifier = Modifier.fillMaxWidth().height(56.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(text = stringResource(Res.string.done_action), fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Preview(name = "Note Step Light Mode", showBackground = true, widthDp = 360, heightDp = 640)
@Composable
fun NoteInputStepLightPreview() {
    PocketGoalsTheme(themeMode = ThemeMode.LIGHT) {
        NoteInputStepPreviewContent()
    }
}

@Preview(name = "Note Step Dark Mode", showBackground = true, widthDp = 360, heightDp = 640)
@Composable
fun NoteInputStepDarkPreview() {
    PocketGoalsTheme(themeMode = ThemeMode.DARK) {
        NoteInputStepPreviewContent()
    }
}

@Composable
private fun NoteInputStepPreviewContent() {
    var noteText by remember { mutableStateOf("Taxi fare for office commute.") }
    NoteInputStep(note = noteText, onNoteChange = { noteText = it }, onDone = {})
}