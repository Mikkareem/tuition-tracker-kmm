package com.techullurgy.tuitiontracker.data.utils

import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import kotlinx.datetime.toLocalDateTime

internal fun LocalDate.toMilliSeconds(): Long {
    return atStartOfDayIn(TimeZone.currentSystemDefault()).toEpochMilliseconds()
}

internal fun Long.toLocalDate(): LocalDate {
    return Instant.fromEpochMilliseconds(this).toLocalDateTime(TimeZone.currentSystemDefault()).date
}

internal fun Long.toBoolean(): Boolean {
    return this != 0L
}

internal fun Boolean.toLong(): Long {
    return if(this) 1L else 0L
}