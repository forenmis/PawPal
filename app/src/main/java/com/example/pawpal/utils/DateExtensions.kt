package com.example.pawpal.utils

import android.widget.EditText
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

fun Date.getDateInStr(): String {
    val simpleDateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
    return simpleDateFormat.format(this)
}

fun Date.getTimeInStr(): String {
    val simpleDateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
    return simpleDateFormat.format(this)
}

fun Date.isTheSameDay(date: Date?): Boolean {
    date ?: return false
    val calendar1 = Calendar.getInstance()
    calendar1.time = this
    val calendar2 = Calendar.getInstance()
    calendar2.time = date
    return calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR) &&
            calendar1.get(Calendar.DAY_OF_YEAR) == calendar2.get(Calendar.DAY_OF_YEAR)
}

fun Calendar.getTimeInStr(): String {
    val simpleDateFormat = SimpleDateFormat("HH:mm", Locale.ENGLISH)
    return simpleDateFormat.format(time)
}

fun String.parseDate(): Date {
    val simpleDateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
    return simpleDateFormat.parse(this) ?: throw Exception()
}

fun String.parseTime(): Date {
    val simpleDateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
    return simpleDateFormat.parse(this) ?: throw Exception()
}

fun getFullDate(date: String, time: String): Date {
    val simpleDateFormat = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())
    return simpleDateFormat.parse("$date $time") ?: throw Exception()
}

fun extractDate(etDate: EditText, etTime: EditText): Date {
    val date = etDate.text.toString()
    val time = etTime.text.toString()
    val simpleDateFormat = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())
    return simpleDateFormat.parse("$date $time") ?: throw Exception()

}

fun Date.fullString(): String {
    val simpleDateFormat = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())
    return simpleDateFormat.format(this)
}