package com.hometriangle.bhandara

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.lifecycleScope
import com.hometriangle.bhandara.databaseUtils.tablesS1.LocationEntity
import com.hometriangle.bhandara.ui.NavDestination.NavigationGraph.MainNavGraph
import com.hometriangle.bhandara.ui.theme.BhandaraStartActivity
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel = MainViewModel()
        lifecycleScope.launch {
            viewModel.locationDb.insertLocation(LocationEntity(latitude = 10.1, longitude = 100.1))
        }
        enableEdgeToEdge()
        setContent {
            BhandaraStartActivity {
               MainNavGraph()
            }
        }
    }
}