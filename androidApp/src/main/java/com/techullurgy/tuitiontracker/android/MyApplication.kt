package com.techullurgy.tuitiontracker.android

import android.app.Application
import com.techullurgy.tuitiontracker.data.DatabaseApi
import com.techullurgy.tuitiontracker.data.DatabaseDriverFactory

class MyApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        databaseApi = DatabaseApi(DatabaseDriverFactory(this))
    }

    companion object {
        lateinit var databaseApi: DatabaseApi
            private set
    }
}