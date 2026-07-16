package com.hathway.pocketgoals.presentation.ui.components.add_expense_components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import androidx.compose.ui.tooling.preview.Preview
import com.hathway.pocketgoals.domain.model.ThemeMode
import com.hathway.pocketgoals.presentation.ui.theme.PocketGoalsTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddExpenseAppBar(
    onBackClick: () -> Unit, modifier: Modifier = Modifier
) {
    TopAppBar(
        title = {
            Text(
                text = "Add Expense",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
        }, navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Go back"
                )
            }
        }, colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surface,
            navigationIconContentColor = MaterialTheme.colorScheme.onSurface,
            titleContentColor = MaterialTheme.colorScheme.onSurface
        ), modifier = modifier
    )
}


@Preview(showBackground = true, widthDp = 360)
@Composable
fun AddExpenseAppBarLightPreview() {
    PocketGoalsTheme(themeMode = ThemeMode.LIGHT) {
        AddExpenseAppBar(onBackClick = {})
    }
}

@Preview(name = "AppBar Dark Mode", showBackground = true, widthDp = 360)
@Composable
fun AddExpenseAppBarDarkPreview() {
    PocketGoalsTheme(themeMode = ThemeMode.DARK) {
        AddExpenseAppBar(onBackClick = {})
    }
}