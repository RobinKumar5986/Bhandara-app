package com.hometriangle.bhandara
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.layout.Column
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.hometriangle.bhandara.ui.NavDestination.NavigationGraph.StartNavigation
import com.hometriangle.bhandara.ui.layouts.Home.HomeViewModel
import com.hometriangle.bhandara.ui.theme.BhandaraStartActivity
import com.hometriangle.bhandara.ui.theme.md_theme_light_primary
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity2 : ComponentActivity() {
    private  val BAN_BOTTOM_NAV_BAR = setOf(
        "com.hometriangle.bhandara.ui.NavDestination.AuthScreenGraph.LandingScreen",
        "com.hometriangle.bhandara.ui.NavDestination.AuthScreenGraph.OtpVerificationScreen/{phoneNumber}/{userId}",
        "com.hometriangle.bhandara.ui.NavDestination.HomeScreenGraph.LocationScreen"
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val className = intent.getStringExtra("Activity")
        val destination = className?.substringAfterLast('.') ?: "Unknown"
//        enableEdgeToEdge()
        setContent {
            BhandaraStartActivity {
                Column {
                    val navController = rememberNavController()
                    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
                    // Log the current screen (route)
                    Log.d("CurrentScreen", "Current screen: $currentRoute")
                    Column(modifier = Modifier.weight(1f)) {
                        StartNavigation(navController = navController,destination = destination)
                    }
//                    if(currentRoute != null && !BAN_BOTTOM_NAV_BAR.contains(currentRoute)) {
//                        BottomNavigationBar()
//                    }
                }
            }
        }
    }
}

@Composable
fun BottomNavigationBar() {
    BottomNavigation(
        backgroundColor = md_theme_light_primary
    ) {
        BottomNavigationItem(
            selected = false,
            onClick = { /* Handle navigation */ },
            icon = {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_home),
                    contentDescription = "Home",
                )
            },
            label = {
                Text("Home")
            }
        )
        BottomNavigationItem(
            selected = false,
            onClick = { /* Handle navigation */ },
            icon = {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_profile),
                    contentDescription = "Profile"
                )
            },
            label = {
                Text("Profile")
            },
        )
    }
}