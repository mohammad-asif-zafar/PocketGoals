package com.hathway.pocketgoals.presentation.ui.state

import com.hathway.pocketgoals.domain.model.Transaction

sealed class TransactionFlowState {
    data object List : TransactionFlowState()
    data class Details(val transaction: Transaction) : TransactionFlowState()
    data class Edit(val transaction: Transaction) : TransactionFlowState()
}