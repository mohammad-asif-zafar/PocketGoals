/*
package com.hathway.pocketgoals.presentation.ui.util

import androidx.compose.ui.text.intl.Locale
import java.time.format.DateTimeFormatter
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.ExperimentalTime

object TimeUtil {

    @OptIn(ExperimentalTime::class)
    fun formatSelectedOrCurrentDate(selectedDateMillis: Long?): String {
        val instant = if (selectedDateMillis != null) {
            Instant.fromEpochMilliseconds(selectedDateMillis)
        } else {
            Clock.System.now()
        }

        val dt = instant.toLocalDateTime(TimeZone.currentSystemDefault())
        val javaDateTime = dt.toJavaLocalDateTime()
        val formatter = DateTimeFormatter.ofPattern("d MMMM yyyy", Locale.current)

        return javaDateTime.format(formatter)
    }
}
*/
