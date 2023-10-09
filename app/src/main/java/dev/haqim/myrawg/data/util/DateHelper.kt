package dev.haqim.myrawg.data.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun convertDate(dateInput: String, format: String ="yyyy-MM-dd'T'HH:mm:ss"): String {
    val inputFormat = SimpleDateFormat(format, Locale.US)
    val date = inputFormat.parse(dateInput)
    val outputFormat = SimpleDateFormat("MMM d, yyyy", Locale.US)
    return date?.let { outputFormat.format(it) } ?: "Jan 1, 1999"
}
