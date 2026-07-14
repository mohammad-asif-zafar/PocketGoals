package com.hathway.pocketgoals.domain.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import org.jetbrains.compose.resources.StringResource
import pocketgoals.shared.generated.resources.Res
import pocketgoals.shared.generated.resources.category_business
import pocketgoals.shared.generated.resources.category_car
import pocketgoals.shared.generated.resources.category_custom
import pocketgoals.shared.generated.resources.category_education
import pocketgoals.shared.generated.resources.category_health
import pocketgoals.shared.generated.resources.category_home
import pocketgoals.shared.generated.resources.category_travel
import pocketgoals.shared.generated.resources.category_wedding

data class GoalCategory(
    val name: StringResource, // References localized bundle strings
    val icon: ImageVector,
    val color: Color
) {
    companion object {
        val defaultCategories = listOf(
            GoalCategory(Res.string.category_travel, Icons.Rounded.Flight, Color(0xFF0EA5E9)),
            GoalCategory(Res.string.category_car, Icons.Rounded.DirectionsCar, Color(0xFF3B82F6)),
            GoalCategory(Res.string.category_home, Icons.Rounded.Home, Color(0xFF6366F1)),
            GoalCategory(Res.string.category_education, Icons.Rounded.School, Color(0xFF14B8A6)),
            GoalCategory(Res.string.category_wedding, Icons.Rounded.Favorite, Color(0xFFEC4899)),
            GoalCategory(
                Res.string.category_health, Icons.Rounded.MedicalServices, Color(0xFFEF4444)
            ),
            GoalCategory(Res.string.category_custom, Icons.Rounded.AddCircle, Color(0xFF10B981)),
            GoalCategory(
                Res.string.category_business, Icons.Rounded.BusinessCenter, Color(0xFFF59E0B)
            )
        )
    }
}
