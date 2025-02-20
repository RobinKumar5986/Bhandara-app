package com.hometriangle.bhandara.ui.NavDestination

import android.os.Parcelable
import com.hometriangle.bhandara.data.models.BhandaraDto
import com.hometriangle.bhandara.ui.NavDestination.NavigationGraph.UiGraph.HomeScreenId
import com.hometriangle.localModel.MapScreenModel
import kotlinx.android.parcel.Parcelize
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
    data object LocationScreen: HomeScreenGraph()

    @Serializable
    data object HomeScreen: HomeScreenGraph()

    @Serializable
    data object CreateBhandaraScreen: HomeScreenGraph()

    @Serializable
    data object AllBhandaraScreen: HomeScreenGraph()

    @Serializable
    data class MapScreenLink(
        val srcLat: Double,
        val srcLng: Double,
        val dstLat: Double,
        val dstLng: Double,
        val srcTitle: String,
        val srcDescription: String,
        val dstTitle: String,
        val dstDescription: String,
    ): HomeScreenGraph()

    @Serializable
    data object AllBhandaraMapScreen: HomeScreenGraph()

    @Serializable
    data object BhandaraDetailPage: HomeScreenGraph()

    @Serializable
    data object DonateScreen: HomeScreenGraph()

}



