package com.kgJr.bhandara.ui.layouts.UiUtils

import android.annotation.SuppressLint
import android.app.TimePickerDialog
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

/**
 * @return returns the time in 12:00 hour format.
 * */
@SuppressLint("NewApi")
@Composable
fun TimePickerField(
    localDateTime: LocalDateTime?,
    placeHolder: String,
    onTimeSelected: (LocalDateTime) -> Unit
) {
    val context = LocalContext.current

    val timePickerDialog = remember {
        TimePickerDialog(
            context,
            { _, hour, minute ->
                val selectedDateTime = LocalDateTime.now()
                    .withHour(hour)
                    .withMinute(minute)
                    .withSecond(0) // Ensure consistency

                onTimeSelected(selectedDateTime)
            },
            12, 0, false // Default time: 12:00 PM, 12-hour format
        )
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.LightGray.copy(alpha = 0.3f), shape = RoundedCornerShape(8.dp))
            .clickable { timePickerDialog.show() }
            .padding(vertical = 12.dp, horizontal = 16.dp)
    ) {
        Text(
            text = localDateTime?.format(DateTimeFormatter.ofPattern("hh:mm a", Locale.getDefault()))
                ?: placeHolder,
            fontSize = 16.sp,
            color = if (localDateTime == null) Color.Gray else Color.Black
        )
    }
}
