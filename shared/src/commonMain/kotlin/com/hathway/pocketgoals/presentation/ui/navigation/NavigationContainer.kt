package com.hathway.pocketgoals.presentation.ui.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.hathway.pocketgoals.presentation.ui.components.common_components.AppBottomBar
import com.hathway.pocketgoals.presentation.ui.screens.AddExpenseScreen
import com.hathway.pocketgoals.presentation.ui.screens.AnalyticsScreen
import com.hathway.pocketgoals.presentation.ui.screens.GoalsScreen
import com.hathway.pocketgoals.presentation.ui.screens.HomeScreen
import com.hathway.pocketgoals.presentation.ui.screens.TransactionsScreen

@Composable
fun NavigationContainer() {
    val navController: NavHostController = rememberNavController()

    // Tracks the destination instead of the raw string route
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {

            AppBottomBar(
                currentRoute = currentDestination as String?, onNavigate = { targetRoute ->
                    // Guard structure to prevent redundant destination reloading
                    if (currentDestination != targetRoute) {
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
        }
    ) { innerPadding ->
        // 3. Type-safe NavHost definition using the HomeRoute object class type
        NavHost(
            navController = navController,
            startDestination = HomeRoute,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // 4. Register destination UI templates using generic type arguments
            composable<HomeRoute> {
                HomeScreen()
            }
            composable<TransactionsRoute> {
                TransactionsScreen()
            }
            composable<AddExpenseRoute> {
                AddExpenseScreen(
                    onBackClick ={},
                    onViewTransaction = {}
                )
            }
            composable<AnalyticsRoute> {
                AnalyticsScreen()
            }
            composable<GoalsRoute> {
                GoalsScreen()
            }
        }
    }
}
