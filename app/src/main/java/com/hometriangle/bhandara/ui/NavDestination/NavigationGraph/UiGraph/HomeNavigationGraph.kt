package com.hometriangle.bhandara.ui.NavDestination.NavigationGraph.UiGraph

import androidx.compose.material3.Text
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.hometriangle.bhandara.ui.NavDestination.HomeScreenGraph
import com.hometriangle.bhandara.ui.layouts.Home.LocationScreen


fun NavGraphBuilder.homeNavigationGraph(navController: NavController) {
    navigation<HomeScreenGraph.HomeScreenMain>(startDestination = HomeScreenGraph.LocationScreen) {
        composable<HomeScreenGraph.HomeScreen> {
            Text("hello world")
        }
        composable<HomeScreenGraph.LocationScreen> {
            LocationScreen{
            }
        }

    }
}
enum class HomeScreenId{
    LOCATION_SCREEN,
    HOME_SCREEN
}