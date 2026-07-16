package com.hathway.pocketgoals.presentation.ui.components.home_components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountBalanceWallet
import androidx.compose.material.icons.rounded.DirectionsCar
import androidx.compose.material.icons.rounded.Restaurant
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hathway.pocketgoals.domain.model.ThemeMode
import com.hathway.pocketgoals.presentation.ui.theme.PocketGoalsTheme

@Composable
fun HomeQuickActionItem(
    label: String,
    icon: ImageVector,
    bgColor: Color,
    iconColor: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.clip(RoundedCornerShape(12.dp)).clickable { onClick() }.padding(4.dp)
    ) {
        Box(
            modifier = Modifier.size(56.dp).background(bgColor, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = iconColor,
                modifier = Modifier.size(24.dp)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            textAlign = TextAlign.Center,
            lineHeight = 14.sp,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Preview(name = "Actions Light Mode", showBackground = true, widthDp = 360)
@Composable
fun HomeQuickActionItemLightPreview() {
    PocketGoalsTheme(themeMode = ThemeMode.LIGHT) {
        QuickActionsPreviewRow()
    }
}

@Preview(name = "Actions Dark Mode", showBackground = true, widthDp = 360)
@Composable
fun HomeQuickActionItemDarkPreview() {
    PocketGoalsTheme(themeMode = ThemeMode.DARK) {
        QuickActionsPreviewRow()
    }
}

@Composable
private fun QuickActionsPreviewRow() {

    Row(
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        // Example 1: Fixed by adding the correct theme engine scope token prefix
        HomeQuickActionItem(
            label = "Career",
            icon = Icons.Rounded.AccountBalanceWallet,
            bgColor = Color(0xFF064E3B),
            iconColor = Color(0xFF064E3B),
            onClick = {})

        // Example 2: Using Custom Goal Category Finance Tokens
        HomeQuickActionItem(
            label = "Finance",
            icon = Icons.Rounded.Restaurant,
            bgColor = Color(0xFF7C2D12),
            iconColor = Color(0xFF7C2D12),
            onClick = {})

        // Example 3: Using Custom Goal Category Health Tokens
        HomeQuickActionItem(
            label = "Health",
            icon = Icons.Rounded.DirectionsCar,
            bgColor = Color(0xFFFFF7ED),
            iconColor = Color(0xFFFFF7ED),
            onClick = {})
    }
}