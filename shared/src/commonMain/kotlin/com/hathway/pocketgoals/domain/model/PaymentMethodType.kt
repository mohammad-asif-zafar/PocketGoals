package com.hathway.pocketgoals.domain.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountBalance
import androidx.compose.material.icons.rounded.CreditCard
import androidx.compose.material.icons.rounded.Payments
import androidx.compose.material.icons.rounded.QrCodeScanner
import androidx.compose.ui.graphics.vector.ImageVector

// Enum representing support modes with localized title slots
enum class PaymentMethodType(val icon: ImageVector) {
    CASH(Icons.Rounded.Payments),
    CARD(Icons.Rounded.CreditCard),
    UPI(Icons.Rounded.QrCodeScanner),
    BANK(Icons.Rounded.AccountBalance)
}