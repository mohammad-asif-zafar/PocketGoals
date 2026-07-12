package com.hathway.pocketgoals.presentation.ui.components.analytics_components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hathway.pocketgoals.domain.model.ThemeMode
import com.hathway.pocketgoals.presentation.ui.theme.PocketGoalsTheme
import org.jetbrains.compose.resources.stringResource
import pocketgoals.shared.generated.resources.Res
import pocketgoals.shared.generated.resources.filter_apply
import pocketgoals.shared.generated.resources.filter_date_range
import pocketgoals.shared.generated.resources.filter_end_date
import pocketgoals.shared.generated.resources.filter_start_date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateRangeFilterSheet(
    onDismiss: () -> Unit,
    onDatesSelected: (startDate: String, endDate: String) -> Unit,
    modifier: Modifier = Modifier
) {
    // Shared structural layout states
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var startDateText by remember { mutableStateOf("12/06/2026") }
    var endDateText by remember { mutableStateOf("12/07/2026") }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.surface
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
                .padding(start = 24.dp, end = 24.dp, bottom = 40.dp, top = 8.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Text(
                text = stringResource(Res.string.filter_date_range),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            // Date Range Selection Input Row Cards
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Start Date Component
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = stringResource(Res.string.filter_start_date),
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(bottom = 6.dp)
                    )
                    DateSelectorPill(
                        date = startDateText,
                        onClick = { /* Hook native system calendar view picker */ })
                }

                // End Date Component
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = stringResource(Res.string.filter_end_date),
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(bottom = 6.dp)
                    )
                    DateSelectorPill(
                        date = endDateText,
                        onClick = { /* Hook native system calendar view picker */ })
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Action Apply Filter Button Layout Control
            Button(
                onClick = {
                    onDatesSelected(startDateText, endDateText)
                    onDismiss()
                },
                modifier = Modifier.fillMaxWidth().height(56.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Text(text = stringResource(Res.string.filter_apply), fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
private fun DateSelectorPill(
    date: String, onClick: () -> Unit, modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.fillMaxWidth().height(52.dp).clip(RoundedCornerShape(12.dp))
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
        color = MaterialTheme.colorScheme.surfaceVariant
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = date,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Icon(
                imageVector = Icons.Rounded.DateRange,
                contentDescription = null,
                modifier = Modifier.size(20.dp),
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Preview(name = "Filter Sheet - Light Theme")
@Composable
private fun FilterSheetLightPreview() {
    PocketGoalsTheme(themeMode = ThemeMode.LIGHT) {
        DateRangeFilterSheet(onDismiss = {}, onDatesSelected = { _, _ -> })
    }
}

@Preview(name = "Filter Sheet - Dark Theme")
@Composable
private fun FilterSheetDarkPreview() {
    PocketGoalsTheme(themeMode = ThemeMode.DARK) {
        DateRangeFilterSheet(onDismiss = {}, onDatesSelected = { _, _ -> })
    }
}