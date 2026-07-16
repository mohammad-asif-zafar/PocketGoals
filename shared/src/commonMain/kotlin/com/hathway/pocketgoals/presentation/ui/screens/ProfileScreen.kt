package com.hathway.pocketgoals.presentation.ui.screens

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.Logout
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.DarkMode
import androidx.compose.material.icons.rounded.Language
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.material.icons.rounded.Palette
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.SettingsBrightness
import androidx.compose.material.icons.rounded.WbSunny
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hathway.pocketgoals.domain.model.AppLanguage
import com.hathway.pocketgoals.domain.model.ThemeMode
import com.hathway.pocketgoals.presentation.ui.components.add_expense_components.AddExpenseTopBar
import com.hathway.pocketgoals.presentation.ui.components.proflie_components.LanguageOptionItem
import com.hathway.pocketgoals.presentation.ui.components.proflie_components.ProfileOptionItem
import com.hathway.pocketgoals.presentation.ui.components.proflie_components.ThemeOptionItem
import com.hathway.pocketgoals.presentation.ui.viewmodel.SettingsViewModel
import org.jetbrains.compose.resources.stringResource
import pocketgoals.shared.generated.resources.Res
import pocketgoals.shared.generated.resources.app_language
import pocketgoals.shared.generated.resources.app_settings
import pocketgoals.shared.generated.resources.app_theme
import pocketgoals.shared.generated.resources.cancel
import pocketgoals.shared.generated.resources.choose_language
import pocketgoals.shared.generated.resources.choose_theme
import pocketgoals.shared.generated.resources.logout
import pocketgoals.shared.generated.resources.notifications
import pocketgoals.shared.generated.resources.profile
import pocketgoals.shared.generated.resources.theme_dark
import pocketgoals.shared.generated.resources.theme_light
import pocketgoals.shared.generated.resources.theme_system

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    settingsViewModel: SettingsViewModel,
    onBackClick: () -> Unit,
) {
    val themeMode by settingsViewModel.themeMode.collectAsState()
    val language by settingsViewModel.language.collectAsState()
    
    var showThemeDialog by remember { mutableStateOf(false) }
    var showLanguageDialog by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()

    Scaffold(
        topBar = {
            AddExpenseTopBar(title = stringResource(Res.string.profile), onBack = onBackClick)
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // User Header
            item {
                Column(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .size(100.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.4f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Person,
                            contentDescription = null,
                            modifier = Modifier.size(60.dp),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Asif Zafar",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Manage your account and preferences",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            // Settings Group
            item {
                Text(
                    text = stringResource(Res.string.app_settings),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }

            item {
                ProfileOptionItem(
                    icon = Icons.Rounded.Palette,
                    title = stringResource(Res.string.app_theme),
                    subtitle = when (themeMode) {
                        ThemeMode.SYSTEM -> stringResource(Res.string.theme_system)
                        ThemeMode.LIGHT -> stringResource(Res.string.theme_light)
                        ThemeMode.DARK -> stringResource(Res.string.theme_dark)
                    },
                    onClick = { showThemeDialog = true }
                )
            }

            item {
                ProfileOptionItem(
                    icon = Icons.Rounded.Language,
                    title = stringResource(Res.string.app_language),
                    subtitle = language.label,
                    onClick = { showLanguageDialog = true }
                )
            }

            item {
                ProfileOptionItem(
                    icon = Icons.Rounded.Notifications,
                    title = stringResource(Res.string.notifications),
                    subtitle = "On",
                    subtitleColor = MaterialTheme.colorScheme.primary,
                    onClick = { }
                )
            }

            item {
                Spacer(modifier = Modifier.height(24.dp))
                Button(
                    onClick = { },
                    modifier = Modifier.fillMaxWidth().height(56.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF311010),
                        contentColor = Color(0xFFEF4444)
                    ),
                    border = BorderStroke(1.dp, Color(0xFFEF4444).copy(alpha = 0.2f))
                ) {
                    Icon(Icons.AutoMirrored.Rounded.Logout, null, modifier = Modifier.size(20.dp))
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(stringResource(Res.string.logout), fontWeight = FontWeight.Bold, fontSize = 16.sp)
                }
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }

    if (showThemeDialog) {
        ModalBottomSheet(
            onDismissRequest = { showThemeDialog = false },
            sheetState = sheetState,
            dragHandle = {
                BottomSheetDefaults.DragHandle()
            },
            containerColor = MaterialTheme.colorScheme.surface,
            shape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .padding(bottom = 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Palette,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Text(
                            text = stringResource(Res.string.choose_theme),
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Select your preferred app appearance",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                ThemeOptionItem(
                    title = stringResource(Res.string.theme_system),
                    subtitle = "Follow system appearance",
                    icon = Icons.Rounded.SettingsBrightness,
                    iconColor = Color.Gray,
                    isSelected = themeMode == ThemeMode.SYSTEM,
                    onClick = { 
                        settingsViewModel.setThemeMode(ThemeMode.SYSTEM)
                        showThemeDialog = false
                    }
                )

                Spacer(modifier = Modifier.height(12.dp))

                ThemeOptionItem(
                    title = stringResource(Res.string.theme_light),
                    subtitle = "Always use light theme",
                    icon = Icons.Rounded.WbSunny,
                    iconColor = Color(0xFFF59E0B),
                    isSelected = themeMode == ThemeMode.LIGHT,
                    onClick = { 
                        settingsViewModel.setThemeMode(ThemeMode.LIGHT)
                        showThemeDialog = false
                    }
                )

                Spacer(modifier = Modifier.height(12.dp))

                ThemeOptionItem(
                    title = stringResource(Res.string.theme_dark),
                    subtitle = "Always use dark theme",
                    icon = Icons.Rounded.DarkMode,
                    iconColor = MaterialTheme.colorScheme.primary,
                    isSelected = themeMode == ThemeMode.DARK,
                    onClick = { 
                        settingsViewModel.setThemeMode(ThemeMode.DARK)
                        showThemeDialog = false
                    }
                )

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = { showThemeDialog = false },
                    modifier = Modifier.fillMaxWidth().height(56.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                        contentColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Icon(Icons.Rounded.Close, null, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(stringResource(Res.string.cancel), fontWeight = FontWeight.Bold)
                }
            }
        }
    }

    if (showLanguageDialog) {
        ModalBottomSheet(
            onDismissRequest = { showLanguageDialog = false },
            sheetState = sheetState,
            dragHandle = {
                BottomSheetDefaults.DragHandle()
            },
            containerColor = MaterialTheme.colorScheme.surface,
            shape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .padding(bottom = 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Language,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Text(
                            text = stringResource(Res.string.choose_language),
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Select your preferred language",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                AppLanguage.entries.forEach { lang ->
                    LanguageOptionItem(
                        language = lang,
                        isSelected = language == lang,
                        onClick = {
                            settingsViewModel.setLanguage(lang)
                            showLanguageDialog = false
                        }
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                }

                Spacer(modifier = Modifier.height(12.dp))

                Button(
                    onClick = { showLanguageDialog = false },
                    modifier = Modifier.fillMaxWidth().height(56.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                        contentColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Icon(Icons.Rounded.Close, null, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(stringResource(Res.string.cancel), fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}