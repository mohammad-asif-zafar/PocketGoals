package com.hathway.pocketgoals.presentation.ui.components.goals_components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hathway.pocketgoals.presentation.ui.components.add_expense_components.FormField
import com.hathway.pocketgoals.presentation.ui.theme.PocketGoalsTheme
import org.jetbrains.compose.resources.stringResource
import pocketgoals.shared.generated.resources.Res
import pocketgoals.shared.generated.resources.add_description
import pocketgoals.shared.generated.resources.btn_next
import pocketgoals.shared.generated.resources.description_label
import pocketgoals.shared.generated.resources.description_placeholder
import pocketgoals.shared.generated.resources.motivation_label
import pocketgoals.shared.generated.resources.motivation_placeholder

@Preview(name = "Light Theme", showBackground = true)
@Preview(name = "Dark Theme", showBackground = false)
annotation class ThemePreviews

@Preview(name = "English", locale = "en")
@Preview(name = "Hindi", locale = "hi")
@Preview(name = "Urdu", locale = "ur")
@Preview(name = "Malay", locale = "ms")
@Preview(name = "Arabic", locale = "ar")
annotation class LocalePreviews

// --- COMPOSABLE COMPONENT ---
@Composable
fun CustomGoalMotivationStep(
    description: String,
    onDescriptionChange: (String) -> Unit,
    motivation: String,
    onMotivationChange: (String) -> Unit,
    onNext: () -> Unit
) {
    val scrollState = rememberScrollState()

    val borderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
    val activeBorderColor = MaterialTheme.colorScheme.primary
    val placeholderColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
    val textColor = MaterialTheme.colorScheme.onSurface

    Column(
        modifier = Modifier.fillMaxSize()
            .background(MaterialTheme.colorScheme.background) // Prevents blank background in dark mode previews
    ) {
        Column(
            modifier = Modifier.weight(1f).verticalScroll(scrollState).padding(horizontal = 24.dp)
        ) {
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = stringResource(Res.string.add_description),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            FormField(label = stringResource(Res.string.description_label)) {
                OutlinedTextField(
                    value = description,
                    onValueChange = onDescriptionChange,
                    placeholder = {
                        Text(
                            stringResource(Res.string.description_placeholder),
                            color = placeholderColor
                        )
                    },
                    modifier = Modifier.fillMaxWidth().height(120.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = activeBorderColor,
                        unfocusedBorderColor = borderColor,
                        focusedTextColor = textColor,
                        unfocusedTextColor = textColor
                    )
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            FormField(label = stringResource(Res.string.motivation_label)) {
                OutlinedTextField(
                    value = motivation,
                    onValueChange = onMotivationChange,
                    placeholder = {
                        Text(
                            stringResource(Res.string.motivation_placeholder),
                            color = placeholderColor
                        )
                    },
                    modifier = Modifier.fillMaxWidth().height(120.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = activeBorderColor,
                        unfocusedBorderColor = borderColor,
                        focusedTextColor = textColor,
                        unfocusedTextColor = textColor
                    )
                )
            }
            Spacer(modifier = Modifier.height(24.dp))
        }

        Box(modifier = Modifier.padding(24.dp)) {
            Button(
                onClick = onNext,
                modifier = Modifier.fillMaxWidth().height(56.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = activeBorderColor,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                )
            ) {
                Text(stringResource(Res.string.btn_next), fontWeight = FontWeight.Bold)
            }
        }
    }
}

// --- PREVIEW IMPLEMENTATIONS ---

@ThemePreviews
@LocalePreviews
@Composable
fun CustomGoalMotivationStepPreview() {
    PocketGoalsTheme {
        Surface {
            CustomGoalMotivationStep(
                description = "",
                onDescriptionChange = {},
                motivation = "",
                onMotivationChange = {},
                onNext = {})
        }
    }
}
