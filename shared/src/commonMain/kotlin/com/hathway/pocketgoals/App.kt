package com.hathway.pocketgoals

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.hathway.pocketgoals.presentation.ui.navigation.NavigationContainer

@Composable
@Preview
fun App() {
    MaterialTheme {
        NavigationContainer()
    }
}