package com.hathway.pocketgoals.presentation.ui.components.transactions_components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Surface
import androidx.compose.ui.tooling.preview.Preview
import com.hathway.pocketgoals.domain.model.ThemeMode
import com.hathway.pocketgoals.presentation.ui.theme.PocketGoalsTheme
import org.jetbrains.compose.resources.stringResource
import pocketgoals.shared.generated.resources.Res
import pocketgoals.shared.generated.resources.section_categories
import pocketgoals.shared.generated.resources.section_recent_transactions

@Composable
fun SectionHeader(
    title: String, modifier: Modifier = Modifier
) {
    Text(
        text = title,
        style = MaterialTheme.typography.labelLarge.copy(
            letterSpacing = 0.5.sp // Cleaner spacing for uppercase/bold group headers
        ),
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        fontWeight = FontWeight.Bold,
        modifier = modifier.padding(vertical = 12.dp)
    )
}


@Preview
@Composable
private fun SectionHeaderLightPreview() {
    PocketGoalsTheme(themeMode = ThemeMode.LIGHT) {
        Surface {
            Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)) {
                SectionHeader(title = stringResource(Res.string.section_recent_transactions))
            }
        }
    }
}

@Preview
@Composable
private fun SectionHeaderDarkPreview() {
    PocketGoalsTheme(themeMode = ThemeMode.DARK) {
        Surface {
            Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)) {
                SectionHeader(title = stringResource(Res.string.section_categories))
            }
        }
    }
}
