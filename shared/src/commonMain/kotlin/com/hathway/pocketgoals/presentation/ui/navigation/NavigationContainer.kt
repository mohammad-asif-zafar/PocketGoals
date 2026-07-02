package com.hathway.pocketgoals.presentation.ui.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
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
import com.hathway.pocketgoals.presentation.ui.navigation_content.GoalsContent
import com.hathway.pocketgoals.presentation.ui.screens.AddExpenseScreen
import com.hathway.pocketgoals.presentation.ui.screens.AnalyticsScreen
import com.hathway.pocketgoals.presentation.ui.screens.GoalsScreen
import com.hathway.pocketgoals.presentation.ui.screens.HomeScreen
import com.hathway.pocketgoals.presentation.ui.screens.TransactionsScreen
import com.hathway.pocketgoals.presentation.ui.viewmodel.AnalyticsViewModel

@Composable
fun NavigationContainer() {
    val navController: NavHostController = rememberNavController()

    // Tracks the destination instead of the raw string route
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    Scaffold(
        modifier = Modifier.fillMaxSize(), bottomBar = {
            AppBottomBar(
                currentRoute = currentDestination?.route, onNavigate = { targetRoute ->
                    // Guard structure to prevent redundant destination reloading
                    if (currentDestination?.route != targetRoute) {
                        navController.navigate(targetRoute) {
                            // Pops up to the start destination of the graph to
                            // avoid building up a massive stack of destinations
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            // Avoids multiple copies of the same destination when
                            // reselecting the same item
                            launchSingleTop = true
                            // Restores state when reselecting a previously selected item
                            restoreState = true
                        }
                    }
                })
        }) { innerPadding ->
        // Type-safe NavHost definition using the HomeRoute object class type
        NavHost(
            navController = navController,
            startDestination = HomeRoute,
            modifier = Modifier.fillMaxSize().padding(innerPadding),

            // 1. Animation when opening a new screen (Slides in from the right, fades in)
            enterTransition = {
                slideInHorizontally(
                    initialOffsetX = { fullWidth -> fullWidth }, animationSpec = tween(300)
                ) + fadeIn(animationSpec = tween(300))
            },

            // 2. Animation for the screen that is disappearing (Slides out to the left, fades out)
            exitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { fullWidth -> -fullWidth }, animationSpec = tween(300)
                ) + fadeOut(animationSpec = tween(300))
            },

            // 3. Animation when returning via system back button (Slides in from the left)
            popEnterTransition = {
                slideInHorizontally(
                    initialOffsetX = { fullWidth -> -fullWidth }, animationSpec = tween(300)
                ) + fadeIn(animationSpec = tween(300))
            },

            // 4. Animation for the screen being popped off the stack (Slides out to the right)
            popExitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { fullWidth -> fullWidth }, animationSpec = tween(300)
                ) + fadeOut(animationSpec = tween(300))
            }
        ) {
            // Register destination UI templates using generic type arguments
            composable<HomeRoute> {
                HomeScreen()
            }
            composable<TransactionsRoute> {
                TransactionsScreen()
            }
            composable<AddExpenseRoute> {
                AddExpenseScreen(onBackClick = {}, onViewTransaction = {})
            }
            composable<AnalyticsRoute> { backStackEntry ->
                // Scopes the ViewModel life to this specific navigation entry block
                val viewModel: AnalyticsViewModel = viewModel(viewModelStoreOwner = backStackEntry)

                AnalyticsScreen(viewModel = viewModel)
            }
            composable<GoalsRoute> {
                GoalsContent()
            }
        }
    }
}
