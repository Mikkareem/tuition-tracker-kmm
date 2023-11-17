package com.techullurgy.tuitiontracker.data

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.techullurgy.tuitiontracker.sqldelight.TestDatabase

actual class DatabaseDriverFactory(
    private val context: Context
) {
    actual fun createDatabaseDriver(): SqlDriver {
        return AndroidSqliteDriver(TestDatabase.Schema, context, "students.db")
    }
}