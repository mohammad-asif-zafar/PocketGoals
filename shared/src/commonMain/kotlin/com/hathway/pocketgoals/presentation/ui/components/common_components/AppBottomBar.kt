package com.hathway.pocketgoals.presentation.ui.components.common_components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hathway.pocketgoals.domain.model.ThemeMode
import com.hathway.pocketgoals.presentation.ui.navigation.BottomNavItem
import com.hathway.pocketgoals.presentation.ui.navigation.NavigationItem
import com.hathway.pocketgoals.presentation.ui.theme.PocketGoalsTheme
import org.jetbrains.compose.resources.stringResource

@Composable
fun AppBottomBar(
    currentRoute: String?,
    onNavigate: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val leftItems = listOf(BottomNavItem.Home, BottomNavItem.Transactions)
    val rightItems = listOf(BottomNavItem.Analytics, BottomNavItem.Goals)
    val centerItem = BottomNavItem.AddExpense

    Box(
        modifier = modifier.fillMaxWidth().padding(horizontal = 16.dp).padding(bottom = 12.dp),
        contentAlignment = Alignment.BottomCenter
    ) {
        // 1. Underlying Bar Layout Shell
        Surface(
            modifier = Modifier.fillMaxWidth().height(72.dp),
            shape = RoundedCornerShape(24.dp),
            color = MaterialTheme.colorScheme.surface,
            shadowElevation = 8.dp
        ) {
            Row(
                modifier = Modifier.fillMaxSize(), verticalAlignment = Alignment.CenterVertically
            ) {
                // Left Wing Grouping Container
                Row(
                    modifier = Modifier.weight(1f), horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    leftItems.forEach { item ->
                        NavigationItem(
                            item = item,
                            isSelected = currentRoute == item.route,
                            onNavigate = { onNavigate(item.route) })
                    }
                }

                // Explicit Floating Center Gap Anchor
                Spacer(modifier = Modifier.size(72.dp))

                // Right Wing Grouping Container
                Row(
                    modifier = Modifier.weight(1f), horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    rightItems.forEach { item ->
                        NavigationItem(
                            item = item,
                            isSelected = currentRoute == item.route,
                            onNavigate = { onNavigate(item.route) })
                    }
                }
            }
        }

        // 2. Floating Action Button Overlay (Colors automatically adjust to Dark Theme)
        Column(
            modifier = Modifier.offset(y = (-14).dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Surface(
                modifier = Modifier.size(56.dp).clip(CircleShape)
                    // FIXED: Better target ergonomics via clip + clickable layering
                    .clickable { onNavigate(centerItem.route) },
                shape = CircleShape,
                color = MaterialTheme.colorScheme.primary, // FIXED: Now uses Primary theme slot
                shadowElevation = 6.dp
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = centerItem.icon,
                        contentDescription = stringResource(centerItem.titleRes),
                        tint = MaterialTheme.colorScheme.onPrimary, // FIXED: Now adapts cleanly to light/dark
                        modifier = Modifier.size(28.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = stringResource(centerItem.titleRes),
                color = MaterialTheme.colorScheme.primary, // FIXED: Adaptive color slot
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}


@Preview(name = "Bar Light Mode", showBackground = true, widthDp = 360, heightDp = 120)
@Composable
fun AppBottomBarLightPreview() {
    PocketGoalsTheme(themeMode = ThemeMode.LIGHT) {
        AppBottomBarPreviewContent()
    }
}

@Preview(name = "Bar Dark Mode", showBackground = true, widthDp = 360, heightDp = 120)
@Composable
fun AppBottomBarDarkPreview() {
    PocketGoalsTheme(themeMode = ThemeMode.DARK) {
        AppBottomBarPreviewContent()
    }
}

@Composable
private fun AppBottomBarPreviewContent() {
    Box(
        modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter
    ) {
        AppBottomBar(
            currentRoute = "home", onNavigate = {})
    }
}