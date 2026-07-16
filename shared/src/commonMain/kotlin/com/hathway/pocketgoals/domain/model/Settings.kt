package com.hathway.pocketgoals.domain.model

enum class ThemeMode {
    SYSTEM, LIGHT, DARK
}

enum class AppLanguage(
    val code: String, 
    val label: String, 
    val nativeLabel: String, 
    val symbol: String
) {
    ENGLISH("en", "English", "English", "A"),
    HINDI("hi", "Hindi", "हिन्दी", "अ"),
    MALAY("ms", "Malay", "Bahasa Melayu", "M"),
    URDU("ur", "Urdu", "اردو", "ا"),
    ARABIC("ar", "Arabic", "العربية", "ع")
}
