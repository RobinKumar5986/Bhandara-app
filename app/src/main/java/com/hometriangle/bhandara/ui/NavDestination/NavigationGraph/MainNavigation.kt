@file:Suppress("IMPLICIT_CAST_TO_ANY")

package com.hometriangle.bhandara.ui.NavDestination.NavigationGraph
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.hometriangle.bhandara.ui.NavDestination.AuthScreenGraph
import com.hometriangle.bhandara.ui.NavDestination.HomeScreenGraph
import com.hometriangle.bhandara.ui.NavDestination.NavDestDataModels
import com.hometriangle.bhandara.ui.NavDestination.NavigationGraph.UiGraph.authScreenGraph
import com.hometriangle.bhandara.ui.NavDestination.NavigationGraph.UiGraph.homeNavigationGraph


@Composable
fun MainNavGraph(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = NavDestDataModels.Splash) {
        splashScreenGraph(navController)
    }
}
@Composable
fun StartNavigation(navController: NavHostController, modifier: Modifier = Modifier, destination: String) {
    val startDestination = when (destination) {
        "HomeScreenGraph\$HomeScreenMain" -> HomeScreenGraph.HomeScreenMain
        "AuthScreenGraph\$AuthScreen" -> AuthScreenGraph.AuthScreen
        else -> AuthScreenGraph.AuthScreen
    }


    NavHost(navController = navController, startDestination = startDestination ) {
        authScreenGraph(navController = navController)
        homeNavigationGraph(navController = navController)
    }
}

//SCREEN IDs
enum class AuthScreenId{
    LOGIN_SCREEN,
    OTP_SCREEN,
    HOME_SCREEN
}
