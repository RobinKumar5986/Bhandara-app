package com.hometriangle.bhandara.ui.NavDestination.NavigationGraph

import android.content.Intent
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.hometriangle.bhandara.MainActivity2
import com.hometriangle.bhandara.ui.NavDestination.AuthScreenGraph
import com.hometriangle.bhandara.ui.NavDestination.HomeScreenGraph
import com.hometriangle.bhandara.ui.NavDestination.NavDestDataModels
import com.hometriangle.bhandara.ui.NavDestination.SplashScreenGraph
import com.hometriangle.bhandara.ui.layouts.Splash.SplashScreen

fun NavGraphBuilder.splashScreenGraph(navController: NavController) {
    navigation<NavDestDataModels.Splash>(startDestination = SplashScreenGraph.SplashScreen) {
        composable<SplashScreenGraph.SplashScreen> {
            SplashScreen { context , id ->
                val intent = Intent(context, MainActivity2::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                var destination: String = ""
                if(id == SplashDestination.AUTH_SCREEN){
                    destination =  AuthScreenGraph.AuthScreen::class.java.name
                }else if(id == SplashDestination.HOME_SCREEN){
                    destination =  HomeScreenGraph.HomeScreenMain::class.java.name
                }
                intent.putExtra("Activity", destination)
                context.startActivity(intent)
            }
        }
    }
}

enum class SplashDestination{
    AUTH_SCREEN,
    HOME_SCREEN
}
