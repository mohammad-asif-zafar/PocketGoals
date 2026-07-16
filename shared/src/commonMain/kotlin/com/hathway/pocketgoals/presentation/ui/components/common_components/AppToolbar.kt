package com.hathway.pocketgoals.presentation.ui.components.common_components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hathway.pocketgoals.domain.model.ThemeMode
import com.hathway.pocketgoals.presentation.ui.theme.PocketGoalsTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppToolbar(
    title: String,
    modifier: Modifier = Modifier,
    subtitle: String? = null,
    showNavigationIcon: Boolean = true,
    navigationIcon: ImageVector? = null,
    onNavigationClick: () -> Unit = {},
    isSearchModeActive: Boolean = false,
    searchQuery: String = "",
    searchPlaceholder: String = "Search...",
    onSearchQueryChange: (String) -> Unit = {},
    onSearchExecute: () -> Unit = {},
    actions: @Composable (RowScope.() -> Unit) = {}
) {
    Column(modifier = modifier.fillMaxWidth()) {
        TopAppBar(
            title = {
                if (isSearchModeActive) {
                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = onSearchQueryChange,
                        placeholder = {
                            Text(
                                text = searchPlaceholder,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
                            )
                        },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "Search",
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        },
                        trailingIcon = {
                            if (searchQuery.isNotEmpty()) {
                                IconButton(onClick = { onSearchQueryChange("") }) {
                                    Icon(
                                        imageVector = Icons.Default.Clear,
                                        contentDescription = "Clear input",
                                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }
                        },
                        singleLine = true,
                        shape = RoundedCornerShape(12.dp), // Matched with app rounding specs
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                        keyboardActions = KeyboardActions(onSearch = { onSearchExecute() }),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = MaterialTheme.colorScheme.onSurface,
                            unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                            focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant.copy(
                                alpha = 0.5f
                            ),
                            unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant.copy(
                                alpha = 0.3f
                            ),
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = MaterialTheme.colorScheme.outlineVariant
                        ),
                        // FIXED: Removed hardcoded height restriction to avoid cutting off input text fields
                        modifier = Modifier.fillMaxWidth().padding(end = 8.dp)
                    )
                } else {
                    Column {
                        Text(
                            text = title,
                            style = MaterialTheme.typography.titleMedium,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        if (!subtitle.isNullOrBlank()) {
                            Text(
                                text = subtitle,
                                style = MaterialTheme.typography.bodySmall,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }, navigationIcon = {
                if (showNavigationIcon && navigationIcon != null) {
                    IconButton(onClick = onNavigationClick) {
                        Icon(
                            imageVector = navigationIcon,
                            contentDescription = "Navigation item trigger",
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }, actions = actions, colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.surface,
                titleContentColor = MaterialTheme.colorScheme.onSurface,
                navigationIconContentColor = MaterialTheme.colorScheme.onSurface,
                actionIconContentColor = MaterialTheme.colorScheme.onSurfaceVariant
            )
        )

        HorizontalDivider(
            thickness = 0.5.dp, color = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
        )
    }
}

@Preview(name = "Toolbar Light Mode", showBackground = true, widthDp = 360, heightDp = 220)
@Composable
fun AppToolbarLightPreview() {
    PocketGoalsTheme(themeMode = ThemeMode.LIGHT) {
        ToolbarPreviewLayout()
    }
}

@Preview(name = "Toolbar Dark Mode", showBackground = true, widthDp = 360, heightDp = 220)
@Composable
fun AppToolbarDarkPreview() {
    PocketGoalsTheme(themeMode = ThemeMode.DARK) {
        ToolbarPreviewLayout()
    }
}

@Composable
private fun ToolbarPreviewLayout() {
    Column(
        modifier = Modifier.fillMaxSize().padding(vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // State A: Standard Layout Display
        AppToolbar(
            title = "Transactions",
            subtitle = "July 2026",
            navigationIcon = Icons.AutoMirrored.Rounded.ArrowBack,
            showNavigationIcon = true,
            isSearchModeActive = false,
            actions = {
                IconButton(onClick = {}) {
                    Icon(Icons.Rounded.MoreVert, contentDescription = "More options")
                }
            })

        Spacer(modifier = Modifier.height(8.dp))

        // State B: Active Filter Input Box Search State Display
        AppToolbar(
            title = "Transactions",
            searchQuery = "Coffee Shops",
            isSearchModeActive = true,
            navigationIcon = Icons.AutoMirrored.Rounded.ArrowBack,
            showNavigationIcon = true,
            onSearchQueryChange = {})
    }
}