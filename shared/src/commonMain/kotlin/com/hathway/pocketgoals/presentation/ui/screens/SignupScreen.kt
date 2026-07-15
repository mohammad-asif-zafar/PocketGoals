package com.hathway.pocketgoals.presentation.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hathway.pocketgoals.domain.model.ThemeMode
import com.hathway.pocketgoals.presentation.ui.components.common_components.LoadingButton
import com.hathway.pocketgoals.presentation.ui.theme.PocketGoalsTheme
import com.hathway.pocketgoals.presentation.ui.theme.Primary
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource
import pocketgoals.shared.generated.resources.Res
import pocketgoals.shared.generated.resources.signup_agree_terms
import pocketgoals.shared.generated.resources.signup_btn_submit
import pocketgoals.shared.generated.resources.signup_create_account
import pocketgoals.shared.generated.resources.signup_desc_google
import pocketgoals.shared.generated.resources.signup_divider_or
import pocketgoals.shared.generated.resources.signup_placeholder_confirm_password
import pocketgoals.shared.generated.resources.signup_placeholder_email
import pocketgoals.shared.generated.resources.signup_placeholder_fullname
import pocketgoals.shared.generated.resources.signup_placeholder_password
import pocketgoals.shared.generated.resources.signup_subtitle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignupScreen(
    onSignupSuccess: () -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var fullName by remember { mutableStateOf("Asif Zafar") }
    var email by remember { mutableStateOf("asifzafar000@yahoo.com") }
    var password by remember { mutableStateOf("Zaf@#9891g") }
    var confirmPassword by remember { mutableStateOf("Zaf@#9891g") }
    var agreeToTerms by remember { mutableStateOf(false) }

    val primaryColor = MaterialTheme.colorScheme.primary
    val borderColor = MaterialTheme.colorScheme.outline
    val placeholderColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
    val bodyTextColor = MaterialTheme.colorScheme.onSurface

    val isFormValid = fullName.isNotBlank() &&
            email.isNotBlank() &&
            password.length >= 6 &&
            password == confirmPassword &&
            agreeToTerms

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background,
        contentWindowInsets = WindowInsets(0, 0, 0, 0), // Keeps status bars color uniform
        topBar = {
            TopAppBar(
                title = { },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                            contentDescription = "Back",
                            tint = bodyTextColor
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 24.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = stringResource(Res.string.signup_create_account),
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )

            Text(
                text = stringResource(Res.string.signup_subtitle),
                style = MaterialTheme.typography.bodyMedium,
                color = placeholderColor,
                modifier = Modifier.padding(top = 8.dp)
            )

            Spacer(modifier = Modifier.height(32.dp))

            // 1. Full Name Entry Input Field Row
            OutlinedTextField(
                value = fullName,
                onValueChange = { fullName = it },
                placeholder = { Text(stringResource(Res.string.signup_placeholder_fullname), color = placeholderColor) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                singleLine = true,
                leadingIcon = { Icon(Icons.Rounded.Person, null, tint = placeholderColor) },
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = borderColor,
                    focusedBorderColor = primaryColor,
                    focusedTextColor = bodyTextColor,
                    unfocusedTextColor = bodyTextColor
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            // 2. Email / Phone Entry Input Field Row
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                placeholder = { Text(stringResource(Res.string.signup_placeholder_email), color = placeholderColor) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                singleLine = true,
                leadingIcon = { Icon(Icons.Rounded.Email, null, tint = placeholderColor) },
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = borderColor,
                    focusedBorderColor = primaryColor,
                    focusedTextColor = bodyTextColor,
                    unfocusedTextColor = bodyTextColor
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            // 3. Secure Masked Password Entry Input Field Row
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                placeholder = { Text(stringResource(Res.string.signup_placeholder_password), color = placeholderColor) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                singleLine = true,
                leadingIcon = { Icon(Icons.Rounded.Lock, null, tint = placeholderColor) },
                visualTransformation = PasswordVisualTransformation(),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = borderColor,
                    focusedBorderColor = primaryColor,
                    focusedTextColor = bodyTextColor,
                    unfocusedTextColor = bodyTextColor
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            // 4. Secure Masked Confirm Password Entry Input Field Row
            OutlinedTextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                placeholder = { Text(stringResource(Res.string.signup_placeholder_confirm_password), color = placeholderColor) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                singleLine = true,
                leadingIcon = { Icon(Icons.Rounded.Lock, null, tint = placeholderColor) },
                visualTransformation = PasswordVisualTransformation(),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = borderColor,
                    focusedBorderColor = primaryColor,
                    focusedTextColor = bodyTextColor,
                    unfocusedTextColor = bodyTextColor
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            // 5. Compliance Checkbox row agreement statement
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Checkbox(
                    checked = agreeToTerms,
                    onCheckedChange = { agreeToTerms = it },
                    colors = CheckboxDefaults.colors(
                        checkedColor = primaryColor,
                        uncheckedColor = borderColor
                    )
                )
                Text(
                    text = stringResource(Res.string.signup_agree_terms),
                    style = MaterialTheme.typography.bodySmall,
                    color = placeholderColor
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            // 6. Primary Action Confirmation Call-To-Action Button
            // ==========================================================
            // 6. Asynchronous Validation Loading Button Instance
            // ==========================================================
            var isRegisteringNetworkCall by remember { mutableStateOf(false) }
            val scope = rememberCoroutineScope()

            LoadingButton(
                text = stringResource(Res.string.signup_btn_submit),
                isLoading = isRegisteringNetworkCall,
                enabled = isFormValid,
                onClick = {
                    scope.launch {
                        isRegisteringNetworkCall = true

                        // Simulate verification api/database delay validation routines safely
                        kotlinx.coroutines.delay(2000)

                        isRegisteringNetworkCall = false
                        onSignupSuccess()
                    }
                }
            )


            Spacer(modifier = Modifier.height(32.dp))

            // ==========================================================
            // 7. Middle Decorative Section Grid Divider Node
            // ==========================================================
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                HorizontalDivider(
                    modifier = Modifier.weight(1f),
                    color = borderColor.copy(alpha = 0.4f)
                )
                Text(
                    text = stringResource(Res.string.signup_divider_or),
                    modifier = Modifier.padding(horizontal = 16.dp),
                    color = placeholderColor,
                    style = MaterialTheme.typography.bodySmall
                )
                HorizontalDivider(
                    modifier = Modifier.weight(1f),
                    color = borderColor.copy(alpha = 0.4f)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // ==========================================================
            // 8. Completed Social Oauth Register Selection Layer
            // ==========================================================
            OutlinedButton(
                onClick = { /* Launch Multiplatform Google Authentication API */ },
                modifier = Modifier.fillMaxWidth().height(56.dp),
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(1.dp, borderColor)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    // Minimal generic vector representation token placeholder for social auth icons
                    Icon(
                        imageVector = Icons.Rounded.Person,
                        contentDescription = null,
                        tint = primaryColor,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = stringResource(Res.string.signup_desc_google),
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }

            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}

// ==========================================================
// Theme-Safe Split Previews
// ==========================================================

@Preview
@Composable
fun SignupScreenLightPreview() {
    PocketGoalsTheme(themeMode = ThemeMode.LIGHT) {
        SignupScreen(onSignupSuccess = {}, onBackClick = {})
    }
}

@Preview
@Composable
fun SignupScreenDarkPreview() {
    PocketGoalsTheme(themeMode = ThemeMode.DARK) {
        SignupScreen(onSignupSuccess = {}, onBackClick = {})
    }
}
