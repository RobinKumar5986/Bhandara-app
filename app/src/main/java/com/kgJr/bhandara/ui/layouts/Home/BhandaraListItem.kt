package com.kgJr.bhandara.ui.layouts.Home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.kgJr.bhandara.R
import com.kgJr.bhandara.data.models.BhandaraDto
import com.kgJr.bhandara.ui.layouts.utils.base64ToBitmap
import com.kgJr.bhandara.ui.layouts.utils.dayMapper
import com.kgJr.bhandara.ui.theme.DeepPink
import com.kgJr.bhandara.ui.theme.RichAccent
import com.kgJr.bhandara.ui.theme.TrueWhite

@Composable
fun BhandaraListItem(
    bhandara: BhandaraDto,
    modifier: Modifier = Modifier,
    onOpenClick:() -> Unit
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Box {
                bhandara.image?.let { base64String ->
                    val decodedBitmap = base64ToBitmap(base64String)
                    decodedBitmap?.let { bitmap ->
                        Image(
                            bitmap = bitmap.asImageBitmap(),
                            contentDescription = "Bhandara Image",
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(150.dp),
                            contentScale = ContentScale.Crop
                        )
                    }
                }
                Text(
                    modifier = Modifier
                        .clip(RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp))
                        .background(TrueWhite)
                        .padding(5.dp)
                        .align(Alignment.BottomStart),
                    text = dayMapper(bhandara.bhandaraType!!),
                    style = MaterialTheme.typography.bodySmall,
                    color = RichAccent,
                    fontWeight = FontWeight.SemiBold,

                )
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(16.dp)
            ) {

                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = bhandara.name!!,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = bhandara.description!!,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Added by ${bhandara.organizationName}",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.DarkGray
                    )

                    Button(
                        onClick = onOpenClick,
                        colors = ButtonDefaults.buttonColors(containerColor = TrueWhite),
                        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp),
                        shape = MaterialTheme.shapes.small,
                        elevation = ButtonDefaults.buttonElevation(defaultElevation = 10.dp)

                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.near_me),
                            contentDescription = "Direction",
                            tint = DeepPink ,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Open", color = Color.Black, style = MaterialTheme.typography.labelSmall)
                    }
                }
            }
        }
    }
}
