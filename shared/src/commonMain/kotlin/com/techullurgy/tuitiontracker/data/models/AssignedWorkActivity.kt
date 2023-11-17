package com.techullurgy.tuitiontracker.data.models


data class AssignedWorkActivity(
    val student: Student,
    val workActivity: WorkActivity,
    val isCompleted: Boolean
)
