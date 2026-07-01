package com.hathway.pocketgoals.presentation.ui.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

object AppIcons {

    // 1. Home Icon (Filled style with curved top and door cutout)
    val Home: ImageVector
        get() = ImageVector.Builder(
            name = "Home",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).path(
            fill = SolidColor(Color.Black),
            pathFillType = PathFillType.EvenOdd
        ) {
            // Main structure of the house
            moveTo(12f, 3f)
            curveTo(10f, 4.8f, 5f, 9.3f, 4f, 10.5f)
            verticalLineTo(20f)
            curveTo(4f, 21.1f, 4.9f, 22f, 6f, 22f)
            horizontalLineTo(10f)
            verticalLineTo(15f)
            curveTo(10f, 13.9f, 10.9f, 13f, 12f, 13f)
            curveTo(13.1f, 13f, 14f, 13.9f, 14f, 15f)
            verticalLineTo(22f)
            horizontalLineTo(18f)
            curveTo(19.1f, 22f, 20f, 21.1f, 20f, 20f)
            verticalLineTo(10.5f)
            curveTo(19f, 9.3f, 14f, 4.8f, 12f, 3f)
            close()
        }.build()

    // 2. Transactions Icon (Horizontal opposing exchange arrows)
    val Transactions: ImageVector
        get() = ImageVector.Builder(
            name = "Transactions",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).path(
            stroke = SolidColor(Color.Black),
            strokeLineWidth = 2f,
            strokeLineCap = StrokeCap.Round,
            strokeLineJoin = StrokeJoin.Round
        ) {
            // Top Arrow pointing Left
            moveTo(20f, 8f)
            horizontalLineTo(4f)
            moveTo(4f, 8f)
            lineTo(8f, 4f)
            moveTo(4f, 8f)
            lineTo(8f, 12f)

            // Bottom Arrow pointing Right
            moveTo(4f, 16f)
            horizontalLineTo(20f)
            moveTo(20f, 16f)
            lineTo(16f, 12f)
            moveTo(20f, 16f)
            lineTo(16f, 20f)
        }.build()

    // 3. Analytics Icon (Progressive bar chart with rounded corners)
    val Analytics: ImageVector
        get() = ImageVector.Builder(
            name = "Analytics",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).path(
            stroke = SolidColor(Color.Black),
            strokeLineWidth = 2f,
            strokeLineCap = StrokeCap.Round,
            strokeLineJoin = StrokeJoin.Round
        ) {
            // Left shortest bar
            moveTo(5f, 20f)
            verticalLineTo(14f)
            curveTo(5f, 13.4f, 5.4f, 13f, 6f, 13f)
            curveTo(6.6f, 13f, 7f, 13.4f, 7f, 14f)
            verticalLineTo(20f)

            // Middle bar
            moveTo(11f, 20f)
            verticalLineTo(9f)
            curveTo(11f, 8.4f, 11.4f, 8f, 12f, 8f)
            curveTo(12.6f, 8f, 13f, 8.4f, 13f, 9f)
            verticalLineTo(20f)

            // Right tallest bar
            moveTo(17f, 20f)
            verticalLineTo(5f)
            curveTo(17f, 4.4f, 17.4f, 4f, 18f, 4f)
            curveTo(18.6f, 4f, 19f, 4.4f, 19f, 5f)
            verticalLineTo(20f)
        }.build()

    // 4. Goals Icon (Target bullseye with arrow hitting center)
    val Goals: ImageVector
        get() = ImageVector.Builder(
            name = "Goals",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).path(
            stroke = SolidColor(Color.Black),
            strokeLineWidth = 2f,
            strokeLineCap = StrokeCap.Round,
            strokeLineJoin = StrokeJoin.Round
        ) {
            // Outer Target Circle
            moveTo(12f, 21f)
            curveTo(16.97f, 21f, 21f, 16.97f, 21f, 12f)
            curveTo(21f, 7.03f, 16.97f, 3f, 12f, 3f)
            curveTo(7.03f, 3f, 3f, 7.03f, 3f, 12f)
            curveTo(3f, 16.97f, 7.03f, 21f, 12f, 21f)
            close()

            // Inner Target Circle
            moveTo(12f, 16f)
            curveTo(14.21f, 16f, 16f, 14.21f, 16f, 12f)
            curveTo(16f, 9.79f, 14.21f, 8f, 12f, 8f)
            curveTo(9.79f, 8f, 8f, 9.79f, 8f, 12f)
            curveTo(8f, 14.21f, 9.79f, 16f, 12f, 16f)
            close()

            // Diagonal Arrow hitting center (Top Right to Center)
            moveTo(18f, 6f)
            lineTo(12f, 12f)
            // Arrow fletching/feathers lines
            moveTo(15.5f, 5.5f)
            lineTo(18.5f, 8.5f)
        }.build()

    // Add this property directly inside your existing 'object AppIcons' file
    val Add: ImageVector
        get() = ImageVector.Builder(
            name = "Add",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).path(
            stroke = SolidColor(Color.Black),
            strokeLineWidth = 2.5f, // Made slightly thicker to look bold inside the FAB
            strokeLineCap = StrokeCap.Round,
            strokeLineJoin = StrokeJoin.Round
        ) {
            // Horizontal bar
            moveTo(5f, 12f)
            horizontalLineTo(19f)

            // Vertical bar
            moveTo(12f, 5f)
            verticalLineTo(19f)
        }.build()

}
