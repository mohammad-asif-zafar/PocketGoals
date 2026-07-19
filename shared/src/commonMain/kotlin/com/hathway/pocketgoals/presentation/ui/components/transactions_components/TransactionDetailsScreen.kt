package com.hathway.pocketgoals.presentation.ui.components.transactions_components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Share
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hathway.pocketgoals.domain.model.ThemeMode
import com.hathway.pocketgoals.domain.model.Transaction
import com.hathway.pocketgoals.domain.model.TransactionType
import com.hathway.pocketgoals.presentation.ui.theme.PocketGoalsTheme
import com.hathway.pocketgoals.presentation.ui.theme.Success
import org.jetbrains.compose.resources.stringResource
import pocketgoals.shared.generated.resources.Res
import pocketgoals.shared.generated.resources.cancel
import pocketgoals.shared.generated.resources.delete_category
import pocketgoals.shared.generated.resources.delete_category_confirm
import pocketgoals.shared.generated.resources.edit_transaction
import pocketgoals.shared.generated.resources.tx_delete
import pocketgoals.shared.generated.resources.tx_details_title
import pocketgoals.shared.generated.resources.tx_lbl_category
import pocketgoals.shared.generated.resources.tx_lbl_date
import pocketgoals.shared.generated.resources.tx_lbl_method
import pocketgoals.shared.generated.resources.tx_lbl_notes
import pocketgoals.shared.generated.resources.tx_lbl_time
import pocketgoals.shared.generated.resources.tx_no_notes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionDetailsScreen(
    transaction: Transaction, onBack: () -> Unit, onEdit: () -> Unit, onDelete: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(
                    stringResource(Res.string.tx_details_title), fontWeight = FontWeight.Bold
                )
            }, navigationIcon = {
                IconButton(onClick = onBack) {
                    Icon(
                        Icons.AutoMirrored.Rounded.ArrowBack, null
                    )
                }
            }, actions = {
                IconButton(onClick = {}) { Icon(Icons.Rounded.Share, null) }
            })
        }) { paddingValues ->
        Box(
            modifier = Modifier.fillMaxSize().padding(paddingValues)
        ) {
            Column(
                modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp)
                    .padding(bottom = 88.dp).verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(16.dp))


                val statusColor = if (transaction.type == TransactionType.INCOME) {
                    Success
                } else {
                    MaterialTheme.colorScheme.error
                }

                Box(
                    modifier = Modifier.size(80.dp).background(statusColor.copy(0.1f), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        transaction.icon, null, tint = statusColor, modifier = Modifier.size(40.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    transaction.title,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Text(
                    text = "${if (transaction.type == TransactionType.INCOME) "+" else "-"} ${transaction.amount.toInt()}",
                    style = MaterialTheme.typography.displaySmall,
                    fontWeight = FontWeight.Bold,
                    color = statusColor
                )

                Surface(
                    color = statusColor.copy(0.1f),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.padding(top = 8.dp)
                ) {
                    Text(
                        transaction.type.name,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                        style = MaterialTheme.typography.labelSmall,
                        color = statusColor
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))

                DetailRow(stringResource(Res.string.tx_lbl_date), transaction.date)
                DetailRow(stringResource(Res.string.tx_lbl_time), transaction.time)
                DetailRow(stringResource(Res.string.tx_lbl_method), transaction.paymentMethod)
                DetailRow(stringResource(Res.string.tx_lbl_category), transaction.category)

                // Notes layout block
                Column(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = stringResource(Res.string.tx_lbl_notes),
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = transaction.note.ifEmpty { stringResource(Res.string.tx_no_notes) },
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSurface,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth().align(Alignment.BottomCenter)
                    .background(MaterialTheme.colorScheme.background) // Prevents list contents from rendering behind buttons
                    .padding(16.dp), horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                var showDeleteDialog by remember { mutableStateOf(false) }

                if (showDeleteDialog) {
                    AlertDialog(
                        onDismissRequest = { showDeleteDialog = false },
                        title = { Text(stringResource(Res.string.tx_delete)) },
                        text = { Text(stringResource(Res.string.delete_category_confirm)) },
                        confirmButton = {
                            Button(
                                onClick = {
                                    showDeleteDialog = false
                                    onDelete()
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                            ) {
                                Text(stringResource(Res.string.tx_delete))
                            }
                        },
                        dismissButton = {
                            TextButton(onClick = { showDeleteDialog = false }) {
                                Text(stringResource(Res.string.cancel))
                            }
                        })
                }

                OutlinedButton(
                    onClick = onEdit,
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(stringResource(Res.string.edit_transaction))
                }

                Button(
                    onClick = { showDeleteDialog = true },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(stringResource(Res.string.delete_category))
                }
            }
        }
    }
}

private val mockExpense = Transaction(
    title = "Starbucks Coffee",
    amount = 350.00,
    type = TransactionType.EXPENSE,
    date = "July 12, 2026",
    time = "10:15 AM",
    paymentMethod = "Credit Card",
    category = "Food & Drinks",
    note = "Meeting with product team"
)

@Preview
@Composable
private fun TransactionDetailsLightPreview() {
    PocketGoalsTheme(themeMode = ThemeMode.LIGHT) {
        Surface {
            TransactionDetailsScreen(
                transaction = mockExpense,
                onBack = {},
                onEdit = {},
                onDelete = {})
        }
    }
}

@Preview
@Composable
private fun TransactionDetailsDarkPreview() {
    PocketGoalsTheme(themeMode = ThemeMode.DARK) {
        Surface {
            TransactionDetailsScreen(
                transaction = mockExpense,
                onBack = {},
                onEdit = {},
                onDelete = {})
        }
    }
}