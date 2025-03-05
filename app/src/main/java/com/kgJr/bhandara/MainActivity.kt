package com.kgJr.bhandara

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.kgJr.bhandara.ui.NavDestination.NavigationGraph.MainNavGraph
import com.kgJr.bhandara.ui.theme.BhandaraStartActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
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