package com.techullurgy.tuitiontracker.data.models

data class Student(
    val id: Long,
    val name: String,
    val age: Int,
    val gender: String,
    val standard: String,
    val remarks: String? = null
)
