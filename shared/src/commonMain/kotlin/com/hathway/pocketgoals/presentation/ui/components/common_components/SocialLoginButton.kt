package com.hathway.pocketgoals.presentation.ui.components.common_components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SocialLoginButton(
    text: String,
    icon: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier.height(56.dp),
        shape = RoundedCornerShape(12.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE2E8F0))
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .background(Color(0xFFF1F5F9), RoundedCornerShape(4.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text(text = icon, fontWeight = FontWeight.Bold, fontSize = 12.sp, color = Color.Black)
            }
            Spacer(modifier = Modifier.width(12.dp))
            Text(text = text, color = Color(0xFF0F172A), fontWeight = FontWeight.Medium)
        }
    }
}