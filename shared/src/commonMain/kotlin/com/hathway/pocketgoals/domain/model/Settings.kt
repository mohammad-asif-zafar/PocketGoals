package com.hathway.pocketgoals.domain.model

enum class ThemeMode {
    SYSTEM, LIGHT, DARK
}

enum class AppLanguage(val code: String, val label: String) {
    ENGLISH("en", "English"),
    HINDI("hi", "Hindi"),
    SPANISH("es", "Spanish"),
    FRENCH("fr", "French")
}