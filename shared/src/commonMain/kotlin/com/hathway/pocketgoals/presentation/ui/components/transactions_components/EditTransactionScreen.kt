package com.hathway.pocketgoals.presentation.ui.components.transactions_components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.hathway.pocketgoals.domain.model.Transaction
import com.hathway.pocketgoals.domain.model.TransactionType
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import com.hathway.pocketgoals.domain.model.ThemeMode
import com.hathway.pocketgoals.presentation.ui.theme.PocketGoalsTheme
import org.jetbrains.compose.resources.stringResource
import pocketgoals.shared.generated.resources.Res
import pocketgoals.shared.generated.resources.amount
import pocketgoals.shared.generated.resources.category
import pocketgoals.shared.generated.resources.edit_transaction
import pocketgoals.shared.generated.resources.expense
import pocketgoals.shared.generated.resources.income
import pocketgoals.shared.generated.resources.save
import pocketgoals.shared.generated.resources.save_changes
import pocketgoals.shared.generated.resources.type
import pocketgoals.shared.generated.resources.summary_lbl_note

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditTransactionScreen(
    transaction: Transaction,
    onBack: () -> Unit,
    onSave: (Transaction) -> Unit
) {
    var amount by remember { mutableStateOf(transaction.amount.toString()) }
    var title by remember { mutableStateOf(transaction.title) }
    var note by remember { mutableStateOf(transaction.note) }
    var selectedType by remember { mutableStateOf(transaction.type) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        stringResource(Res.string.edit_transaction), fontWeight = FontWeight.Bold
                    )
                },

                navigationIcon = {
                    IconButton(onClick = onBack) { Icon(Icons.Rounded.Close, null) }
                }, actions = {
                    TextButton(onClick = {
                        onSave(
                            transaction.copy(
                                amount = amount.toDoubleOrNull() ?: transaction.amount,
                                title = title,
                                note = note,
                                type = selectedType
                            )
                        )
                    }) {
                        Text(
                            text = stringResource(Res.string.save),
                            color = Color(0xFF0F766E),
                            fontWeight = FontWeight.Bold
                        )
                    }
                })
        }) { paddingValues ->
        Column(modifier = Modifier.fillMaxSize().padding(paddingValues).padding(16.dp)) {
            Text(
                text = stringResource(Res.string.type),
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold
            )
            Row(
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                FilterChip(
                    selected = selectedType == TransactionType.INCOME,
                    onClick = { selectedType = TransactionType.INCOME },
                    label = { Text(stringResource(Res.string.income)) },
                    modifier = Modifier.weight(1f)
                )
                FilterChip(
                    selected = selectedType == TransactionType.EXPENSE,
                    onClick = { selectedType = TransactionType.EXPENSE },
                    label = { Text(stringResource(Res.string.expense)) },
                    modifier = Modifier.weight(1f)
                )
            }

            OutlinedTextField(
                value = amount,
                onValueChange = { amount = it },
                label = { Text(stringResource(Res.string.amount)) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text(stringResource(Res.string.category)) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                trailingIcon = { Icon(Icons.Rounded.KeyboardArrowDown, null) })

            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = note,
                onValueChange = { note = it },
                label = { Text(stringResource(Res.string.summary_lbl_note)) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                minLines = 3
            )

            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = {
                    onSave(
                        transaction.copy(
                            amount = amount.toDoubleOrNull() ?: transaction.amount,
                            title = title,
                            note = note,
                            type = selectedType
                        )
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0F766E))
            ) {
                Text(stringResource(Res.string.save_changes))
            }
        }
    }
}

val previewData = Transaction(amount = 250.50, title = "Shopping")
@Preview(name = "English", locale = "en", showSystemUi = true)
@Composable
private fun EditTransactionMultiLocaleLightPreview() {
    PocketGoalsTheme(themeMode = ThemeMode.LIGHT) {
        Surface {
            EditTransactionScreen(transaction = previewData, onBack = {}, onSave = {})
        }
    }

}

@Preview(name = "English", locale = "en", showSystemUi = true)
@Composable
private fun EditTransactionMultiLocaleDarkPreview() {

    PocketGoalsTheme(themeMode = ThemeMode.DARK) {
        Surface {
            EditTransactionScreen(transaction = previewData, onBack = {}, onSave = {})
        }
    }
}

