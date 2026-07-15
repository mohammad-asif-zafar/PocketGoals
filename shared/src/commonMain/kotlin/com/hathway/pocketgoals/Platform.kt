package com.hathway.pocketgoals

import androidx.compose.runtime.Composable

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform

@Composable
expect fun rememberBiometricAuthenticator(): BiometricAuthenticator

interface BiometricAuthenticator {
    /**
     * Checks if the host device has physical fingerprint hardware or face scanning sensors configured.
     */
    fun isBiometricHardwareAvailable(): Boolean

    /**
     * Spawns the system-native biometric authentication security modal sheet.
     */
    fun promptBiometricAuth(
        title: String,
        reason: String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    )
}