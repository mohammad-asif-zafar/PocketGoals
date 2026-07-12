package com.hathway.pocketgoals.presentation.ui.components.add_income_components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowRight
import androidx.compose.material.icons.rounded.Category
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hathway.pocketgoals.domain.model.ThemeMode
import com.hathway.pocketgoals.presentation.ui.theme.PocketGoalsTheme
import org.jetbrains.compose.resources.stringResource
import pocketgoals.shared.generated.resources.Res
import pocketgoals.shared.generated.resources.cd_select_option

@Composable
fun FormItemRow(
    onClick: () -> Unit,
    icon: ImageVector,
    iconColor: Color,
    text: String,
    borderColor: Color,
    modifier: Modifier = Modifier,
    isPlaceholder: Boolean = false
) {
    val isDark = isSystemInDarkTheme()

    // Core Fix: Increases opacity in dark mode to preserve badge visibility
    val backgroundAlpha = if (isDark) 0.2f else 0.1f

    Row(
        modifier = modifier.fillMaxWidth().height(56.dp)
            // Core Fix: clip and clickable placed BEFORE border and padding prevents square ripple bleeding
            .clip(RoundedCornerShape(12.dp)).clickable(onClick = onClick)
            .border(1.dp, borderColor, RoundedCornerShape(12.dp)).padding(horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Left Icon Container Badge
        Box(
            modifier = Modifier.size(36.dp)
                .background(iconColor.copy(alpha = backgroundAlpha), RoundedCornerShape(8.dp)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = iconColor,
                modifier = Modifier.size(20.dp)
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        // Center Content Text Element
        Text(
            text = text,
            color = if (isPlaceholder) MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
            else MaterialTheme.colorScheme.onSurface,
            fontWeight = FontWeight.Medium,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        Spacer(modifier = Modifier.weight(1f))

        // Right Navigation Action Indicator Chevron
        Icon(
            imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowRight,
            // Core Fix: Localized accessibility description string
            contentDescription = stringResource(Res.string.cd_select_option),
            tint = MaterialTheme.colorScheme.outline
        )
    }
}

// ==========================================================
// Theme-Safe Split Previews
// ==========================================================

@Preview
@Composable
fun FormItemRowPlaceholderLightPreview() {
    PocketGoalsTheme(themeMode = ThemeMode.LIGHT) {
        Box(
            modifier = Modifier.background(MaterialTheme.colorScheme.background).padding(16.dp)
        ) {
            FormItemRow(
                onClick = {},
                icon = Icons.Rounded.Category,
                iconColor = MaterialTheme.colorScheme.primary,
                text = "Select Category", // Explicit string bypasses stringResource missing bugs
                isPlaceholder = true,
                borderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.4f)
            )
        }
    }
}

@Preview
@Composable
fun FormItemRowSelectedDarkPreview() {
    PocketGoalsTheme(themeMode = ThemeMode.DARK) {
        Box(
            modifier = Modifier.background(MaterialTheme.colorScheme.background).padding(16.dp)
        ) {
            FormItemRow(
                onClick = {},
                icon = Icons.Rounded.Category,
                iconColor = Color(0xFF2DD4BF),
                text = "Groceries", // Explicit value verification string
                isPlaceholder = false,
                borderColor = MaterialTheme.colorScheme.primary
            )
        }
    }
}
