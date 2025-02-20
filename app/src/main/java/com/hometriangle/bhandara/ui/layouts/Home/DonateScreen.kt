package com.hometriangle.bhandara.ui.layouts.Home

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hometriangle.bhandara.R

@Composable
fun DonateScreen() {
    val context = LocalContext.current
    val scrollState = rememberScrollState()
    val clipboardManager = LocalClipboardManager.current
    val upiId = "rkrobin6550@oksbi"

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF0F0F0))
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.TopStart)
                .verticalScroll(scrollState)
                .fillMaxWidth()
        ) {
            Text(
                text = "Why Donate to Us?",
                style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(bottom = 8.dp),
                color = Color.Black
            )
            Text(
                text = """
                    This application is created and maintained by an independent developer. Currently, the costs to keep the app running are fully supported by the developer, which is not financially sustainable in the long term. To ensure the app remains live and continuously improved, we rely on the generous support of users like you. 

                    Your contribution will help cover the ongoing operational costs. Any additional funds raised will go beyond just sustaining the app—they will be used to support charitable causes such as feeding the hungry and helping those in need. Your donation not only ensures the app stays active but also makes a positive impact on the community.
                """.trimIndent(),
                style = TextStyle(fontSize = 16.sp),
                color = Color.DarkGray
            )

            Spacer(modifier = Modifier.height(24.dp))

            // UPI ID with copy icon
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .clickable {
                        clipboardManager.setText(AnnotatedString(upiId))
                        Toast.makeText(context, "UPI ID copied to clipboard", Toast.LENGTH_SHORT).show()
                    }
                    .padding(16.dp)
                    .background(
                        Color(0xFFE0E0E0),
                        shape = MaterialTheme.shapes.medium
                    )
                    .padding(16.dp)
            ) {
                Text(
                    text = "UPI ID: $upiId",
                    style = TextStyle(fontSize = 16.sp),
                    modifier = Modifier.weight(1f),
                    color = Color.Black
                )
                Image(
                    painter = painterResource(id = R.drawable.copy_past),
                    contentDescription = "Copy UPI ID",
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}
