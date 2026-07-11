package com.hathway.pocketgoals.presentation.ui.screens

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hathway.pocketgoals.domain.model.ThemeMode
import com.hathway.pocketgoals.presentation.ui.theme.PocketGoalsTheme
import com.hathway.pocketgoals.presentation.ui.theme.Primary
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import pocketgoals.shared.generated.resources.Res
import pocketgoals.shared.generated.resources.btn_get_started
import pocketgoals.shared.generated.resources.btn_next
import pocketgoals.shared.generated.resources.btn_skip
import pocketgoals.shared.generated.resources.desc_achieve_goals
import pocketgoals.shared.generated.resources.desc_save_money
import pocketgoals.shared.generated.resources.desc_track_expenses
import pocketgoals.shared.generated.resources.page_first
import pocketgoals.shared.generated.resources.page_second
import pocketgoals.shared.generated.resources.page_third
import pocketgoals.shared.generated.resources.title_achieve_goals
import pocketgoals.shared.generated.resources.title_save_money
import pocketgoals.shared.generated.resources.title_track_expenses

// Adjust class definition to accommodate vector graphic checks
data class OnboardingPage(
    val title: String,
    val description: String,
    val image: DrawableResource,
    val color: Color,
    val useIllustration: Boolean = false
)

@Composable
fun OnboardingScreen(
    onFinished: () -> Unit, onLoginClick: () -> Unit
) {
    // Build page data dynamically using localisable string resources
    val pages = listOf(
        OnboardingPage(
            title = stringResource(Res.string.title_track_expenses),
            description = stringResource(Res.string.desc_track_expenses),
            image = Res.drawable.page_first,
            color = Primary,
            useIllustration = true
        ), OnboardingPage(
            title = stringResource(Res.string.title_save_money),
            description = stringResource(Res.string.desc_save_money),
            image = Res.drawable.page_second,
            color = Primary,
            useIllustration = true
        ), OnboardingPage(
            title = stringResource(Res.string.title_achieve_goals),
            description = stringResource(Res.string.desc_achieve_goals),
            image = Res.drawable.page_third,
            color = Primary,
            useIllustration = true
        )
    )

    val pagerState = rememberPagerState(pageCount = { pages.size })
    val scope = rememberCoroutineScope()

    val primaryColor = MaterialTheme.colorScheme.primary
    val bodyTextColor = MaterialTheme.colorScheme.onSurfaceVariant

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background, topBar = {
            Row(
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                // Arrangement.End naturally shifts to Left in RTL languages (Arabic/Urdu)
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(onClick = onFinished) {
                    Text(
                        text = stringResource(Res.string.btn_skip), color = bodyTextColor
                    )
                }
            }
        }) { padding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(padding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            HorizontalPager(
                state = pagerState, modifier = Modifier.weight(1f)
            ) { index -> // 1. Fixed parameter name to resolve compiler error
                val currentPageData = pages[index]

                OnboardingPageContent(
                    page = currentPageData // 2. Clean call passing the local data class model
                )
            }

            // Indicator Dots Track
            Row(
                modifier = Modifier.padding(vertical = 32.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                repeat(pages.size) { iteration ->
                    val isActive = pagerState.currentPage == iteration
                    val color =
                        if (isActive) primaryColor else MaterialTheme.colorScheme.outlineVariant

                    // Added smooth animation for indicator widths
                    val width = animateDpAsState(targetValue = if (isActive) 24.dp else 8.dp)

                    Box(
                        modifier = Modifier.padding(horizontal = 4.dp).clip(CircleShape)
                            .background(color).size(width.value, 8.dp)
                    )
                }
            }

            // Bottom Buttons Panel
            Column(
                modifier = Modifier.padding(horizontal = 24.dp).padding(bottom = 32.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    onClick = {
                        if (pagerState.currentPage < pages.size - 1) {
                            scope.launch { pagerState.animateScrollToPage(pagerState.currentPage + 1) }
                        } else {
                            onFinished()
                        }
                    },
                    modifier = Modifier.fillMaxWidth().height(56.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = primaryColor,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    )
                ) {
                    Text(
                        text = if (pagerState.currentPage == pages.size - 1) {
                            stringResource(Res.string.btn_get_started)
                        } else {
                            stringResource(Res.string.btn_next)
                        }, fontWeight = FontWeight.Bold, fontSize = 16.sp
                    )
                }
            }
        }
    }
}

@Composable
fun OnboardingPageContent(page: OnboardingPage) {
    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (page.useIllustration) {
            // Renders your crisp onboarding device illustration layout graphics safely
            Image(
                painter = painterResource(page.image), // Replace with your exact graphic name if different
                contentDescription = page.title, modifier = Modifier.size(260.dp).padding(12.dp)
            )
        } else {
            // Fallback layout circle frame for remaining steps
            Box(
                modifier = Modifier.size(240.dp).clip(CircleShape)
                    .background(page.color.copy(alpha = 0.12f)), contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(page.image),
                    contentDescription = null,
                    modifier = Modifier.size(250.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(48.dp))

        Text(
            text = page.title,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = page.description,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
            lineHeight = 24.sp
        )
    }
}

// ==========================================================
// Theme-Safe Split Previews
// ==========================================================

@Preview
@Composable
fun OnboardingScreenLightPreview() {
    PocketGoalsTheme(themeMode = ThemeMode.LIGHT) {
        OnboardingScreen(onFinished = {}, onLoginClick = {})
    }
}

@Preview
@Composable
fun OnboardingScreenDarkPreview() {
    PocketGoalsTheme(themeMode = ThemeMode.DARK) {
        OnboardingScreen(onFinished = {}, onLoginClick = {})
    }
}
