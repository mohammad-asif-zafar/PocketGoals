package com.hathway.pocketgoals.presentation.ui.components.add_expense_components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.Backspace
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hathway.pocketgoals.domain.model.ThemeMode
import com.hathway.pocketgoals.presentation.ui.theme.PocketGoalsTheme

@Composable
fun CustomNumberPad(
    modifier: Modifier = Modifier,
    onNumberClick: (String) -> Unit,
    onDelete: () -> Unit,
    onDone: () -> Unit
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        shadowElevation = 16.dp,
        color = MaterialTheme.colorScheme.surface
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            val numbers = listOf(
                listOf("1", "2", "3"),
                listOf("4", "5", "6"),
                listOf("7", "8", "9"),
                listOf(".", "0", "DEL")
            )

            numbers.forEach { row ->
                Row(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    row.forEach { char ->
                        Box(
                            modifier = Modifier.size(64.dp).clip(RoundedCornerShape(16.dp))
                                .clickable {
                                    when (char) {
                                        "DEL" -> onDelete()
                                        else -> onNumberClick(char)
                                    }
                                }, contentAlignment = Alignment.Center
                        ) {
                            if (char == "DEL") {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Rounded.Backspace,
                                    contentDescription = "Delete",
                                    tint = MaterialTheme.colorScheme.onSurface
                                )
                            } else {
                                Text(
                                    text = char,
                                    fontSize = 24.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }
                    }
                }
            }

            Button(
                onClick = onDone,
                modifier = Modifier.fillMaxWidth().height(56.dp).padding(top = 8.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                )
            ) {
                Text("Next", fontWeight = FontWeight.Bold)
            }
        }
    }
}

// ==========================================================
// Theme-Safe Split Previews
// ==========================================================

@Preview
@Composable
fun CustomNumberPadLightPreview() {
    PocketGoalsTheme(themeMode = ThemeMode.LIGHT) {
        CustomNumberPad(onNumberClick = {}, onDelete = {}, onDone = {})
    }
}

@Preview
@Composable
fun CustomNumberPadDarkPreview() {
    PocketGoalsTheme(themeMode = ThemeMode.DARK) {
        CustomNumberPad(onNumberClick = {}, onDelete = {}, onDone = {})
    }
}