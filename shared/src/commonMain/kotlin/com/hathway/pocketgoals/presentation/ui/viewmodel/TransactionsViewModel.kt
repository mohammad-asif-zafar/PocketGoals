package com.hathway.pocketgoals.presentation.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.hathway.pocketgoals.domain.Transaction
import com.hathway.pocketgoals.domain.TransactionType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountBalance
import androidx.compose.material.icons.rounded.DirectionsCar
import androidx.compose.material.icons.rounded.Lightbulb
import androidx.compose.material.icons.rounded.ShoppingCart
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class TransactionsViewModel : ViewModel() {

    // Seed initial mock tracking data items into a single master state holder stream
    private val _transactions = MutableStateFlow(
        listOf(
            Transaction(
                "1",
                "Groceries",
                2500.0,
                TransactionType.EXPENSE,
                "18 May 2024",
                "10:30 AM",
                "Groceries",
                "Cash",
                "Weekly groceries from supermarket",
                Icons.Rounded.ShoppingCart
            ), Transaction(
                "2",
                "Salary",
                125000.0,
                TransactionType.INCOME,
                "15 May 2024",
                "09:00 AM",
                "Salary",
                "Bank Transfer",
                "",
                Icons.Rounded.AccountBalance
            ), Transaction(
                "3",
                "Electricity Bill",
                1200.0,
                TransactionType.EXPENSE,
                "14 May 2024",
                "08:00 PM",
                "Bills",
                "UPI",
                "",
                Icons.Rounded.Lightbulb
            ), Transaction(
                "4",
                "Fuel",
                4500.0,
                TransactionType.EXPENSE,
                "14 May 2024",
                "04:45 PM",
                "Transport",
                "Card",
                "",
                Icons.Rounded.DirectionsCar
            )
        )
    )
    val transactions: StateFlow<List<Transaction>> = _transactions.asStateFlow()

    fun addTransaction(transaction: Transaction) {
        _transactions.update { current ->
            listOf(transaction) + current
        }
    }

    fun updateTransaction(transaction: Transaction) {
        viewModelScope.launch {
            _transactions.update { currentList ->
                currentList.map { existingTx ->
                    // Uses ID matching pipeline to ensure identical titles do not break data states
                    if (existingTx.id == transaction.id) transaction else existingTx
                }
            }
        }
    }

    /**
     * Compiles active state tracking data models into a clean raw byte array.
     * Returns a ByteArray representing a formatted CSV file text block.
     */
    fun compileTransactionReport(): ByteArray {
        val currentData = _transactions.value

        // Build the localized column header rails array pipeline
        val csvBuilder = StringBuilder()
        csvBuilder.append("Title,Amount,Type,Date,Time,Payment Method,Category,Notes\n")

        // Loop and format each structural list item entity model cleanly
        currentData.forEach { tx ->
            val sign = if (tx.type == TransactionType.INCOME) "+" else "-"

            // Clean dynamic text strings of commas to prevent spreadsheet structural parsing errors
            val safeTitle = tx.title.replace(",", " ")
            val safeNote = tx.note.replace(",", " ").ifEmpty { "N/A" }
            val safeCategory = tx.category.replace(",", " ")
            val safeMethod = tx.paymentMethod.replace(",", " ")

            csvBuilder.append("$safeTitle,")
            csvBuilder.append("$sign${tx.amount.toInt()},")
            csvBuilder.append("${tx.type.name},")
            csvBuilder.append("${tx.date},")
            csvBuilder.append("${tx.time},")
            csvBuilder.append("$safeMethod,")
            csvBuilder.append("$safeCategory,")
            csvBuilder.append("$safeNote\n")
        }

        // Return a raw platform-agnostic byte buffer allocation block array stream
        return csvBuilder.toString().encodeToByteArray()
    }
}
