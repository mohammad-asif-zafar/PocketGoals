package com.hathway.pocketgoals.presentation.ui.components.add_income_components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hathway.pocketgoals.domain.IncomeType
import com.hathway.pocketgoals.domain.model.ThemeMode
import com.hathway.pocketgoals.presentation.ui.theme.PocketGoalsTheme
import org.jetbrains.compose.resources.stringResource
import pocketgoals.shared.generated.resources.*

@Composable
fun IncomeTypeSelectionStep(
    selectedType: IncomeType?, onTypeSelected: (IncomeType) -> Unit, onNext: () -> Unit
) {
    val types = IncomeType.defaultTypes
    val borderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f)

    Column(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.weight(1f).padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.Top
        ) {
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                stringResource(Res.string.income_type),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier.weight(1f)
            ) {
                items(types) { type ->
                    val isSelected = type.name == selectedType?.name
                    Row(
                        modifier = Modifier.fillMaxWidth().height(72.dp)
                            .clip(RoundedCornerShape(16.dp)).background(
                                if (isSelected) type.color.copy(alpha = 0.05f)
                                else MaterialTheme.colorScheme.surface
                            ).border(
                                1.dp,
                                if (isSelected) type.color else borderColor,
                                RoundedCornerShape(16.dp)
                            ).clickable { onTypeSelected(type) }.padding(horizontal = 16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier.size(40.dp).background(
                                type.color.copy(alpha = 0.1f), RoundedCornerShape(12.dp)
                            ), contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = type.icon,
                                contentDescription = null,
                                tint = type.color,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = type.name,
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = type.description,
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        Icon(
                            imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowRight,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.outline
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
        }

        Box(modifier = Modifier.padding(24.dp)) {
            Button(
                onClick = onNext,
                enabled = selectedType != null,
                modifier = Modifier.fillMaxWidth().height(56.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(stringResource(Res.string.btn_next), fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Preview(name = "Type Selection - Light Mode")
@Composable
private fun IncomeTypeSelectionStepLightPreview() {
    PocketGoalsTheme(themeMode = ThemeMode.LIGHT) {
        Surface(color = MaterialTheme.colorScheme.background) {
            IncomeTypeSelectionStep(
                // Simulates an active selection of the "Salary" item card to test colors
                selectedType = IncomeType.defaultTypes[0], onTypeSelected = {}, onNext = {})
        }
    }
}

@Preview(name = "Type Selection - Dark Mode")
@Composable
private fun IncomeTypeSelectionStepDarkPreview() {
    PocketGoalsTheme(themeMode = ThemeMode.DARK) {
        Surface(color = MaterialTheme.colorScheme.background) {
            IncomeTypeSelectionStep(
                // Simulates an unselected state to test initial border opacity and CTA button disabled color mapping
                selectedType = null, onTypeSelected = {}, onNext = {})
        }
    }
}