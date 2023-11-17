package com.techullurgy.tuitiontracker.android.domain

import com.techullurgy.tuitiontracker.android.MyApplication

object Repositories {
    val studentsRepository by lazy {
        MyApplication.databaseApi.studentsRepository
    }
    val workActivitiesRepository by lazy {
        MyApplication.databaseApi.workActivitiesRepository
    }
    val studentWorkActivitiesRepository by lazy {
        MyApplication.databaseApi.studentWorkActivitiesRepository
    }
}