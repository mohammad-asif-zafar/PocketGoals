package com.hathway.pocketgoals.presentation.ui.components.common_components

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

class CurrencyVisualTransformation(private val currencySymbol: String = "₹") :
    VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val prefix = "$currencySymbol "
        val out = prefix + text.text

        val offsetMapping = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                return offset + prefix.length
            }

            override fun transformedToOriginal(offset: Int): Int {
                if (offset < prefix.length) return 0
                return offset - prefix.length
            }
        }

        return TransformedText(AnnotatedString(out), offsetMapping)
    }
}