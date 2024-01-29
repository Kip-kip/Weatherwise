package com.example.weatherwise.presentation.utility

import java.lang.String.format
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object Utils {
    fun convertDateStringToDayAndTime(dateString: String): String {
        try {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
            val date = inputFormat.parse(dateString)

            val dayFormat = SimpleDateFormat("EEEE", Locale.getDefault())
            val day = dayFormat.format(date)

            val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
            val time = timeFormat.format(date)

            return "$day $time"
        } catch (e: Exception) {
            e.printStackTrace()
            return "Error"
        }
    }

    fun convertLongTimeStampToDayAndTime(currentTimeMillis: Long): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        return sdf.format(Date(currentTimeMillis))
    }
}