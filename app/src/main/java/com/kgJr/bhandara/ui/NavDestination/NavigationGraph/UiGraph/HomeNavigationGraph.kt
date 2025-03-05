package com.kgJr.bhandara.ui.NavDestination.NavigationGraph.UiGraph

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.kgJr.bhandara.ui.NavDestination.HomeScreenGraph
import com.kgJr.bhandara.ui.layouts.Home.AllBhandaraMapScreen
import com.kgJr.bhandara.ui.layouts.Home.AllBhandaraScreen
import com.kgJr.bhandara.ui.layouts.Home.BhandaraDetailScreen
import com.kgJr.bhandara.ui.layouts.Home.CreateBhandaraScreen
import com.kgJr.bhandara.ui.layouts.Home.DonateScreen
import com.kgJr.bhandara.ui.layouts.Home.HomeScreen
import com.kgJr.bhandara.ui.layouts.Home.LocationScreen
import com.kgJr.bhandara.ui.layouts.Home.MapScreen


fun NavGraphBuilder.homeNavigationGraph(navController: NavController) {
    navigation<HomeScreenGraph.HomeScreenMain>(startDestination = HomeScreenGraph.LocationScreen) {
        composable<HomeScreenGraph.LocationScreen> {
            LocationScreen(nav = { id ->
                if (id == HomeScreenId.HOME_SCREEN) {
                    navController.navigate(HomeScreenGraph.HomeScreen) {
                        popUpTo(navController.graph.id) {
                            inclusive = true
                        }
                    }
                }
            })
        }

        composable<HomeScreenGraph.HomeScreen> {
            HomeScreen(nav = { navId ->
                if (navId == HomeScreenId.CREATE_BHANDARA_SCREEN) {
                    navController.navigate(HomeScreenGraph.CreateBhandaraScreen)
                } else if (navId == HomeScreenId.ALL_BHANDARA_SCREEN) {
                    navController.navigate(HomeScreenGraph.AllBhandaraScreen)
                }else if(navId == HomeScreenId.DONATE_SCREEN){
                    navController.navigate(HomeScreenGraph.DonateScreen)
                }
            })
        }
        composable<HomeScreenGraph.CreateBhandaraScreen> {
            CreateBhandaraScreen(nav = {
                navController.popBackStack(route = HomeScreenGraph.HomeScreen, inclusive = false)
            })
        }
        composable<HomeScreenGraph.AllBhandaraScreen> {
            AllBhandaraScreen(nav = { id, mapModel ->
                if(id == HomeScreenId.SINGLE_MAP_VIEW) {
                    navController.navigate(
                        HomeScreenGraph.MapScreenLink(
                            srcLat = mapModel!!.srcLat,
                            srcLng = mapModel.srcLng,
                            dstLat = mapModel.dstLat,
                            dstLng = mapModel.dstLng,
                            srcTitle = mapModel.srcTitle,
                            srcDescription = mapModel.srcDescription,
                            dstTitle = mapModel.dstTitle,
                            dstDescription = mapModel.dstDescription
                        )
                    )
                }else if (id == HomeScreenId.ALL_BHANDARA_MAP_SCREEN){
                    navController.navigate(HomeScreenGraph.AllBhandaraMapScreen)
                }else if(id == HomeScreenId.BHANDARA_DETAIL_SCREEN){
                    navController.navigate(HomeScreenGraph.BhandaraDetailPage)
                }
            })
        }
        composable<HomeScreenGraph.MapScreenLink> {
            val data = it.toRoute<HomeScreenGraph.MapScreenLink>()
            MapScreen(
                srcLat = data.srcLat,
                srcLng = data.srcLng,
                dstLat = data.dstLat,
                dstLng = data.dstLng,
                srcTitle = data.srcTitle,
                srcDescription = data.srcDescription,
                dstTitle = data.dstTitle,
                dstDescription = data.dstDescription
            )
        }
        composable<HomeScreenGraph.AllBhandaraMapScreen> {
            AllBhandaraMapScreen()
        }
        composable<HomeScreenGraph.BhandaraDetailPage> {
            BhandaraDetailScreen(){

            }
        }
        composable<HomeScreenGraph.DonateScreen> {
            DonateScreen()
        }

    }
}
enum class HomeScreenId{
    LOCATION_SCREEN,
    HOME_SCREEN,
    ALL_BHANDARA_SCREEN,
    CREATE_BHANDARA_SCREEN,
    VOLUNTEER_SCREEN,
    DONATE_SCREEN,
    PROFILE_SCREEN,
    SINGLE_MAP_VIEW,
    ALL_BHANDARA_MAP_SCREEN,
    BHANDARA_DETAIL_SCREEN,
}