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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hathway.pocketgoals.presentation.ui.navigation.BottomNavItem
import com.hathway.pocketgoals.presentation.ui.navigation.NavigationItem
import org.jetbrains.compose.resources.stringResource

@Composable
fun AppBottomBar(
    currentRoute: String?,
    onNavigate: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    // 1. Separate your items to keep the Row layout mathematically uniform
    val leftItems = listOf(BottomNavItem.Home, BottomNavItem.Transactions)
    val rightItems = listOf(BottomNavItem.Analytics, BottomNavItem.Goals)

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .padding(bottom = 12.dp),
        contentAlignment = Alignment.BottomCenter
    ) {
        // 2. Underlying bar structure (Constrained height container)
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .height(72.dp),
            shape = RoundedCornerShape(24.dp),
            color = MaterialTheme.colorScheme.surface,
            shadowElevation = 12.dp
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Populate Left Side
                leftItems.forEach { item ->
                    NavigationItem(
                        item = item,
                        isSelected = currentRoute == item.route,
                        onNavigate = { onNavigate(item.route) }
                    )
                }

                // Middle Placeholder Gap spacing so layout components don't visually overlap the center overlay
                Spacer(modifier = Modifier.weight(1f))

                // Populate Right Side
                rightItems.forEach { item ->
                    NavigationItem(
                        item = item,
                        isSelected = currentRoute == item.route,
                        onNavigate = { onNavigate(item.route) }
                    )
                }
            }
        }

        // 3. Floating Overlay Center Action Button Layout block
        Column(
            modifier = Modifier.offset(y = (-16).dp), // Adjust height offset to achieve intended floating style
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val centerItem = BottomNavItem.AddExpense
            Surface(
                modifier = Modifier
                    .size(60.dp)
                    .clickable { onNavigate(centerItem.route) },
                shape = CircleShape,
                color = Color(0xFF0F766E), // Matching Deep Teal background
                shadowElevation = 6.dp
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = centerItem.icon,
                        contentDescription = stringResource(centerItem.titleRes),
                        tint = Color.White,
                        modifier = Modifier.size(28.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = stringResource(centerItem.titleRes),
                color = Color(0xFF0F766E),
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

