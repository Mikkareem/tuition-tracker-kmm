package com.techullurgy.tuitiontracker.data

import com.techullurgy.tuitiontracker.data.models.AssignedWorkActivity
import com.techullurgy.tuitiontracker.data.models.Student
import com.techullurgy.tuitiontracker.data.models.WorkActivity
import com.techullurgy.tuitiontracker.data.utils.toBoolean
import com.techullurgy.tuitiontracker.data.utils.toLocalDate
import com.techullurgy.tuitiontracker.data.utils.toLong
import com.techullurgy.tuitiontracker.sqldelight.TestDatabase
import com.techullurgy.tuitiontracker.sqldelight.TuitionQueries
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext

interface StudentWorkActivitiesRepository {
    @Throws(Exception::class)
    suspend fun getAssignedStudentsCountForActivity(activityId: Long): Int

    @Throws(Exception::class)
    suspend fun getAssignedStudentsForActivity(activityId: Long): List<AssignedWorkActivity>

    @Throws(Exception::class)
    suspend fun getAllActivitiesForStudent(studentId: Long): List<AssignedWorkActivity>

//    @Throws(Exception::class)
//    suspend fun getAllCompletedActivitiesForStudent(studentId: Long): List<AssignedWorkActivity>
//
//    @Throws(Exception::class)
//    suspend fun getAllIncompleteActivitiesForStudent(studentId: Long): List<AssignedWorkActivity>

    @Throws(Exception::class)
    suspend fun updateActivityCompletionStatusForStudent(studentId: Long, activityId: Long, isCompleted: Boolean)

    @Throws(Exception::class)
    suspend fun deleteAssignedActivity(assignedActivity: Long)
}

internal class StudentWorkActivitiesRepositoryImpl(
    private val testDatabase: TestDatabase
): StudentWorkActivitiesRepository {

    private val queries: TuitionQueries = testDatabase.tuitionQueries

    @Throws(Exception::class)
    override suspend fun getAssignedStudentsCountForActivity(activityId: Long): Int {
        return getAssignedStudentsForActivity(activityId).size
    }

    @Throws(Exception::class)
    override suspend fun getAssignedStudentsForActivity(activityId: Long): List<AssignedWorkActivity> {
        return withContext(Dispatchers.IO) {
            val activity = queries.getActivityById(activityId) { id, name, description, group_key, group_value, created_date, expiration_date ->
                WorkActivity(
                    id = id,
                    name = name,
                    description = description,
                    groupKey = group_key,
                    groupValue = group_value,
                    createdDate = created_date.toLocalDate(),
                    expirationDate = expiration_date.toLocalDate()
                )
            }.executeAsOne()

            val assignedStudents = queries.getAllAssignedStudentsForActivity(activityId).executeAsList()

            assignedStudents.map {
                val student = queries.getStudentById(it.student_id) { id, name, age, gender, standard, remarks ->
                    Student(
                        id = id,
                        name = name,
                        age = age.toInt(),
                        gender = gender,
                        standard = standard,
                        remarks = remarks
                    )
                }.executeAsOne()

                AssignedWorkActivity(
                    student = student,
                    workActivity = activity,
                    isCompleted = it.is_completed.toBoolean()
                )
            }
        }
    }

    @Throws(Exception::class)
    override suspend fun getAllActivitiesForStudent(studentId: Long): List<AssignedWorkActivity> {
        return withContext(Dispatchers.IO) {
            val student = queries.getStudentById(studentId) { id, name, age, gender, standard, remarks ->
                Student(
                    id = id,
                    name = name,
                    age = age.toInt(),
                    gender = gender,
                    standard = standard,
                    remarks = remarks
                )
            }.executeAsOne()


            val assignedActivities = queries.getAllAssignedActivitiesForStudent(studentId).executeAsList()

            assignedActivities.map {
                val activity = queries.getActivityById(it.activity_id) { id, name, description, group_key, group_value, created_date, expiration_date ->
                    WorkActivity(
                        id = id,
                        name = name,
                        description = description,
                        groupKey = group_key,
                        groupValue = group_value,
                        createdDate = created_date.toLocalDate(),
                        expirationDate = expiration_date.toLocalDate()
                    )
                }.executeAsOne()

                AssignedWorkActivity(
                    student = student,
                    workActivity = activity,
                    isCompleted = it.is_completed.toBoolean()
                )
            }
        }
    }

    @Throws(Exception::class)
    override suspend fun updateActivityCompletionStatusForStudent(
        studentId: Long,
        activityId: Long,
        isCompleted: Boolean
    ) {
        withContext(Dispatchers.IO) {
            testDatabase.transaction {
                queries.updateActivityCompletionStatusForStudent(
                    isCompleted = isCompleted.toLong(),
                    studentId = studentId,
                    activityId = activityId
                )
            }
        }
    }

    @Throws(Exception::class)
    override suspend fun deleteAssignedActivity(assignedActivity: Long) {
        withContext(Dispatchers.IO) {
            testDatabase.transaction {
                queries.deleteStudentActivity(assignedActivity)
            }
        }
    }
}