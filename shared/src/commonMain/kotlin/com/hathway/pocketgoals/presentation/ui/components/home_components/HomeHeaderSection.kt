package com.hathway.pocketgoals.presentation.ui.components.home_components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.NotificationsNone
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hathway.pocketgoals.presentation.ui.theme.PocketGoalsTheme
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

@Composable
fun HomeHeaderSection(
    name: String,
    unreadCount: Int = 0, // 1. Added unreadCount parameter
    onNotificationClick: () -> Unit = {},
    onProfileClick: () -> Unit = {}
) {
    // Multiplatform-safe time calculation
    val greetingMessage = remember {
        val currentMoment = kotlinx.datetime.Clock.System.now()
        val localDateTime = currentMoment.toLocalDateTime(TimeZone.currentSystemDefault())
        val currentHour = localDateTime.hour // Returns 0..23 representing the hour of the day

        when (currentHour) {
            in 0..11 -> "Good Morning,"
            in 12..16 -> "Good Afternoon,"
            else -> "Good Evening,"
        }
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = greetingMessage,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = "$name 👋",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            // Notification Area
            Box(
                modifier = Modifier
                    .size(44.dp) // Expanded slightly to fit larger text badges comfortably
                    .clip(CircleShape)
                    .clickable { onNotificationClick() },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Rounded.NotificationsNone,
                    contentDescription = "Notifications",
                    tint = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.size(28.dp)
                )

                // 2. Dynamic Badge Display Logic
                if (unreadCount > 0) {
                    val badgeText = if (unreadCount > 99) "99+" else unreadCount.toString()

                    Text(
                        text = badgeText,
                        color = Color.White,
                        style = MaterialTheme.typography.labelSmall, // Smallest typography tier
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .offset(x = (-2).dp, y = 2.dp)
                            .sizeIn(minWidth = 16.dp, minHeight = 16.dp) // Forces a round circle for single digits
                            .background(Color.Red, CircleShape)
                            .padding(horizontal = 4.dp, vertical = 1.dp) // Inner text cushioning
                    )
                }
            }

            Spacer(modifier = Modifier.width(12.dp))

            // Profile Button
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.surfaceVariant)
                    .clickable { onProfileClick() },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Rounded.Person,
                    contentDescription = "Profile",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}


@Preview
@Composable
fun HomeHeaderSectionWithBadgePreview() {
    PocketGoalsTheme(darkTheme = false) {
        Box(modifier = Modifier.background(MaterialTheme.colorScheme.background).padding(16.dp)) {
            HomeHeaderSection(
                name = "Alex",
                unreadCount = 5, // Testing single digit circle badge
                onNotificationClick = {}
            )
        }
    }
}

@Preview
@Composable
fun HomeHeaderSectionWithLargeBadgePreview() {
    PocketGoalsTheme(darkTheme = false) {
        Box(modifier = Modifier.background(MaterialTheme.colorScheme.background).padding(16.dp)) {
            HomeHeaderSection(
                name = "Alex",
                unreadCount = 120, // Testing "99+" capsule shape
                onNotificationClick = {}
            )
        }
    }
}
