package com.hathway.pocketgoals.di

import com.hathway.pocketgoals.data.local.database.DatabaseFactory
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module = module {
    single { DatabaseFactory().createDatabase().build() }
}
