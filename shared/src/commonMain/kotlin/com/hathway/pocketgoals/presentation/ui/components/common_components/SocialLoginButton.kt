package com.hathway.pocketgoals.presentation.ui.components.common_components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.tooling.preview.Preview
import com.hathway.pocketgoals.domain.model.ThemeMode
import com.hathway.pocketgoals.presentation.ui.theme.PocketGoalsTheme

@Composable
fun SocialLoginButton(
    text: String,
    icon: String, // Kept string parameter assuming it's for an Emoji or single character fallback
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier.height(56.dp),
        shape = RoundedCornerShape(12.dp),
        // FIXED: Replaced static line color with adaptive dynamic theme outline token
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface
        )
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier.size(24.dp)
                    // FIXED: Replaced hardcoded grey with container variant that automatically matches light/dark screens
                    .background(MaterialTheme.colorScheme.surfaceVariant, RoundedCornerShape(4.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = icon, fontWeight = FontWeight.Bold, fontSize = 12.sp,
                    // FIXED: Adaptive text token to contrast against the icon box base color
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Text(
                text = text,
                // FIXED: Replaced static slate black with adaptive theme onSurface color token
                color = MaterialTheme.colorScheme.onSurface, fontWeight = FontWeight.Medium
            )
        }
    }
}


@Preview(name = "Login Light Mode", showBackground = true, widthDp = 360, heightDp = 160)
@Composable
fun SocialLoginButtonLightPreview() {
    PocketGoalsTheme(themeMode = ThemeMode.LIGHT) {
        SocialLoginButtonPreviewContainer()
    }
}

@Preview(name = "Login Dark Mode", showBackground = true, widthDp = 360, heightDp = 160)
@Composable
fun SocialLoginButtonDarkPreview() {
    PocketGoalsTheme(themeMode = ThemeMode.DARK) {
        SocialLoginButtonPreviewContainer()
    }
}

@Composable
private fun SocialLoginButtonPreviewContainer() {
    Column(
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        SocialLoginButton(
            text = "Continue with Google",
            icon = "G",
            modifier = Modifier.fillMaxWidth(),
            onClick = {})
        SocialLoginButton(
            text = "Continue with Apple",
            icon = "",
            modifier = Modifier.fillMaxWidth(),
            onClick = {})
    }
}