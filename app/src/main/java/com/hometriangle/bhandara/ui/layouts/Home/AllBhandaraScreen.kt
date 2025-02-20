package com.hometriangle.bhandara.ui.layouts.Home

import android.widget.Toast
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Switch
import androidx.compose.material.SwitchDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.hometriangle.bhandara.MainApplication
import com.hometriangle.bhandara.ui.NavDestination.NavigationGraph.UiGraph.HomeScreenId
import com.hometriangle.bhandara.ui.layouts.UiUtils.CenteredProgressView
import com.hometriangle.bhandara.ui.theme.DeepPink
import com.hometriangle.bhandara.ui.theme.TrueWhite
import com.hometriangle.bhandara.ui.theme.VibrantPink
import com.hometriangle.localModel.MapScreenModel


@Composable
fun AllBhandaraScreen(
    nav:(HomeScreenId, MapScreenModel?) -> Unit
) {
    var selectedTab by remember { mutableStateOf(0) }
    val viewModel: HomeViewModel = hiltViewModel()
    val isLoading = viewModel.isLoading.collectAsState().value
    val currentBhandara = viewModel.currentBhandaras.collectAsState().value
    var nearMe by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val currentLocation = viewModel.locations.collectAsState().value
    val error  = viewModel.error.collectAsState().value
    LaunchedEffect(Unit){
        viewModel.getAllUpComingBhandara()
    }
    LaunchedEffect(error){
        if(!error.isNullOrEmpty()) {
            Toast.makeText(context, "Error: ${error}", Toast.LENGTH_SHORT).show()
            viewModel.clearError()
        }
    }
    if(!isLoading) {
        Column(modifier = Modifier.fillMaxSize()) {
            ToggleButton(selectedTab) {
                selectedTab = it
                if(selectedTab == 0){
                    viewModel.filterTodaysBhandara()
                }else{
                    viewModel.filterUpcomingBhandara()
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Divider()
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Near me",
                        fontSize = 12.sp)
                    Switch(
                        checked = nearMe,
                        onCheckedChange = {
                            nearMe = it
                            if(nearMe) {
                                viewModel.filterNearMeBhandara(userLat = currentLocation.first().latitude, userLon = currentLocation.first().longitude)
                            }else{
                                if(selectedTab == 0){
                                    viewModel.filterTodaysBhandara()
                                }else{
                                    viewModel.filterUpcomingBhandara()
                                }
                            } },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = DeepPink, // Thumb color when ON
                            checkedTrackColor = DeepPink, // Track color when ON
                            uncheckedThumbColor = Color.Gray, // Thumb color when OFF
                            uncheckedTrackColor = Color.LightGray // Track color when OFF
                        )
                    )

                }

                Button(
                    onClick = {
                        MainApplication.bhandaraList = currentBhandara
                        nav(HomeScreenId.ALL_BHANDARA_MAP_SCREEN,null)
                              },
                    shape = RoundedCornerShape(25),
                    colors = ButtonDefaults.buttonColors(containerColor = TrueWhite),
                    contentPadding = PaddingValues(horizontal = 12.dp),
                    border = BorderStroke(1.dp, VibrantPink)
                ) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = "Location",
                        tint = VibrantPink
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = "Map View", color = Color.Black, fontSize = 12.sp)
                }

            }
            Spacer(modifier = Modifier.height(8.dp))
            Divider()
            Spacer(modifier = Modifier.height(8.dp))
            currentBhandara.let { list ->
                LazyColumn(
                    modifier = Modifier.padding(16.dp)
                ) {

                    items(list) { bhandara ->
                        Column (
                            modifier = Modifier
                                .padding(bottom = 8.dp)
                                .clickable(onClick = {
                                    MainApplication.bhandaraDto = bhandara
                                    nav(HomeScreenId.BHANDARA_DETAIL_SCREEN,null)
                                })
                        ){
                            BhandaraListItem(
                                bhandara = bhandara,
                                modifier = Modifier.padding(bottom = 8.dp)
                            , onOpenClick = {
                                nav(HomeScreenId.SINGLE_MAP_VIEW , MapScreenModel(
                                    srcLat = currentLocation.first().latitude, srcLng = currentLocation.first().longitude,
                                    dstLat = bhandara.latitude!!, dstLng = bhandara.longitude!!,
                                    srcTitle = "Me",
                                    srcDescription = "My Current Location",
                                    dstTitle = bhandara.name ?: "Bhandara",
                                    dstDescription = bhandara.description ?: "Bhandara for all"
                                ))
                            })
                        }
                    }

                }
            }

        }
    }else{
        CenteredProgressView(message = "Getting the bhandars...")
    }
}

@Composable
fun ToggleButton(selectedTab: Int, onTabSelected: (Int) -> Unit) {
    Row(
        modifier = Modifier
            .padding(16.dp)
            .background(Color.LightGray, shape = RoundedCornerShape(25))
            .clip(RoundedCornerShape(25))
    ) {
        Column(
            Modifier.weight(1f)
                .padding(start = 3.dp)
                .padding(vertical = 3.dp)
        ){
            ToggleItem("TODAY", selectedTab == 0) {
                onTabSelected(0)
            }
        }
        Column(
            Modifier.weight(1f)
                .padding(end = 3.dp)
                .padding(vertical = 3.dp)
        ) {
            ToggleItem("UPCOMING", selectedTab == 1) { onTabSelected(1) }
        }
    }
}

@Composable
fun ToggleItem(text: String, isSelected: Boolean, onClick: () -> Unit) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .clip(RoundedCornerShape(25))
            .background(if (isSelected) VibrantPink else Color.Transparent)
            .animateContentSize(animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy))
            .clickable { onClick() }
    ) {
        Text(
            text = text,
            color = if (isSelected) Color.White else Color.Black,
            fontWeight = FontWeight.Bold
        )
    }
}
