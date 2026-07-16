package com.hathway.pocketgoals.presentation.ui.components.proflie_components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hathway.pocketgoals.domain.model.AppLanguage
import com.hathway.pocketgoals.domain.model.ThemeMode
import com.hathway.pocketgoals.presentation.ui.theme.PocketGoalsTheme

@Composable
fun LanguageOptionItem(
    language: AppLanguage, isSelected: Boolean, onClick: () -> Unit
) {
    val borderColor = if (isSelected) MaterialTheme.colorScheme.primary else Color.Transparent
    val backgroundColor =
        if (isSelected) MaterialTheme.colorScheme.primary.copy(alpha = 0.05f) else MaterialTheme.colorScheme.surfaceVariant.copy(
            alpha = 0.3f
        )

    Row(
        modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(16.dp))
            .background(backgroundColor).then(
                if (isSelected) Modifier.border(1.dp, borderColor, RoundedCornerShape(16.dp))
                else Modifier
            ).clickable(onClick = onClick).padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier.size(48.dp).background(
                MaterialTheme.colorScheme.primary.copy(alpha = 0.1f), RoundedCornerShape(12.dp)
            ), contentAlignment = Alignment.Center
        ) {
            Text(
                text = language.symbol,
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = language.label,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = language.nativeLabel,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        RadioButton(
            selected = isSelected, onClick = null, colors = RadioButtonDefaults.colors(
                selectedColor = MaterialTheme.colorScheme.primary
            )
        )
    }
}

@Preview(name = "Languages Light Mode", showBackground = true, widthDp = 360)
@Composable
fun LanguageOptionItemLightPreview() {
    PocketGoalsTheme(themeMode = ThemeMode.LIGHT) {
        LanguageOptionsPreviewList()
    }
}

@Preview(name = "Languages Dark Mode", showBackground = true, widthDp = 360)
@Composable
fun LanguageOptionItemDarkPreview() {
    PocketGoalsTheme(themeMode = ThemeMode.DARK) {
        LanguageOptionsPreviewList()
    }
}

@Composable
private fun LanguageOptionsPreviewList() {
    // Track selected item dynamically to test the radio buttons interactive states in validation loops
    var selectedLanguage by remember { mutableStateOf(AppLanguage.ENGLISH) }

    Column(
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        AppLanguage.values().forEach { language ->
            LanguageOptionItem(
                language = language,
                isSelected = language == selectedLanguage,
                onClick = { selectedLanguage = language })
        }
    }
}

