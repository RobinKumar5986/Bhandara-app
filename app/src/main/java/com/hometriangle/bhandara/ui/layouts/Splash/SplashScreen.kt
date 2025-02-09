package com.hometriangle.bhandara.ui.layouts.Splash

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import com.hometriangle.bhandara.MainApplication
import com.hometriangle.bhandara.ui.NavDestination.NavigationGraph.SplashDestination


@Composable
fun SplashScreen(onNavigateToNext: (Context, SplashDestination) -> Unit) {
    val context = LocalContext.current
    LaunchedEffect(key1 = Unit) {
        val sharedPreferences = MainApplication.userDataPref
        val isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false)
        if (isLoggedIn) {
            onNavigateToNext(context, SplashDestination.HOME_SCREEN)
        } else {
            onNavigateToNext(context, SplashDestination.AUTH_SCREEN)
        }
    }
}