package com.hometriangle.bhandara.ui.NavDestination.NavigationGraph.UiGraph

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


fun NavGraphBuilder.authScreenGraph(navController: NavController) {
    navigation<AuthScreenGraph.AuthScreen>(startDestination = AuthScreenGraph.LandingScreen) {
        composable<AuthScreenGraph.LandingScreen> {
            LandingScreen { id, phoneNumber, userId ->
                if (id == AuthScreenId.OTP_SCREEN) {
                    navController.navigate(
                        AuthScreenGraph.OtpVerificationScreen(
                            phoneNumber = phoneNumber,
                            userId = userId
                        )
                    ) {
                        popUpTo(navController.graph.id) {
                            inclusive = true
                        }
                    }
                }
            }
        }
        composable<AuthScreenGraph.OtpVerificationScreen> {
            val data = it.toRoute<AuthScreenGraph.OtpVerificationScreen>()
            OtpVerificationScreen(data) { id ->
                if(id == AuthScreenId.HOME_SCREEN){
                    navController.navigate(HomeScreenGraph.HomeScreenMain) {
                        popUpTo(navController.graph.id) {
                            inclusive = true
                        }
                    }
                }
            }
        }
    }
}
