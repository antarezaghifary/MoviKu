package com.gibox.moviku.util

import android.widget.TextView
import java.text.SimpleDateFormat
import java.util.*

fun TextView.dateFormat(date: String, input: String, output: String) {
    var format = SimpleDateFormat(input, Locale.getDefault())
    var newDate: Date? = null

    newDate = format.parse(date)

    format = SimpleDateFormat(output, Locale.getDefault())

    this.text = format.format(newDate)
}