package com.techullurgy.tuitiontracker.data

import com.techullurgy.tuitiontracker.sqldelight.TestDatabase

class DatabaseApi(
    databaseDriverFactory: DatabaseDriverFactory
) {
    private val testDatabase: TestDatabase = TestDatabase(databaseDriverFactory.createDatabaseDriver())

    private val _studentsRepository: StudentsRepository = StudentsRepositoryImpl(testDatabase)
    private val _workActivitiesRepository: WorkActivitiesRepository = WorkActivitiesRepositoryImpl(testDatabase)
    private val _studentWorkActivitiesRepository: StudentWorkActivitiesRepository = StudentWorkActivitiesRepositoryImpl(testDatabase)

    val studentsRepository: StudentsRepository
        get() = _studentsRepository

    val workActivitiesRepository: WorkActivitiesRepository
        get() = _workActivitiesRepository

    val studentWorkActivitiesRepository: StudentWorkActivitiesRepository
        get() = _studentWorkActivitiesRepository
}