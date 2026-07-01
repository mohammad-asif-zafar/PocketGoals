package com.hathway.pocketgoals.presentation.ui.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hathway.pocketgoals.presentation.ui.theme.OnSurfaceVariant
import com.hathway.pocketgoals.presentation.ui.theme.Primary
import org.jetbrains.compose.resources.stringResource

@Composable
fun RowScope.NavigationItem(
    item: BottomNavItem,
    isSelected: Boolean,
    onNavigate: () -> Unit
) {
    val activeColor = Primary // Deep Teal
    val inactiveColor = OnSurfaceVariant // Slate 500
    val color = if (isSelected) activeColor else inactiveColor

    Column(
        modifier = Modifier
            .weight(1f)
            .fillMaxHeight()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onNavigate
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = item.icon,
            contentDescription = stringResource(item.titleRes), // FIXED: Fixed typo item.t -> item.titleRes wrapped in stringResource
            tint = color,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = stringResource(item.titleRes), // FIXED: Fixed property call item.title -> item.titleRes wrapped in stringResource
            color = color,
            maxLines = 1,
            style = MaterialTheme.typography.labelSmall.copy(
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                fontSize = 10.sp
            )
        )
        Spacer(modifier = Modifier.height(2.dp))
        if (isSelected) {
            Box(
                modifier = Modifier
                    .width(18.dp)
                    .height(2.5.dp)
                    .background(color, shape = RoundedCornerShape(2.dp))
            )
        } else {
            Spacer(modifier = Modifier.height(2.5.dp))
        }
    }
}
