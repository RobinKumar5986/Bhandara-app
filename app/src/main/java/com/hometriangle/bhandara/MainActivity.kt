package com.hometriangle.bhandara

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.hometriangle.bhandara.ui.NavDestination.NavigationGraph.MainNavGraph
import com.hometriangle.bhandara.ui.theme.BhandaraStartActivity

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BhandaraStartActivity {
               MainNavGraph()
            }
        }
    }
}