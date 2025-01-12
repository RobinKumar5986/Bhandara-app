package com.hometriangle.bhandara.ui.layouts.UiUtils

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme

import androidx.compose.runtime.Composable

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment

@Composable
fun OTPTextField(  boxWidth: Dp = 50.dp,
                   boxHeight: Dp = 50.dp,
                   length:Int,value: String,
                   onValueChange: (String) -> Unit
){
    val spaceBetweenBoxes = 8.dp
    BasicTextField(
        value = value,
        onValueChange = { newValue ->
            if(newValue.length <= length){
                onValueChange(newValue)
            }
        },
        textStyle = MaterialTheme.typography.bodyMedium.copy(textAlign = TextAlign.Center),
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
        keyboardActions = KeyboardActions.Default,
        decorationBox = {
            Row(
                modifier = Modifier
                    .size(width = (boxWidth + spaceBetweenBoxes) * length, height = boxHeight),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                repeat(length) { index ->
                    Box(
                        modifier = Modifier
                            .size(boxWidth, boxHeight)
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color.White)
                            .border(1.dp, Color.Gray, RoundedCornerShape(12.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text =  value.getOrNull(index)?.toString() ?: "",
                            style = MaterialTheme.typography.bodyMedium.copy(textAlign = TextAlign.Center)
                        )
                    }
                }
            }
        }
    )
}