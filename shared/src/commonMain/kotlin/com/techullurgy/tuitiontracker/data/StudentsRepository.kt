package com.techullurgy.tuitiontracker.data

import com.techullurgy.tuitiontracker.data.models.Student
import com.techullurgy.tuitiontracker.sqldelight.TestDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext

interface StudentsRepository {
    @Throws(Exception::class)
    suspend fun getAllStudents(): List<Student>

    @Throws(Exception::class)
    suspend fun addStudent(student: Student)

    @Throws(Exception::class)
    suspend fun getStudentById(studentId: Long): Student?

    @Throws(Exception::class)
    suspend fun getAllStudentNames(): List<String>

    @Throws(Exception::class)
    suspend fun updateStudentRemarks(studentId: Long, remarks: String)

    @Throws(Exception::class)
    suspend fun deleteStudent(studentId: Long)
}

internal class StudentsRepositoryImpl(
    private val testDatabase: TestDatabase
): StudentsRepository {
    private val queries = testDatabase.tuitionQueries

    @Throws(Exception::class)
    override suspend fun getAllStudents(): List<Student> {
        return withContext(Dispatchers.IO) {
            queries.getAllStudents { id, name, age, gender, standard, remarks ->
                Student(
                    id = id,
                    name = name,
                    age = age.toInt(),
                    gender = gender,
                    standard = standard,
                    remarks = remarks
                )
            }.executeAsList()
        }
    }

    @Throws(Exception::class)
    override suspend fun addStudent(student: Student) {
        withContext(Dispatchers.IO) {
            queries.insertStudent(
                name = student.name,
                age = student.age.toLong(),
                gender = student.gender,
                standard = student.standard
            )
        }
    }

    @Throws(Exception::class)
    override suspend fun getStudentById(studentId: Long): Student? {
        return withContext(Dispatchers.IO) {
            queries.getStudentById(studentId) { id, name, age, gender, standard, remarks ->
                Student(
                    id = id,
                    name = name,
                    age = age.toInt(),
                    gender = gender,
                    standard = standard,
                    remarks = remarks
                )
            }.executeAsOneOrNull()
        }
    }

    @Throws(Exception::class)
    override suspend fun getAllStudentNames(): List<String> {
        return withContext(Dispatchers.IO) {
            queries.getAllStudentNames().executeAsList()
        }
    }

    @Throws(Exception::class)
    override suspend fun updateStudentRemarks(studentId: Long, remarks: String) {
        withContext(Dispatchers.IO) {
            testDatabase.transaction {
                queries.updateStudentRemarks(studentId = studentId, remarks = remarks)
            }
        }
    }

    @Throws(Exception::class)
    override suspend fun deleteStudent(studentId: Long) {
        withContext(Dispatchers.IO) {
            testDatabase.transaction {
                queries.deleteStudent(studentId)
            }
        }
    }
}