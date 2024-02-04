package com.mobilebreakero.core_ui.components

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

fun calculateEndDate(startDate: String, duration: String): String {
    val sdf = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
    val calendar = Calendar.getInstance()

    calendar.time = sdf.parse(startDate) ?: Date()
    calendar.add(Calendar.DAY_OF_MONTH, duration.toInt())

    return sdf.format(calendar.time)
}
