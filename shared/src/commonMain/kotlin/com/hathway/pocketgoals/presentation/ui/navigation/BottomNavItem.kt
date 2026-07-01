package com.hathway.pocketgoals.presentation.ui.navigation

import androidx.compose.ui.graphics.vector.ImageVector
import com.hathway.pocketgoals.presentation.ui.icons.AppIcons
import org.jetbrains.compose.resources.StringResource
import pocketgoals.shared.generated.resources.Res
import pocketgoals.shared.generated.resources.add_expense
import pocketgoals.shared.generated.resources.analytics
import pocketgoals.shared.generated.resources.goals
import pocketgoals.shared.generated.resources.home
import pocketgoals.shared.generated.resources.transactions


sealed class BottomNavItem(
    val titleRes: StringResource, // Changes from String to StringResource
    val icon: ImageVector,
    val route: StringResource
) {
    object Home : BottomNavItem(
        titleRes = Res.string.home,
        icon = AppIcons.Home,
        route = Res.string.home
    )

    object Transactions : BottomNavItem(
        titleRes = Res.string.transactions,
        icon = AppIcons.Transactions,
        route = Res.string.transactions
    )

    object AddExpense : BottomNavItem(
        titleRes = Res.string.add_expense,
        icon = AppIcons.Add,
        route = Res.string.add_expense
    )

    object Analytics : BottomNavItem(
        titleRes = Res.string.analytics,
        icon = AppIcons.Analytics,
        route = Res.string.analytics
    )

    object Goals : BottomNavItem(
        titleRes = Res.string.goals,
        icon = AppIcons.Goals,
        route = Res.string.goals
    )
}


