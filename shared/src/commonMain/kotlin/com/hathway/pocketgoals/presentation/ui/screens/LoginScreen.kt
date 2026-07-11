package com.hathway.pocketgoals.presentation.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hathway.pocketgoals.domain.model.ThemeMode
import com.hathway.pocketgoals.presentation.ui.theme.PocketGoalsTheme
import org.jetbrains.compose.resources.stringResource
import pocketgoals.shared.generated.resources.Res
import pocketgoals.shared.generated.resources.btn_forgot_password
import pocketgoals.shared.generated.resources.btn_login
import pocketgoals.shared.generated.resources.btn_sign_up
import pocketgoals.shared.generated.resources.hint_email
import pocketgoals.shared.generated.resources.hint_password
import pocketgoals.shared.generated.resources.login_subtitle
import pocketgoals.shared.generated.resources.login_welcome
import pocketgoals.shared.generated.resources.text_dont_have_account
import pocketgoals.shared.generated.resources.text_or_continue

@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    onSignupClick: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    val scrollState = rememberScrollState()
    val isFormValid = email.isNotBlank() && password.length >= 6

    val primaryColor = MaterialTheme.colorScheme.primary
    val borderColor = MaterialTheme.colorScheme.outline
    val labelTextColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
    val bodyTextColor = MaterialTheme.colorScheme.onSurface

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        // Using bottomBar pins the footer cleanly without weight-based infinite layout calculations
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .windowInsetsPadding(WindowInsets.safeDrawing) // Safe area for iOS Home Indicator / Android Nav Bar
                    .padding(bottom = 24.dp, top = 8.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(Res.string.text_dont_have_account),
                    color = labelTextColor
                )
                TextButton(onClick = onSignupClick) {
                    Text(
                        text = stringResource(Res.string.btn_sign_up),
                        color = primaryColor,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(scrollState)
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = stringResource(Res.string.login_welcome),
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )

            Text(
                text = stringResource(Res.string.login_subtitle),
                style = MaterialTheme.typography.bodyMedium,
                color = labelTextColor,
                modifier = Modifier.padding(top = 8.dp)
            )

            Spacer(modifier = Modifier.height(40.dp))

            // Email Input Field
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                placeholder = { Text(stringResource(Res.string.hint_email), color = labelTextColor) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                singleLine = true,
                leadingIcon = { Icon(Icons.Rounded.Email, null, tint = labelTextColor) },
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = borderColor,
                    focusedBorderColor = primaryColor,
                    focusedTextColor = bodyTextColor,
                    unfocusedTextColor = bodyTextColor
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Password Input Field
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                placeholder = { Text(stringResource(Res.string.hint_password), color = labelTextColor) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                singleLine = true,
                leadingIcon = { Icon(Icons.Rounded.Lock, null, tint = labelTextColor) },
                trailingIcon = {
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(
                            imageVector = if (passwordVisible) Icons.Rounded.VisibilityOff else Icons.Rounded.Visibility,
                            contentDescription = null,
                            tint = labelTextColor
                        )
                    }
                },
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = borderColor,
                    focusedBorderColor = primaryColor,
                    focusedTextColor = bodyTextColor,
                    unfocusedTextColor = bodyTextColor
                )
            )

            // Forgot Password (Mirrors dynamically to top-left in Arabic & Urdu layouts)
            TextButton(
                onClick = { /* Handle forgot password logic */ },
                modifier = Modifier.align(Alignment.End)
            ) {
                Text(
                    text = stringResource(Res.string.btn_forgot_password),
                    color = primaryColor,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Primary Call to Action Button
            Button(
                onClick = onLoginSuccess,
                enabled = isFormValid,
                modifier = Modifier.fillMaxWidth().height(56.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = primaryColor,
                    contentColor = MaterialTheme.colorScheme.onPrimary, // Fixed: High visibility text setup
                    disabledContainerColor = primaryColor.copy(alpha = 0.35f),
                    disabledContentColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.65f)
                )
            ) {
                Text(
                    text = stringResource(Res.string.btn_login),
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Divider Section
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                HorizontalDivider(modifier = Modifier.weight(1f), color = borderColor)
                Text(
                    text = stringResource(Res.string.text_or_continue),
                    modifier = Modifier.padding(horizontal = 16.dp),
                    color = labelTextColor,
                    style = MaterialTheme.typography.bodySmall
                )
                HorizontalDivider(modifier = Modifier.weight(1f), color = borderColor)
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Social Providers Block
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                SocialLoginButton(
                    text = "Google",
                    iconLabel = "G",
                    modifier = Modifier.weight(1f),
                    onClick = { /* Handle Google login */ }
                )
                SocialLoginButton(
                    text = "Apple",
                    iconLabel = "A",
                    modifier = Modifier.weight(1f),
                    onClick = { /* Handle Apple login */ }
                )
            }

            // Fixed: Secure constant height space eliminates infinite sizing crashes inside layouts
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}
// Inline Social Button subcomponent for stand-alone layout parsing setup
@Composable
fun SocialLoginButton(
    text: String,
    iconLabel: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .height(52.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.surface)
            .border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(12.dp))
            .clickable { onClick() }
            .padding(horizontal = 16.dp),
        // Fix: Use spacedBy to automatically handle padding orientation for RTL/LTR scripts
        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = iconLabel,
            fontWeight = FontWeight.Black,
            fontSize = 18.sp,
            color = MaterialTheme.colorScheme.onSurface
            // Fix: Absolute trailing padding removed to prevent layout clipping
        )
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

// ==========================================================
// Theme-Safe Split Previews
// ==========================================================

@Preview
@Composable
fun LoginScreenLightPreview() {
    PocketGoalsTheme(themeMode = ThemeMode.LIGHT) {
        LoginScreen(onLoginSuccess = {}, onSignupClick = {})
    }
}

@Preview
@Composable
fun LoginScreenDarkPreview() {
    PocketGoalsTheme(themeMode = ThemeMode.DARK) {
        LoginScreen(onLoginSuccess = {}, onSignupClick = {})
    }
}

