package com.techullurgy.tuitiontracker.data.models

import kotlinx.datetime.LocalDate

data class WorkActivity(
    val id: Long = 0,
    val name: String,
    val description: String = "",
    val groupKey: String,
    val groupValue: String,
    val createdDate: LocalDate,
    val expirationDate: LocalDate
)
