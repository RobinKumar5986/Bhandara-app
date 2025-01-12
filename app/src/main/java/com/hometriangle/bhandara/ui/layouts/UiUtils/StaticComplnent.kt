package com.hometriangle.bhandara.ui.layouts.UiUtils

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hometriangle.bhandara.R
import com.hometriangle.bhandara.ui.theme.SpaceExtraLarge
import com.hometriangle.bhandara.ui.theme.SpaceHuge
import com.hometriangle.bhandara.ui.theme.SpaceMedium
import com.hometriangle.bhandara.ui.theme.SpaceSmall
import com.hometriangle.bhandara.ui.theme.md_theme_light_primary
import com.hometriangle.bhandara.ui.theme.md_theme_light_tertiaryContainer

@Composable
fun StandardTextFieldNewTwo(
    text: String,
    onValueChange: (String) -> Unit,
    hint: String,
    maxLength: Int,
    leadingIcon: Int,
    keyboardType: KeyboardType
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Transparent)
            .border(1.dp, Color.Gray, RoundedCornerShape(4.dp))
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Row(
                modifier = Modifier.padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = leadingIcon),
                    contentDescription = "Flag",
                    modifier = Modifier
                        .size(24.dp)
                        .clip(RoundedCornerShape(4.dp))
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "+91", style = MaterialTheme.typography.bodyMedium)
                Spacer(modifier = Modifier.width(8.dp))
            }
            Spacer(
                modifier = Modifier
                    .width(1.dp)
                    .height(50.dp)
                    .background(Color.Gray)
            )
            Box(
                modifier = Modifier
                    .weight(1f)
                    .padding(12.dp)
            ) {
                if (text.isEmpty()) {
                    Text(
                        text = hint,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray,
                        modifier = Modifier.padding(4.dp)
                    )
                }
                BasicTextField(
                    value = text,
                    onValueChange = {
                        if (it.length <= maxLength) onValueChange(it.filter { char -> char.isDigit() })
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
                    singleLine = true,
                    textStyle = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.Normal,
                        letterSpacing = 3.sp
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Transparent)
                        .padding(4.dp),

                    )
            }
        }
    }
}
@Composable
fun FeaturesCard() {
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = SpaceExtraLarge)
            .padding(vertical = SpaceMedium)
    ) {
        Column(
            modifier = Modifier
                .background(md_theme_light_tertiaryContainer)
                .padding(SpaceMedium),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            FeatureItem(
                text = "Verified and vetted pros",
                icon = R.drawable.ic_verified_icon,
                iconTint = md_theme_light_primary
            )
            FeatureItem(
                text = "Matched to your need",
                icon = R.drawable.ic_check_menu,
                iconTint = md_theme_light_primary
            )
            FeatureItem(
                text = "No Commission",
                icon = R.drawable.ic_rupee_icon,
                iconTint = md_theme_light_primary
            )
            FeatureItem(
                text = "24/7 Customer Support",
                icon = R.drawable.ic_headphone_icon,
                iconTint = md_theme_light_primary
            )
        }
    }
}


@Composable
fun FeatureItem(text: String, icon: Int, iconTint: Color) {
    Row(
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = SpaceSmall)
            .padding(start = SpaceHuge)
    ) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = null,
            tint = iconTint,
            modifier = Modifier.size(18.dp)
        )
        Spacer(modifier = Modifier.width(SpaceSmall))
        Text(
            text = text,
            style = MaterialTheme.typography.bodySmall,
            fontWeight = FontWeight.Normal
        )
    }
}