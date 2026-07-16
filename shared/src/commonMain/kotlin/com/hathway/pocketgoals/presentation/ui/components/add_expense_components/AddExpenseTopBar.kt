package com.hathway.pocketgoals.presentation.ui.components.add_expense_components

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.stringResource
import pocketgoals.shared.generated.resources.Res
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.tooling.preview.Preview
import com.hathway.pocketgoals.domain.model.ThemeMode
import com.hathway.pocketgoals.presentation.ui.theme.PocketGoalsTheme
import pocketgoals.shared.generated.resources.delete_category
import pocketgoals.shared.generated.resources.go_back_desc

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddExpenseTopBar(
    title: String, onBack: () -> Unit, modifier: Modifier = Modifier, onDelete: (() -> Unit)? = null
) {
    TopAppBar(
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
        }, navigationIcon = {
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                    contentDescription = stringResource(Res.string.go_back_desc),
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }
        }, actions = {
            if (onDelete != null) {
                IconButton(onClick = onDelete) {
                    Icon(
                        imageVector = Icons.Rounded.Delete,
                        contentDescription = stringResource(Res.string.delete_category),
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            }
        }, colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.background
        ), windowInsets = WindowInsets(top = 0.dp), modifier = modifier
    )
}


@Preview(name = "TopBar Light Mode", showBackground = true, widthDp = 360, heightDp = 160)
@Composable
fun AddExpenseTopBarLightPreview() {
    PocketGoalsTheme(themeMode = ThemeMode.LIGHT) {
        TopBarPreviewContainer()
    }
}

@Preview(name = "TopBar Dark Mode", showBackground = true, widthDp = 360, heightDp = 160)
@Composable
fun AddExpenseTopBarDarkPreview() {
    PocketGoalsTheme(themeMode = ThemeMode.DARK) {
        TopBarPreviewContainer()
    }
}

@Composable
private fun TopBarPreviewContainer() {
    Column(
        modifier = Modifier.fillMaxSize().padding(vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // State 1: Add Mode (Delete action is hidden)
        AddExpenseTopBar(
            title = "Add Expense", onBack = {}, onDelete = null
        )

        // State 2: Edit Mode (Delete action is present)
        AddExpenseTopBar(title = "Edit Expense", onBack = {}, onDelete = {})
    }
}
