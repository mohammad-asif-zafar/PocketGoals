package com.hathway.pocketgoals.presentation.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.hathway.pocketgoals.domain.model.Transaction
import com.hathway.pocketgoals.domain.model.TransactionType
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

import com.hathway.pocketgoals.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

class TransactionsViewModel(
    private val repository: TransactionRepository
) : ViewModel() {

    val transactions: StateFlow<List<Transaction>> = repository.getTransactions()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun addTransaction(transaction: Transaction) {
        viewModelScope.launch {
            repository.addTransaction(transaction)
        }
    }

    fun updateTransaction(transaction: Transaction) {
        viewModelScope.launch {
            repository.updateTransaction(transaction)
        }
    }
    
    fun deleteTransaction(id: String) {
        viewModelScope.launch {
            repository.deleteTransaction(id)
        }
    }

    /**
     * Compiles active state tracking data models into a clean raw byte array.
     * Returns a ByteArray representing a formatted CSV file text block.
     */
    fun compileTransactionReport(): ByteArray {
        val currentData = transactions.value


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
