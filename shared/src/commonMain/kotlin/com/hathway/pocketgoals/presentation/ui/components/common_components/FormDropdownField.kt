package com.hathway.pocketgoals.presentation.ui.components.common_components


import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.spring
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.hathway.pocketgoals.domain.model.ThemeMode
import com.hathway.pocketgoals.presentation.ui.components.add_expense_components.FormField
import com.hathway.pocketgoals.presentation.ui.theme.PocketGoalsTheme
import org.jetbrains.compose.resources.stringResource
import pocketgoals.shared.generated.resources.Res
import pocketgoals.shared.generated.resources.cd_toggle_dropdown_menu
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormDropdownField(
    label: String,
    selectedValue: String,
    options: List<String>,
    onOptionSelected: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "Select an option",
    errorMessage: String? = null
) {
    var expanded by remember { mutableStateOf(false) }

    FormField(
        label = label, modifier = modifier, errorMessage = errorMessage
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            OutlinedTextField(
                value = selectedValue,
                onValueChange = {}, // Read-only component layout input handling
                readOnly = true,
                placeholder = { Text(placeholder) },
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Rounded.KeyboardArrowDown,
                        contentDescription = stringResource(Res.string.cd_toggle_dropdown_menu)

                    )
                },
                isError = errorMessage != null,
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(12.dp))
                    .clickable { expanded = true },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.outline
                )
            )

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.fillMaxWidth(0.9f) // Forces alignment scale symmetry ratios
            ) {
                options.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(text = option, style = MaterialTheme.typography.bodyMedium) },
                        onClick = {
                            onOptionSelected(option)
                            expanded = false
                        })
                }
            }
        }
    }
}


@Preview(name = "Form Dropdown Module - Light Theme")
@Composable
private fun FormDropdownFieldLightPreview() {
    val optionsList = listOf("Cash", "Credit Card", "UPI", "Bank Transfer")
    var mockSelection by remember { mutableStateOf("UPI") }

    PocketGoalsTheme(themeMode = ThemeMode.LIGHT) {
        Surface(color = MaterialTheme.colorScheme.background, modifier = Modifier.padding(16.dp)) {
            FormDropdownField(
                label = "Payment Instrument Method",
                selectedValue = mockSelection,
                options = optionsList,
                onOptionSelected = { mockSelection = it })
        }
    }
}



fun Modifier.shake(trigger: Any?): Modifier = composed {
    val anim = remember { Animatable(0f) }

    LaunchedEffect(trigger) {
        if (trigger != null) {
            val shakeIntensity = 15f
            // Rapid decay oscillation path sequence
            anim.animateTo(targetValue = shakeIntensity, animationSpec = spring(stiffness = 2000f))
            anim.animateTo(targetValue = -shakeIntensity, animationSpec = spring(stiffness = 2000f))
            anim.animateTo(targetValue = shakeIntensity / 2, animationSpec = spring(stiffness = 2000f))
            anim.animateTo(targetValue = -shakeIntensity / 2, animationSpec = spring(stiffness = 2000f))
            anim.animateTo(targetValue = 0f, animationSpec = spring(stiffness = 2000f))
        }
    }

    this.offset { IntOffset(x = anim.value.roundToInt(), y = 0) }
}

@Preview(name = "Form Dropdown Module - Dark Theme")
@Composable
private fun FormDropdownFieldDarkPreview() {
    val optionsList = listOf("Cash", "Credit Card", "UPI", "Bank Transfer")

    PocketGoalsTheme(themeMode = ThemeMode.DARK) {
        Surface(color = MaterialTheme.colorScheme.background, modifier = Modifier.padding(16.dp)) {
            Column {
                FormDropdownField(
                    label = "Payment Instrument Method",
                    selectedValue = "", // Unselected State showing placeholder fallback text blocks
                    options = optionsList,
                    onOptionSelected = {},
                    errorMessage = "Please choose a payment method to proceed" // Triggered layout validation crash state text
                )
            }
        }
    }
}
