package com.hathway.pocketgoals.presentation.ui.components.home_components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ChevronRight
import androidx.compose.material.icons.rounded.Fastfood
import androidx.compose.material.icons.rounded.ShoppingBag
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hathway.pocketgoals.presentation.ui.theme.PocketGoalsTheme
import kotlin.math.roundToInt

// Helper data structure for easy list mapping
data class HomeCategoryData(
    val name: String,
    val percentage: Double,
    val amount: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector,
    val color: Color
)

@Composable
fun HomeCategoryCard(
    title: String,
    categories: List<HomeCategoryData>,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        // Elevates slightly in light mode and uses surface tinted surfaces for dark mode depth
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Dynamically loop over rows inside the elevated card shell bounds
            categories.forEach { data ->
                HomeCategoryRow(
                    name = data.name,
                    percentage = data.percentage,
                    amount = data.amount,
                    icon = data.icon,
                    color = data.color
                )
            }
        }
    }
}

@Preview
@Composable
fun HomeCategoryCardLightPreview() {
    val sampleCategories = listOf(
        HomeCategoryData("Shopping", 42.5, "12,450", Icons.Rounded.ShoppingBag, Color(0xFFEF4444)),
        HomeCategoryData("Food & Dining", 28.0, "8,200", Icons.Rounded.Fastfood, Color(0xFFF59E0B)),
        HomeCategoryData("Entertainment", 15.0, "4,400", Icons.Rounded.ChevronRight, Color(0xFF3B82F6))
    )

    PocketGoalsTheme(darkTheme = false) {
        Box(modifier = Modifier.background(MaterialTheme.colorScheme.background).padding(16.dp)) {
            HomeCategoryCard(title = "Monthly Spending", categories = sampleCategories)
        }
    }
}

@Preview
@Composable
fun HomeCategoryCardDarkPreview() {
    val sampleCategories = listOf(
        HomeCategoryData("Shopping", 42.5, "12,450", Icons.Rounded.ShoppingBag, Color(0xFFEF4444)),
        HomeCategoryData("Food & Dining", 28.0, "8,200", Icons.Rounded.Fastfood, Color(0xFFF59E0B)),
        HomeCategoryData("Entertainment", 15.0, "4,400", Icons.Rounded.Warning, Color(0xFF3B82F6))
    )

    PocketGoalsTheme(darkTheme = true) {
        Box(modifier = Modifier.background(MaterialTheme.colorScheme.background).padding(16.dp)) {
            HomeCategoryCard(title = "Monthly Spending", categories = sampleCategories)
        }
    }
}


@Composable
fun HomeCategoryRow(
    name: String,
    percentage: Double,
    amount: String,
    icon: ImageVector,
    color: Color
) {
    var targetProgress by remember { mutableStateOf(0f) }

    LaunchedEffect(percentage) {
        targetProgress = (percentage / 100).toFloat()
    }

    val animatedProgress by animateFloatAsState(
        targetValue = targetProgress,
        animationSpec = tween(durationMillis = 800),
        label = "ProgressLoadAnimation"
    )

    // Calculate the integer percentage dynamically from the animated float
    val currentPercentageText = (animatedProgress * 100).roundToInt().toString()

    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier.size(40.dp).background(color, RoundedCornerShape(8.dp)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = name,
                tint = Color.White,
                modifier = Modifier.size(20.dp)
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = name,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    text = "₹$amount",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }

            Spacer(modifier = Modifier.height(6.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                LinearProgressIndicator(
                    progress = { animatedProgress },
                    modifier = Modifier.weight(1f).height(6.dp).clip(CircleShape),
                    color = color,
                    trackColor = color.copy(alpha = 0.1f)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    // Renders the live rolling text value updating alongside the loading bar frame updates
                    text = "$currentPercentageText%",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Preview
@Composable
fun HomeCategoryRowLightPreview() {
    PocketGoalsTheme(darkTheme = false) {
        Box(modifier = Modifier.background(MaterialTheme.colorScheme.background).padding(16.dp)) {
            HomeCategoryRow(
                name = "Shopping",
                percentage = 42.5,
                amount = "12,450",
                icon = Icons.Rounded.ShoppingBag,
                color = Color(0xFFEF4444)
            )
        }
    }
}
