package com.hometriangle.bhandara.ui.NavDestination.NavigationGraph.AuthGraph

import androidx.compose.material3.Text
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.hometriangle.bhandara.ui.NavDestination.AuthScreenGraph
import com.hometriangle.bhandara.ui.NavDestination.HomeScreenGraph
import com.hometriangle.bhandara.ui.NavDestination.NavigationGraph.AuthScreenId
import com.hometriangle.bhandara.ui.layouts.Auth.LandingScreen
import com.hometriangle.bhandara.ui.layouts.Auth.OtpVerificationScreen


fun NavGraphBuilder.homeNavigationGraph(navController: NavController) {
    navigation<HomeScreenGraph.HomeScreenMain>(startDestination = HomeScreenGraph.HomeScreen) {
        composable<HomeScreenGraph.HomeScreen> {
           Text("Home Screen....")
        }

    }
}
