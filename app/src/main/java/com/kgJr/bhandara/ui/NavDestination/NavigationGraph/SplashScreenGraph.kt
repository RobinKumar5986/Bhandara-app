package com.kgJr.bhandara.ui.NavDestination.NavigationGraph

import android.content.Intent
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.kgJr.bhandara.MainActivity2
import com.kgJr.bhandara.ui.NavDestination.AuthScreenGraph
import com.kgJr.bhandara.ui.NavDestination.HomeScreenGraph
import com.kgJr.bhandara.ui.NavDestination.NavDestDataModels
import com.kgJr.bhandara.ui.NavDestination.SplashScreenGraph
import com.kgJr.bhandara.ui.layouts.Splash.ServerStarter
import com.kgJr.bhandara.ui.layouts.Splash.SplashScreen
import kotlinx.serialization.Serializable

fun NavGraphBuilder.splashScreenGraph(navController: NavController) {
    navigation<NavDestDataModels.Splash>(startDestination = SplashScreenGraph.SplashScreen) {
        composable<SplashScreenGraph.SplashScreen> {
            SplashScreen { context , id ->
                navController.navigate(SplashScreenGraph.ServerStarter(id = id))
                {
                    popUpTo(navController.graph.id) {
                        inclusive = true
                    }
                }
            }
        }
        composable<SplashScreenGraph.ServerStarter> {
            val data = it.toRoute<SplashScreenGraph.ServerStarter>()
            val id = data.id
            ServerStarter { context ->
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
@Serializable
enum class SplashDestination{
    AUTH_SCREEN,
    HOME_SCREEN
}
