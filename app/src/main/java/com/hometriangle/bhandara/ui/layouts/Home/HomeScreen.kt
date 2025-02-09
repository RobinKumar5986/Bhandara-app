package com.hometriangle.bhandara.ui.layouts.Home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.hometriangle.bhandara.R
import com.hometriangle.bhandara.ui.theme.DarkGrey
import com.hometriangle.bhandara.ui.theme.PureWhite
import com.hometriangle.bhandara.ui.theme.SoftPink
import com.hometriangle.bhandara.ui.theme.SpaceMedium
import com.hometriangle.bhandara.ui.theme.TrueWhite
import com.hometriangle.bhandara.ui.theme.defaultButtonColor
import com.hometriangle.bhandara.ui.theme.md_theme_light_secondary
import kotlinx.coroutines.delay
@OptIn(ExperimentalPagerApi::class)
@Composable
fun HomeScreen(
    nav: () -> Unit
) {
    val context = LocalContext.current
    val brush = Brush.verticalGradient(
        colors = listOf(
            PureWhite,
            PureWhite,
            PureWhite,
            PureWhite,
            SoftPink
        )
    )

    val imageUrls = listOf(
        "https://fastly.picsum.photos/id/499/536/354.jpg?hmac=8f-M63IkmYvH2AXKVRL_mE-G5R9N1Qbt2rAPNq_rXvs",
        "https://fastly.picsum.photos/id/499/536/354.jpg?hmac=8f-M63IkmYvH2AXKVRL_mE-G5R9N1Qbt2rAPNq_rXvs",
        "https://fastly.picsum.photos/id/499/536/354.jpg?hmac=8f-M63IkmYvH2AXKVRL_mE-G5R9N1Qbt2rAPNq_rXvs"
    )

    val pagerState = rememberPagerState(initialPage = 0)

    LaunchedEffect(pagerState) {
        while (true) {
            delay(3000)
            val nextPage = (pagerState.currentPage + 1) % imageUrls.size
            pagerState.animateScrollToPage(nextPage)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(brush)
    ) {
        HorizontalPager(
            count = imageUrls.size,
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        ) { page ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .clickable { },
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = imageUrls[page],
                    contentDescription = "Pager Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = SpaceMedium),
            content = {
                item {
                    DashBoardBox(
                        title = "Bhandara",
                        msg = "Free Food",
                        image = R.drawable.cutlery,
                        onClick = { /* Handle onClick */ }
                    )
                }
                item {
                    DashBoardBox(
                        title = "Create Bhandara",
                        msg = "Feed People",
                        image = R.drawable.bhandara_icon,
                        onClick = { /* Handle onClick */ }
                    )
                }
                item {
                    DashBoardBox(
                        title = "Volunteer",
                        msg = "Make change",
                        image = R.drawable.volunteer,
                        onClick = { /* Handle onClick */ }
                    )
                }
                item {
                    DashBoardBox(
                        title = "Donate",
                        msg = "Show support",
                        image = R.drawable.donation,
                        onClick = { /* Handle onClick */ }
                    )
                }
            }
        )
        Button(
            modifier = Modifier
                .fillMaxWidth().padding(8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = defaultButtonColor,
                contentColor = TrueWhite,
                disabledContainerColor = DarkGrey,
                disabledContentColor = TrueWhite
            ),
            shape = MaterialTheme.shapes.small,
            onClick = {

            }
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = "Location Icon",
                    modifier = Modifier.padding(end = 8.dp)
                )
                Text(text = "Share A Bhandara Location")
            }
        }
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = md_theme_light_secondary,
                contentColor = TrueWhite,
                disabledContainerColor = DarkGrey,
                disabledContentColor = TrueWhite
            ),
            shape = MaterialTheme.shapes.small,
            onClick = {
                // Handle button click
            }
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = "Donation Icon",
                    modifier = Modifier.padding(end = 8.dp),
                    tint = Color.Red
                )
                Text(text = "Consider To Donate")
            }
        }

        Spacer(modifier = Modifier.weight(1f)) // Push text to bottom

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp, start = 16.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = "Crafted with ❤️",
                fontWeight = FontWeight.Bold,
                fontSize = 28.sp,
                color = defaultButtonColor
            )
            Text(
                text = "For the people by the people",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = Color.Black
            )
        }
    }
}
