package com.hathway.pocketgoals.di

import com.hathway.pocketgoals.data.local.database.AppDatabase
import com.hathway.pocketgoals.data.repository.*
import com.hathway.pocketgoals.domain.repository.ExpenseRepository
import com.hathway.pocketgoals.domain.repository.GoalRepository
import com.hathway.pocketgoals.domain.repository.TransactionRepository
import com.hathway.pocketgoals.domain.usecase.GetExpensesUseCase
import com.hathway.pocketgoals.presentation.ui.viewmodel.AnalyticsViewModel
import com.hathway.pocketgoals.presentation.ui.viewmodel.HomeViewModel
import com.hathway.pocketgoals.presentation.ui.viewmodel.GoalsViewModel
import com.hathway.pocketgoals.presentation.ui.viewmodel.SettingsViewModel
import com.hathway.pocketgoals.presentation.ui.viewmodel.TransactionsViewModel
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

fun initKoin(useMock: Boolean = false, appDeclaration: KoinAppDeclaration = {}) =
    startKoin {
        appDeclaration()
        modules(commonModule(useMock), platformModule)
    }

// called by iOS etc
fun initKoin() = initKoin(useMock = false) {}

fun commonModule(useMock: Boolean) = module {
    single { get<AppDatabase>().expenseDao() }
    single { get<AppDatabase>().incomeDao() }
    single { get<AppDatabase>().goalDao() }
    single { get<AppDatabase>().budgetDao() }
    single { get<AppDatabase>().accountDao() }
    single { get<AppDatabase>().categoryDao() }

    single<ExpenseRepository> { ExpenseRepositoryImpl(get()) }
    
    if (useMock) {
        single<TransactionRepository> { MockTransactionRepositoryImpl() }
        single<GoalRepository> { MockGoalRepositoryImpl() }
    } else {
        single<TransactionRepository> { RoomTransactionRepositoryImpl(get(), get()) }
        single<GoalRepository> { RoomGoalRepositoryImpl(get()) }
    }

    factory { TransactionsViewModel(get()) }
    factory { HomeViewModel(get()) }
    factory { GoalsViewModel(get()) }
    factory { AnalyticsViewModel(get()) }
    factory { SettingsViewModel() }
    factory { GetExpensesUseCase(get()) }
}

expect val platformModule: Module
