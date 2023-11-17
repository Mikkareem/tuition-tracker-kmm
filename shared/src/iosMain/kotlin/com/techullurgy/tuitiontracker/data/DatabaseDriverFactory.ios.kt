package com.techullurgy.tuitiontracker.data

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import com.techullurgy.tuitiontracker.sqldelight.TestDatabase

actual class DatabaseDriverFactory {
    actual fun createDatabaseDriver(): SqlDriver {
        return NativeSqliteDriver(TestDatabase.Schema, "students.db")
    }
}