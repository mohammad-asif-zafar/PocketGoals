package com.hathway.pocketgoals.presentation.ui.components.proflie_components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.DarkMode
import androidx.compose.material.icons.rounded.LightMode
import androidx.compose.material.icons.rounded.SettingsSuggest
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hathway.pocketgoals.domain.model.ThemeMode
import com.hathway.pocketgoals.presentation.ui.theme.Info
import com.hathway.pocketgoals.presentation.ui.theme.PocketGoalsTheme
import com.hathway.pocketgoals.presentation.ui.theme.Warning

@Composable
fun ThemeOptionItem(
    title: String,
    subtitle: String,
    icon: ImageVector,
    iconColor: Color,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val borderColor = if (isSelected) MaterialTheme.colorScheme.primary else Color.Transparent
    val backgroundColor =
        if (isSelected) MaterialTheme.colorScheme.primary.copy(alpha = 0.05f) else MaterialTheme.colorScheme.surfaceVariant.copy(
            alpha = 0.3f
        )

    Row(
        modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(16.dp))
            .background(backgroundColor).then(
                if (isSelected) Modifier.border(1.dp, borderColor, RoundedCornerShape(16.dp))
                else Modifier
            ).clickable(onClick = onClick).padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier.size(48.dp).background(iconColor.copy(alpha = 0.1f), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = iconColor,
                modifier = Modifier.size(24.dp)
            )
        }
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        RadioButton(
            selected = isSelected, onClick = null, colors = RadioButtonDefaults.colors(
                selectedColor = MaterialTheme.colorScheme.primary
            )
        )
    }
}


@Preview(name = "Theme Options Light", showBackground = true, widthDp = 360)
@Composable
fun ThemeOptionItemLightPreview() {
    PocketGoalsTheme(themeMode = ThemeMode.LIGHT) {
        ThemeOptionsPreviewContainer()
    }
}

@Preview(name = "Theme Options Dark", showBackground = true, widthDp = 360)
@Composable
fun ThemeOptionItemDarkPreview() {
    PocketGoalsTheme(themeMode = ThemeMode.DARK) {
        ThemeOptionsPreviewContainer()
    }
}

@Composable
private fun ThemeOptionsPreviewContainer() {
    Column(
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Option 1: Selected state using dynamic theme warning tint
        ThemeOptionItem(
            title = "Light Theme",
            subtitle = "Bright, high-contrast visual display",
            icon = Icons.Rounded.LightMode,
            iconColor = Warning,
            isSelected = true,
            onClick = {})

        // Option 2: Unselected state using custom tertiary purple tint
        ThemeOptionItem(
            title = "Dark Theme",
            subtitle = "Easy on the eyes for dim spaces",
            icon = Icons.Rounded.DarkMode,
            iconColor = MaterialTheme.colorScheme.tertiary,
            isSelected = false,
            onClick = {})

        // Option 3: Unselected state using custom system info blue tint
        ThemeOptionItem(
            title = "System Default",
            subtitle = "Follows device configuration rules",
            icon = Icons.Rounded.SettingsSuggest,
            iconColor = Info,
            isSelected = false,
            onClick = {})
    }
}