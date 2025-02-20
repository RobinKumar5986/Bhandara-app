package com.hometriangle.bhandara.ui.layouts.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun dayMapper(key: String): String {
    val map = mapOf(
        "singleDay" to "One Day",
        "everyDay" to "Everyday"
    )
    return map[key] ?: "Invalid day"
}
fun longToDateConverter(timestamp: Long): String {
    val sdf = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
    val date = Date(timestamp)
    return sdf.format(date)
}