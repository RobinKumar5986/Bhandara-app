package com.hometriangle.bhandara.ui.NavDestination

import kotlinx.serialization.Serializable

/**
 * @Note: this class is the main root of the graph.
 */

sealed class NavDestDataModels{
    @Serializable
    data object Splash: NavDestDataModels()

}


sealed class SplashScreenGraph {
    @Serializable
    data object SplashScreen : SplashScreenGraph()

    @Serializable
    data object SecondScreen : SplashScreenGraph()
}


sealed class AuthScreenGraph {
    @Serializable
    data object AuthScreen : AuthScreenGraph()

    @Serializable
    data object LandingScreen: AuthScreenGraph()

    @Serializable
    data class OtpVerificationScreen(val phoneNumber: String ,val userId: String): AuthScreenGraph()

}

sealed class HomeScreenGraph{
    @Serializable
    data object HomeScreenMain: HomeScreenGraph()

    @Serializable
    data object HomeScreen: HomeScreenGraph()
}



