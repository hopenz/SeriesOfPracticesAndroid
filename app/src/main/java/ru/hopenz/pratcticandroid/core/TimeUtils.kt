package ru.hopenz.pratcticandroid.core

import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeParseException

fun tryParseServerDate(dateTime: String?): LocalDateTime? {
    return try {
        val zonedDateTime = java.time.ZonedDateTime.parse(dateTime)
        zonedDateTime.withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime()
    } catch (e: DateTimeParseException) {
        null
    }
}

fun LocalDateTime?.orNow(): LocalDateTime {
    return this ?: LocalDateTime.now()
}