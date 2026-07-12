package com.hathway.pocketgoals.presentation.ui.components.transactions_components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hathway.pocketgoals.domain.model.ThemeMode
import com.hathway.pocketgoals.presentation.ui.theme.PocketGoalsTheme

@Composable
fun DetailRow(
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.weight(1f)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = value,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.End,
            modifier = Modifier.weight(1.5f)
        )
    }
}

data class GoalDetailsMock(
    val category: String,
    val targetAmount: String,
    val savedAmount: String,
    val targetDate: String
)

val sampleGoal = GoalDetailsMock(
    category = "Vacation Trip 2026",
    targetAmount = "$4,500.00",
    savedAmount = "$1,250.00",
    targetDate = "Dec 15, 2026"
)


@Preview(name = "Light Mode", showBackground = true)
@Composable
private fun DetailRowLightPreview() {
    PocketGoalsTheme(themeMode = ThemeMode.LIGHT) {
        Surface {
            DetailRow(
                label = "Target Amount",
                value = "$5,000.00",
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }
    }
}

@Preview(name = "Dark Mode", showBackground = true)
@Composable
private fun DetailRowDarkPreview() {
    PocketGoalsTheme(themeMode = ThemeMode.DARK) {
        Surface {
            DetailRow(
                label = "Target Amount",
                value = "$5,000.00",
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }
    }
}
