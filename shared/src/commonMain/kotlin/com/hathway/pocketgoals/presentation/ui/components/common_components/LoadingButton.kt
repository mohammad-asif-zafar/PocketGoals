package com.hathway.pocketgoals.presentation.ui.components.common_components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hathway.pocketgoals.domain.model.ThemeMode
import com.hathway.pocketgoals.presentation.ui.theme.PocketGoalsTheme

@Composable
fun LoadingButton(
    text: String,
    isLoading: Boolean,
    enabled: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val primaryColor = MaterialTheme.colorScheme.primary

    val animatedWidth by animateDpAsState(
        targetValue = if (isLoading) 56.dp else 320.dp,
        animationSpec = tween(durationMillis = 300),
        label = "ButtonWidthAnimation"
    )

    Box(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Button(
            onClick = onClick,
            enabled = enabled && !isLoading,
            modifier = if (isLoading) Modifier.size(width = animatedWidth, height = 56.dp) else Modifier.fillMaxWidth().height(56.dp),
            shape = RoundedCornerShape(28.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = primaryColor,
                contentColor = MaterialTheme.colorScheme.onPrimary,
                disabledContainerColor = primaryColor.copy(alpha = 0.35f),
                disabledContentColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.65f)
            )
        ) {
            // FIX: Capture the RowScope from the Button to use as an explicit receiver for AnimatedVisibility.
            // This avoids the "implicit receiver" ambiguity when nested inside another scope like Box.
            val rowScope = this
            Box(
                modifier = Modifier,
                contentAlignment = Alignment.Center
            ) {
                // Text disappears gracefully when async trigger flips
                rowScope.AnimatedVisibility(
                    visible = !isLoading,
                    enter = fadeIn(animationSpec = tween(150)),
                    exit = fadeOut(animationSpec = tween(150)),
                    modifier = Modifier, // Explicit empty modifier allocation isolates scope receivers
                    label = "ButtonTextVisibility"
                ) {
                    Text(
                        text = text,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.titleMedium
                    )
                }

                // Loading Indicator reveals natively inside button viewport boundaries
                rowScope.AnimatedVisibility(
                    visible = isLoading,
                    enter = fadeIn(animationSpec = tween(150)),
                    exit = fadeOut(animationSpec = tween(150)),
                    modifier = Modifier, // Explicit empty modifier allocation isolates scope receivers
                    label = "ButtonProgressVisibility"
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = MaterialTheme.colorScheme.onPrimary,
                        strokeWidth = 3.dp
                    )
                }
            }
        }
    }
}


@Preview(name = "Loading Button Normal - Light Theme")
@Composable
private fun LoadingButtonNormalPreview() {
    PocketGoalsTheme(themeMode = ThemeMode.LIGHT) {
        Surface(color = MaterialTheme.colorScheme.background, modifier = Modifier.padding(16.dp)) {
            LoadingButton(text = "Sign Up", isLoading = false, enabled = true, onClick = {})
        }
    }
}

@Preview(name = "Loading Button Active Spinner - Dark Theme")
@Composable
private fun LoadingButtonActiveDarkPreview() {
    PocketGoalsTheme(themeMode = ThemeMode.DARK) {
        Surface(color = MaterialTheme.colorScheme.background, modifier = Modifier.padding(16.dp)) {
            LoadingButton(text = "Sign Up", isLoading = true, enabled = true, onClick = {})
        }
    }
}