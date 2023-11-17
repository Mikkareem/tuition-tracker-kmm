package com.techullurgy.tuitiontracker.data

import app.cash.sqldelight.db.SqlDriver

expect class DatabaseDriverFactory {
    fun createDatabaseDriver(): SqlDriver
}