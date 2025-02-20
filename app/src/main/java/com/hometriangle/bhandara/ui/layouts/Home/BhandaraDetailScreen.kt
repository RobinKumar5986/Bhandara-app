package com.hometriangle.bhandara.ui.layouts.Home

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.gms.maps.model.LatLng
import com.hometriangle.bhandara.MainApplication
import com.hometriangle.bhandara.R
import com.hometriangle.bhandara.ui.layouts.utils.base64ToBitmap
import com.hometriangle.bhandara.ui.layouts.utils.dayMapper
import com.hometriangle.bhandara.ui.layouts.utils.openGoogleMaps
import com.hometriangle.bhandara.ui.theme.DarkGrey
import com.hometriangle.bhandara.ui.theme.DeepPink
import com.hometriangle.bhandara.ui.theme.RichAccent
import com.hometriangle.bhandara.ui.theme.TrueBlack
import com.hometriangle.bhandara.ui.theme.TrueWhite
import com.hometriangle.bhandara.ui.theme.defaultButtonColor
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@SuppressLint("NewApi")
@Composable
fun BhandaraDetailScreen(nav: () -> Unit) {
    val bhandara = MainApplication.bhandaraDto
    val context = LocalContext.current
    val viewModel: HomeViewModel = hiltViewModel()
    val currentLocation = viewModel.locations.collectAsState().value
    Box(modifier = Modifier
        .fillMaxSize()
        .padding(bottom = 16.dp)) {
        Column(modifier = Modifier.fillMaxWidth().verticalScroll(rememberScrollState())
            .padding(bottom = 30.dp)) {
            // Image Section
            Box {
                bhandara.image?.let { base64String ->
                    val decodedBitmap = base64ToBitmap(base64String)
                    decodedBitmap?.let { bitmap ->
                        Image(
                            bitmap = bitmap.asImageBitmap(),
                            contentDescription = "Bhandara Image",
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(250.dp),
                            contentScale = ContentScale.Crop
                        )
                    }
                }
                Text(
                    modifier = Modifier
                        .clip(RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp))
                        .background(TrueWhite)
                        .padding(10.dp)
                        .align(Alignment.BottomStart),
                    text = dayMapper(bhandara.bhandaraType ?: ""),
                    style = MaterialTheme.typography.bodySmall,
                    color = RichAccent,
                    fontWeight = FontWeight.SemiBold,
                )
            }

            // Details Section
            Column(modifier = Modifier.padding(16.dp)) {
                val startTime = formatLocalDateTime(convertToLocalDateTime(bhandara.startingTime))
                val endTime = formatLocalDateTime(convertToLocalDateTime(bhandara.endingTime))
                val date = Instant.ofEpochMilli(bhandara.dateOfBhandara!!)
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate()
                    .format(DateTimeFormatter.ofPattern("dd MMM yyyy"))

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = CardDefaults.cardColors(containerColor = TrueWhite)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            if(!bhandara.bhandaraType.equals("everyDay")) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.calendar_month),
                                        contentDescription = "Date",
                                        modifier = Modifier.size(20.dp)
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(text = "Date", style = MaterialTheme.typography.bodyMedium)
                                }
                            }
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    painter = painterResource(id = R.drawable.clock_24),
                                    contentDescription = "Start Time",
                                    modifier = Modifier.size(20.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "Start Time",
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    painter = painterResource(id = R.drawable.clock_24),
                                    contentDescription = "End Time",
                                    modifier = Modifier.size(20.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(text = "End Time", style = MaterialTheme.typography.bodyMedium)
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            if(!bhandara.bhandaraType.equals("everyDay")) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(text = date, style = MaterialTheme.typography.bodyLarge)
                                }
                            }
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(text = startTime, style = MaterialTheme.typography.bodyLarge)
                            }
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(text = endTime, style = MaterialTheme.typography.bodyLarge)
                            }
                        }
                    }
                }

                if (bhandara.needVolunteer == true) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                        shape = RoundedCornerShape(8.dp),
                        colors = CardDefaults.cardColors(containerColor = TrueWhite)
                    ) {
                        Column {
                            Row(
                                modifier = Modifier.padding(8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.adjust),
                                    contentDescription = "End Time",
                                    modifier = Modifier
                                        .padding(end = 8.dp)
                                        .size(24.dp), // Increased icon size slightly
                                    tint = Color.Green
                                )
                                Text(
                                    text = "We Need Volunteer, contact us at",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Medium,
                                    modifier = Modifier.align(Alignment.CenterVertically)
                                )
                            }
                            Text(
                                text = bhandara.contactForVolunteer!!,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Medium,
                                modifier = Modifier.padding(8.dp).padding(start = 8.dp),
                                color = TrueBlack
                            )
                        }
                    }
                }
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = CardDefaults.cardColors(containerColor = TrueWhite)
                ) {
                    Text(
                        bhandara.name!!,
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp,
                        modifier = Modifier.padding(8.dp)
                    )
                    Text(
                        bhandara.description!!,
                        fontWeight = FontWeight.Medium,
                        fontSize = 16.sp,
                        modifier = Modifier.padding(8.dp)
                    )
                }

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = CardDefaults.cardColors(containerColor = TrueWhite)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Text(
                            text = bhandara.organizationType ?: "N/A",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = "Organization: ${bhandara.organizationName ?: "N/A"}",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium
                        )
                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = "Food Type: ${bhandara.foodType ?: "N/A"}",
                            fontSize = 16.sp
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        if (!bhandara.specialNote.isNullOrEmpty()) {
                            Text(
                                text = "Special Note: ${bhandara.specialNote}",
                                fontSize = 14.sp,
                                fontStyle = FontStyle.Italic
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                        }
                    }
                }
            }
        }

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .align(Alignment.BottomStart),
            colors = ButtonDefaults.buttonColors(
                containerColor = defaultButtonColor,
                contentColor = TrueWhite,
                disabledContainerColor = DarkGrey,
                disabledContentColor = TrueWhite
            ),
            shape = MaterialTheme.shapes.small,
            onClick = {
                val srcLocation = LatLng(currentLocation.first().latitude, currentLocation.first().longitude)
                val dstLocation = LatLng(bhandara.latitude!!, bhandara.longitude!!)
                openGoogleMaps(context =  context ,srcLocation,dstLocation)
            }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.near_me),
                contentDescription = "Direction",
                tint = DeepPink,
                modifier = Modifier.size(24.dp)
            )
            Text("Direction")
        }
    }
}

// Utility Functions
@SuppressLint("NewApi")
fun convertToLocalDateTime(epochMillis: Double?): LocalDateTime? {
    return epochMillis?.toLong()?.let { millis ->
        Instant.ofEpochMilli(millis).atZone(ZoneId.systemDefault()).toLocalDateTime()
    }
}

@SuppressLint("NewApi")
fun formatLocalDateTime(time: LocalDateTime?): String {
    return time?.format(DateTimeFormatter.ofPattern("hh:mm a")) ?: "N/A"
}