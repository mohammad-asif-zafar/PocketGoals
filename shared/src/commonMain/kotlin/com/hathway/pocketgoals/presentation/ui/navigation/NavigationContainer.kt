package com.hathway.pocketgoals.presentation.ui.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.hathway.pocketgoals.presentation.ui.components.common_components.AppBottomBar
import com.hathway.pocketgoals.presentation.ui.screens.*
import com.hathway.pocketgoals.presentation.ui.viewmodel.AnalyticsViewModel
import com.hathway.pocketgoals.presentation.ui.viewmodel.GoalsViewModel
import com.hathway.pocketgoals.presentation.ui.viewmodel.SettingsViewModel
import com.hathway.pocketgoals.presentation.ui.viewmodel.TransactionsViewModel

@Composable
fun NavigationContainer(
    settingsViewModel: SettingsViewModel = viewModel(),
    goalsViewModel: GoalsViewModel = viewModel(),
    transactionsViewModel: TransactionsViewModel = viewModel()
) {
    val navController: NavHostController = rememberNavController()

    // Tracks the destination instead of the raw string route
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            // Hide bottom bar on auth and splash screens
            val hideBottomBar = currentDestination?.route?.contains("Splash") == true ||
                    currentDestination?.route?.contains("Onboarding") == true ||
                    currentDestination?.route?.contains("Login") == true ||
                    currentDestination?.route?.contains("Signup") == true ||
                    currentDestination?.route?.contains("AddExpense") == true ||
                    currentDestination?.route?.contains("AddIncome") == true ||
                    currentDestination?.route?.contains("AddGoal") == true ||
                    currentDestination?.route?.contains("Profile") == true

            if (!hideBottomBar) {
                AppBottomBar(
                    currentRoute = currentDestination?.route,
                    onNavigate = { targetRoute ->
                        if (currentDestination?.route != targetRoute) {
                            navController.navigate(targetRoute) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    }
                )
            }
        }
    ) { innerPadding ->
        // FIX: Removed .padding(innerPadding) from the NavHost container.
        // This opens up full-bleed edge-to-edge layout space across coordinates.
        NavHost(
            navController = navController,
            startDestination = SplashRoute,
            modifier = Modifier.fillMaxSize(),

            enterTransition = {
                slideInHorizontally(initialOffsetX = { fullWidth -> fullWidth }, animationSpec = tween(300)) + fadeIn(animationSpec = tween(300))
            },
            exitTransition = {
                slideOutHorizontally(targetOffsetX = { fullWidth -> -fullWidth }, animationSpec = tween(300)) + fadeOut(animationSpec = tween(300))
            },
            popEnterTransition = {
                slideInHorizontally(initialOffsetX = { fullWidth -> -fullWidth }, animationSpec = tween(300)) + fadeIn(animationSpec = tween(300))
            },
            popExitTransition = {
                slideOutHorizontally(targetOffsetX = { fullWidth -> fullWidth }, animationSpec = tween(300)) + fadeOut(animationSpec = tween(300))
            }
        ) {
            // --- EDGE-TO-EDGE FULL SCREEN DESTINATIONS ---
            composable<SplashRoute> {
                SplashScreen(onSplashFinished = {
                    navController.navigate(OnboardingRoute) {
                        popUpTo(SplashRoute) { inclusive = true }
                    }
                })
            }

            composable<OnboardingRoute> {
                OnboardingScreen(
                    onFinished = {
                        navController.navigate(LoginRoute) {
                            popUpTo(OnboardingRoute) { inclusive = true }
                        }
                    },
                    onLoginClick = {
                        navController.navigate(LoginRoute)
                    }
                )
            }

            composable<LoginRoute> {
                LoginScreen(
                    onLoginSuccess = {
                        navController.navigate(HomeRoute) {
                            popUpTo(LoginRoute) { inclusive = true }
                        }
                    },
                    onSignupClick = {
                        navController.navigate(SignupRoute)
                    }
                )
            }

            composable<SignupRoute> {
                SignupScreen(
                    onSignupSuccess = {
                        navController.navigate(HomeRoute) {
                            popUpTo(LoginRoute) { inclusive = true }
                        }
                    },
                    onBackClick = {
                        navController.popBackStack()
                    }
                )
            }

            // --- MAIN TAB DESTINATIONS (CONSUME THE BOTTOM BAR PADDING) ---
            composable<HomeRoute> {
                Box(modifier = Modifier.fillMaxSize().padding(innerPadding)) {
                    HomeScreen(
                        onProfileNavigation = { navController.navigate(ProfileRoute) },
                        onAddExpenseClick = { navController.navigate(AddExpenseRoute) },
                        onAddIncomeClick = { navController.navigate(AddIncomeRoute) },
                        onTransferClick = { /* navController.navigate(TransferRoute) */ },
                        onViewReportsClick = { navController.navigate(AnalyticsRoute) },
                        onManageClick = { /* Handle manage logic or navigate */ }
                    )
                }
            }

            composable<TransactionsRoute> {
                Box(modifier = Modifier.fillMaxSize().padding(innerPadding)) {
                    TransactionsScreen(viewModel = transactionsViewModel)
                }
            }

            // --- INDEPENDENT OVERLAY SUBFLOW SCREEN SCALINGS ---
            composable<AddExpenseRoute> {
                AddExpenseScreen(
                    onBackClick = { navController.popBackStack() },
                    onViewTransaction = { navController.popBackStack() },
                    onSaveTransaction = { transactionsViewModel.addTransaction(it) }
                )
            }

            composable<AddIncomeRoute> {
                AddIncomeScreen(
                    onBackClick = { navController.popBackStack() },
                    onViewIncome = { navController.popBackStack() },
                    onSaveTransaction = { transactionsViewModel.addTransaction(it) }
                )
            }

            composable<AnalyticsRoute> { backStackEntry ->
                val viewModel: AnalyticsViewModel = viewModel(viewModelStoreOwner = backStackEntry)
                Box(modifier = Modifier.fillMaxSize().padding(innerPadding)) {
                    AnalyticsScreen(viewModel = viewModel)
                }
            }

            composable<GoalsRoute> {
                Box(modifier = Modifier.fillMaxSize().padding(innerPadding)) {
                    GoalsScreen(
                        viewModel = goalsViewModel,
                        onAddGoalClick = { navController.navigate(AddGoalRoute) }
                    )
                }
            }

            composable<AddGoalRoute> {
                AddGoalScreen(
                    viewModel = goalsViewModel,
                    onBackClick = { navController.popBackStack() },
                    onViewGoal = { navController.popBackStack() },
                    onSaveTransaction = { transactionsViewModel.addTransaction(it) }
                )
            }

            composable<ProfileRoute> {
                Box(modifier = Modifier.fillMaxSize().padding(innerPadding)) {
                    ProfileScreen(
                        settingsViewModel = settingsViewModel,
                        onBackClick = { navController.popBackStack() }
                    )
                }
            }
        }
    }
}
