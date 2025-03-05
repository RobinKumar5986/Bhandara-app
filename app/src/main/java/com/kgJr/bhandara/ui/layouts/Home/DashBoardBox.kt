package com.kgJr.bhandara.ui.layouts.Home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kgJr.bhandara.R
import com.kgJr.bhandara.ui.theme.SoftPink
import com.kgJr.bhandara.ui.theme.TrueWhite

@Composable
fun DashBoardBox(
    title: String,
    msg: String,
    image: Int,
    onClick: () -> Unit
) {
    val brush = Brush.horizontalGradient(listOf(SoftPink, TrueWhite))
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(90.dp)
            .clip(RoundedCornerShape(16.dp))
            .padding(8.dp)
            .background(brush, shape = RoundedCornerShape(16.dp))
            .clickable { onClick() }
            .padding(16.dp)

    ) {
        Row {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(text = title, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                Text(text = msg, fontSize = 11.sp)
            }
            Image(
                painter = painterResource(id = image),
                contentDescription = null,
                modifier = Modifier.size(40.dp)
            )
        }

    }
}

@Composable
@Preview
fun boxPreView() {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.fillMaxWidth(),
        content = {
            item {
                DashBoardBox(
                    title = "Bhandara",
                    msg = "Free Food",
                    image = R.drawable.bhandara_icon,
                    onClick = { /* Handle onClick */ }
                )
            }
            item {
                DashBoardBox(
                    title = "Create Bhandar",
                    msg = "Need volunteer",
                    image = R.drawable.bhandara_icon,
                    onClick = { /* Handle onClick */ }
                )
            }
            item {
                DashBoardBox(
                    title = "Create Bhandar",
                    msg = "Need volunteer",
                    image = R.drawable.bhandara_icon,
                    onClick = { /* Handle onClick */ }
                )
            }
            item {
                DashBoardBox(
                    title = "Create Bhandar",
                    msg = "Need volunteer",
                    image = R.drawable.bhandara_icon,
                    onClick = { /* Handle onClick */ }
                )
            }
        }
    )
}

