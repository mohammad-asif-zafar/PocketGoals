package com.hathway.pocketgoals.presentation.ui.components.category_components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.material.icons.automirrored.rounded.MenuBook
import androidx.compose.material.icons.automirrored.rounded.TrendingUp
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IconPickerSheet(
    selectedIcon: ImageVector,
    selectedColor: Color,
    onIconSelected: (ImageVector) -> Unit,
    onDismiss: () -> Unit
) {
    val icons = listOf(
        // Food & Dining
        Icons.Rounded.Restaurant, Icons.Rounded.Coffee, Icons.Rounded.Fastfood,
        Icons.Rounded.LunchDining, Icons.Rounded.Icecream,
        // Daily Living
        Icons.Rounded.ShoppingCart, Icons.Rounded.ShoppingBag, Icons.Rounded.LocalMall,
        Icons.Rounded.Checkroom, Icons.Rounded.Pets,
        // Transportation
        Icons.Rounded.DirectionsCar, Icons.Rounded.LocalGasStation, Icons.Rounded.DirectionsBus,
        Icons.Rounded.Train, Icons.Rounded.Flight,
        // Home
        Icons.Rounded.Home, Icons.Rounded.Lightbulb, Icons.Rounded.Receipt,
        Icons.Rounded.Bolt, Icons.Rounded.WaterDrop,
        // Health
        Icons.Rounded.Favorite, Icons.Rounded.MedicalServices, Icons.Rounded.LocalHospital,
        Icons.Rounded.FitnessCenter, Icons.Rounded.Spa,
        // Entertainment
        Icons.Rounded.PlayCircle, Icons.Rounded.Movie, Icons.Rounded.MusicNote,
        Icons.Rounded.SportsEsports, Icons.Rounded.CameraAlt,
        // Education & Work
        Icons.Rounded.School, Icons.AutoMirrored.Rounded.MenuBook, Icons.Rounded.Work,
        Icons.Rounded.Laptop, Icons.Rounded.Calculate,
        // Finance
        Icons.Rounded.AccountBalanceWallet, Icons.AutoMirrored.Rounded.TrendingUp, Icons.Rounded.Savings,
        Icons.Rounded.AddCard, Icons.Rounded.Payments,
        // Social & Others
        Icons.Rounded.Groups, Icons.Rounded.CardGiftcard, Icons.Rounded.VolunteerActivism,
        Icons.Rounded.AutoAwesome, Icons.Rounded.Build,
        Icons.Rounded.PhoneIphone, Icons.Rounded.Subscriptions, Icons.Rounded.Cloud,
        Icons.Rounded.MoreHoriz, Icons.Rounded.Category
    )

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
        containerColor = MaterialTheme.colorScheme.surface,
        dragHandle = { BottomSheetDefaults.DragHandle() }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .padding(bottom = 32.dp)
        ) {
            Text(
                text = "Choose Icon",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            LazyVerticalGrid(
                columns = GridCells.Fixed(5),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(icons) { icon ->
                    val isCurrent = icon == selectedIcon
                    Box(
                        modifier = Modifier
                            .aspectRatio(1f)
                            .clip(RoundedCornerShape(12.dp))
                            .background(
                                if (isCurrent) selectedColor.copy(alpha = 0.1f)
                                else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                            )
                            .clickable {
                                onIconSelected(icon)
                                onDismiss()
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = icon,
                            contentDescription = null,
                            tint = if (isCurrent) selectedColor else MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            }
        }
    }
}