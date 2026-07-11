package com.hathway.pocketgoals.presentation.ui.components.add_expense_components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun NoteInputStep(
    note: String,
    onNoteChange: (String) -> Unit,
    onDone: () -> Unit
) {
    Column(modifier = Modifier.fillMaxSize().padding(24.dp)) {
        Text(
            "Add Note",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        OutlinedTextField(
            value = note,
            onValueChange = onNoteChange,
            placeholder = { Text("Enter details about this expense...") },
            modifier = Modifier.fillMaxWidth().weight(1f),
            shape = MaterialTheme.shapes.medium,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
            )
        )

        Button(
            onClick = onDone,
            modifier = Modifier.fillMaxWidth().height(56.dp).padding(top = 16.dp),
            shape = MaterialTheme.shapes.medium
        ) {
            Text("Done", fontWeight = FontWeight.Bold)
        }
    }
}