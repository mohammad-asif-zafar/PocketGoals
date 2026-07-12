package com.hathway.pocketgoals.presentation.ui.screens

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hathway.pocketgoals.domain.model.ThemeMode
import com.hathway.pocketgoals.presentation.ui.theme.PocketGoalsTheme
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import pocketgoals.shared.generated.resources.Res
import pocketgoals.shared.generated.resources.app_name
import pocketgoals.shared.generated.resources.splash

@Composable
fun SplashScreen(onSplashFinished: () -> Unit) {
    val scale = remember { Animatable(0f) }
    val alpha = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        scale.animateTo(
            targetValue = 1f,
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessLow
            )
        )
        alpha.animateTo(1f, animationSpec = tween(1000))
        delay(2000)
        onSplashFinished()
    }

    val gradientBrush = Brush.verticalGradient(
        colors = listOf(
            Color(0xFF07332F),
            Color(0xFF0F766E),
            Color(0xFF2DD4BF)
        )
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(gradientBrush),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(Res.drawable.splash),
            contentDescription = stringResource(Res.string.app_name),
            modifier = Modifier
                .fillMaxSize(), // Removed generic padding restrictions here
            contentScale = ContentScale.Crop
        )

        // Footer message - Safely padded using system navigation bars inset space
        Text(
            text = "Take control of your money,\nbuild a better future.",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodySmall,
            color = Color.White.copy(alpha = 0.6f),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .windowInsetsPadding(WindowInsets.navigationBars) // Ensures text doesn't hide behind system navbar gesture bar
                .padding(bottom = 32.dp)
                .alpha(alpha.value)
        )
    }
}

// ==========================================================
// Theme-Safe Split Previews
// ==========================================================

@Preview
@Composable
fun SplashScreenLightPreview() {
    PocketGoalsTheme(themeMode = ThemeMode.LIGHT) {
        SplashScreen(onSplashFinished = {})
    }
}

@Preview
@Composable
fun SplashScreenDarkPreview() {
    PocketGoalsTheme(themeMode = ThemeMode.DARK) {
        SplashScreen(onSplashFinished = {})
    }
}
