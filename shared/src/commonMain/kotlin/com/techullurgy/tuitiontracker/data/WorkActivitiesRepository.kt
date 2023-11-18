package com.techullurgy.tuitiontracker.data

import com.techullurgy.tuitiontracker.data.models.WorkActivity
import com.techullurgy.tuitiontracker.data.utils.toLocalDate
import com.techullurgy.tuitiontracker.data.utils.toMilliSeconds
import com.techullurgy.tuitiontracker.sqldelight.TestDatabase
import com.techullurgy.tuitiontracker.sqldelight.TuitionQueries
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext


interface WorkActivitiesRepository {
    @Throws(Exception::class)
    suspend fun getAllWorkActivities(): List<WorkActivity>

    @Throws(Exception::class)
    suspend fun addWorkActivity(workActivity: WorkActivity)

    @Throws(Exception::class)
    suspend fun getWorkActivityById(workActivityId: Long): WorkActivity?

    @Throws(Exception::class)
    suspend fun getAllIndividualWorkActivities(): List<WorkActivity>

    @Throws(Exception::class)
    suspend fun getAllGenderWorkActivities(): List<WorkActivity>

    @Throws(Exception::class)
    suspend fun getAllMaleWorkActivities(): List<WorkActivity>

    @Throws(Exception::class)
    suspend fun getAllFemaleWorkActivities(): List<WorkActivity>

    @Throws(Exception::class)
    suspend fun deleteWorkActivity(activityId: Long)
}

internal class WorkActivitiesRepositoryImpl(
    private val testDatabase: TestDatabase
): WorkActivitiesRepository {

    private val queries: TuitionQueries = testDatabase.tuitionQueries

    @Throws(Exception::class)
    override suspend fun getAllWorkActivities(): List<WorkActivity> {
        return withContext(Dispatchers.IO) {
            queries.getAllActivities { id, name, description, group_key, group_value, created_date, expiration_date ->
                WorkActivity(
                    id = id,
                    name = name,
                    description = description,
                    groupKey = group_key,
                    groupValue = group_value,
                    createdDate = created_date.toLocalDate(),
                    expirationDate = expiration_date.toLocalDate()
                )
            }.executeAsList()
        }
    }

    @Throws(Exception::class)
    override suspend fun addWorkActivity(workActivity: WorkActivity) {
        withContext(Dispatchers.IO) {
            testDatabase.transaction {
                queries.insertActivity(
                    name = workActivity.name,
                    description = workActivity.description,
                    group_key = workActivity.groupKey,
                    group_value = workActivity.groupValue,
                    created_date = workActivity.createdDate.toMilliSeconds(),
                    expiration_date = workActivity.expirationDate.toMilliSeconds()
                )

                val activityId = queries.getActivityIdFor(name = workActivity.name).executeAsOne()

                when(workActivity.groupKey) {
                    "INDIVIDUAL" -> {
                        val studentId = workActivity.groupValue.toLong()
                        queries.insertStudentActivity(student_id = studentId, activity_id = activityId)
                    }
                    "GENDER" -> {
                        val studentIds = queries.getStudentIdsByGender(gender = workActivity.groupValue).executeAsList()
                        studentIds.forEach { studentId ->
                            queries.insertStudentActivity(student_id = studentId, activity_id = activityId)
                        }
                    }
                    "ALL" -> {
                        val studentIds = queries.getAllStudentIds().executeAsList()
                        studentIds.forEach { studentId ->
                            queries.insertStudentActivity(student_id = studentId, activity_id = activityId)
                        }
                    }
                    "STANDARD" -> {
                        val studentIds = queries.getStudentIdsByStandard(standard = workActivity.groupValue).executeAsList()
                        studentIds.forEach { studentId ->
                            queries.insertStudentActivity(student_id = studentId, activity_id = activityId)
                        }
                    }
                }
            }
        }
    }

    override suspend fun getWorkActivityById(workActivityId: Long): WorkActivity? {
        return withContext(Dispatchers.IO) {
            queries.getActivityById(workActivityId) { id, name, description, group_key, group_value, created_date, expiration_date ->
                WorkActivity(
                    id = id,
                    name = name,
                    description = description,
                    groupKey = group_key,
                    groupValue = group_value,
                    createdDate = created_date.toLocalDate(),
                    expirationDate = expiration_date.toLocalDate()
                )
            }.executeAsOneOrNull()
        }
    }

    override suspend fun getAllIndividualWorkActivities(): List<WorkActivity> {
        TODO("Not yet implemented")
    }

    override suspend fun getAllGenderWorkActivities(): List<WorkActivity> {
        TODO("Not yet implemented")
    }

    override suspend fun getAllMaleWorkActivities(): List<WorkActivity> {
        TODO("Not yet implemented")
    }

    override suspend fun getAllFemaleWorkActivities(): List<WorkActivity> {
        TODO("Not yet implemented")
    }

    @Throws(Exception::class)
    override suspend fun deleteWorkActivity(activityId: Long) {
        withContext(Dispatchers.IO) {
            testDatabase.transaction {
                queries.deleteWorkActivity(activityId)
            }
        }
    }
}