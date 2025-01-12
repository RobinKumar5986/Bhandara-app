package com.hometriangle.bhandara.ui.layouts.Splash

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import com.hometriangle.bhandara.ui.NavDestination.NavigationGraph.SplashDestination
import kotlinx.coroutines.delay


@Composable
fun SplashScreen(onNavigateToNext: (Context, SplashDestination) -> Unit) {
    val context = LocalContext.current
    LaunchedEffect(key1 = Unit) {
        val sharedPreferences = context.getSharedPreferences("UserData", Context.MODE_PRIVATE)
        val isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false)
        // Navigate based on login state
        if (isLoggedIn) {
            onNavigateToNext(context, SplashDestination.HOME_SCREEN)
        } else {
            onNavigateToNext(context, SplashDestination.AUTH_SCREEN)
        }
    }
}