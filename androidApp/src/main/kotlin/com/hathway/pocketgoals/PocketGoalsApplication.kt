package com.hathway.pocketgoals

import android.app.Application
import com.hathway.pocketgoals.di.initKoin
import org.koin.android.ext.koin.androidContext

class PocketGoalsApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        initKoin(useMock = BuildConfig.FLAVOR == "mock") {
            androidContext(this@PocketGoalsApplication)
        }
    }
}
