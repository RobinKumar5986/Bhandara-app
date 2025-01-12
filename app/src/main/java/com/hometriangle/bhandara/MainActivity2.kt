package com.hometriangle.bhandara


import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.hometriangle.bhandara.ui.NavDestination.NavigationGraph.StartNavigation
import com.hometriangle.bhandara.ui.theme.BhandaraStartActivity
class MainActivity2 : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val className = intent.getStringExtra("Activity")
        val destination = className?.substringAfterLast('.') ?: "Unknown"
        enableEdgeToEdge()
        setContent {
            BhandaraStartActivity {
                StartNavigation(destination = destination)
            }
        }
    }
}