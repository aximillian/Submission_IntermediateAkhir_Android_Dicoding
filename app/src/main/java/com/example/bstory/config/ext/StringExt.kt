package com.example.bstory.config.ext

import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun String.formatDate(): String {

    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    val dateTime = LocalDate.parse(this, formatter)
    val formattedDate = dateTime.format(DateTimeFormatter.ofPattern("MMM dd, yyyy"))
    return formattedDate.toString()
}