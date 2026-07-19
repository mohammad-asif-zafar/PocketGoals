package com.hathway.pocketgoals.presentation.ui.icons

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.TrendingUp
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.rounded.*
import androidx.compose.ui.graphics.vector.ImageVector

object IconHelper {
    fun getIconByName(name: String): ImageVector {
        return when (name) {
            "Work" -> Icons.Rounded.Work
            "BusinessCenter" -> Icons.Rounded.BusinessCenter
            "Person" -> Icons.Rounded.Person
            "TrendingUp" -> Icons.AutoMirrored.Rounded.TrendingUp
            "Home" -> Icons.Rounded.Home
            "AccountBalance" -> Icons.Rounded.AccountBalance
            "AddCard" -> Icons.Rounded.AddCard
            "Restaurant" -> Icons.Rounded.Restaurant
            "ShoppingCart" -> Icons.Rounded.ShoppingCart
            "DirectionsCar" -> Icons.Rounded.DirectionsCar
            "LocalGasStation" -> Icons.Rounded.LocalGasStation
            "Lightbulb" -> Icons.Rounded.Lightbulb
            "Favorite" -> Icons.Rounded.Favorite
            "ShoppingBag" -> Icons.Rounded.ShoppingBag
            "PlayCircle" -> Icons.Rounded.PlayCircle
            "Flight" -> Icons.Rounded.Flight
            "School" -> Icons.Rounded.School
            "Groups" -> Icons.Rounded.Groups
            "AccountBalanceWallet" -> Icons.Rounded.AccountBalanceWallet
            "Savings" -> Icons.Rounded.Savings
            "CardGiftcard" -> Icons.Rounded.CardGiftcard
            "Pets" -> Icons.Rounded.Pets
            "VolunteerActivism" -> Icons.Rounded.VolunteerActivism
            "Subscriptions" -> Icons.Rounded.Subscriptions
            "AutoAwesome" -> Icons.Rounded.AutoAwesome
            "FitnessCenter" -> Icons.Rounded.FitnessCenter
            "Build" -> Icons.Rounded.Build
            "MedicalServices" -> Icons.Rounded.MedicalServices
            "AddCircle" -> Icons.Rounded.AddCircle
            else -> Icons.Default.Image
        }
    }

    fun getIconName(icon: ImageVector): String {
        return icon.name.split(".").last()
    }
}
