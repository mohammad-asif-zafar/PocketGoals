package com.hathway.pocketgoals

import android.os.Build
import androidx.compose.runtime.Composable

class AndroidPlatform : Platform {
    override val name: String = "Android ${Build.VERSION.SDK_INT}"
}

actual fun getPlatform(): Platform = AndroidPlatform()

@Composable
actual fun rememberBiometricAuthenticator(): BiometricAuthenticator {
    TODO("Not yet implemented")
}