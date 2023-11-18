package com.techullurgy.tuitiontracker.android.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.techullurgy.tuitiontracker.android.domain.Repositories
import com.techullurgy.tuitiontracker.data.StudentWorkActivitiesRepository
import com.techullurgy.tuitiontracker.data.StudentsRepository
import com.techullurgy.tuitiontracker.data.models.AssignedWorkActivity
import com.techullurgy.tuitiontracker.data.models.Student
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class StudentDetailsViewModel: ViewModel() {

    private val studentWorkActivitiesRepository: StudentWorkActivitiesRepository = Repositories.studentWorkActivitiesRepository
    private val studentsRepository: StudentsRepository = Repositories.studentsRepository

    private val _studentDetailsScreenUIState = MutableStateFlow(StudentDetailsScreenUIState())
    val studentDetailsScreenUIState: StateFlow<StudentDetailsScreenUIState>
        get() = _studentDetailsScreenUIState.asStateFlow()

    fun toggleCompletionStatusOfAssignedActivity(assignedActivity: AssignedWorkActivity) {
        viewModelScope.launch {
            try {
                studentWorkActivitiesRepository.updateActivityCompletionStatusForStudent(
                    studentId = assignedActivity.student.id,
                    activityId = assignedActivity.workActivity.id,
                    isCompleted = !assignedActivity.isCompleted
                )
                loadStudentAssignedActivities(studentId = assignedActivity.student.id)
            } catch (e: Exception) {
                println(e.localizedMessage)
            }
        }
    }

    suspend fun loadStudentDetails(studentId: Long) {
        try {
            val student = studentsRepository.getStudentById(studentId = studentId)
            _studentDetailsScreenUIState.update {
                it.copy(
                    student = student
                )
            }
            student?.let {
                loadStudentAssignedActivities(it.id)
            }
        } catch (e: Exception) {
            println(e.localizedMessage)
        }
    }

    private suspend fun loadStudentAssignedActivities(studentId: Long) {
        try {
            val assignedActivities = studentWorkActivitiesRepository.getAllActivitiesForStudent(studentId = studentId)
            _studentDetailsScreenUIState.update {
                it.copy(
                    assignedActivities = assignedActivities
                )
            }
        } catch (e: Exception) {
            println(e.localizedMessage)
        }
    }

    suspend fun refresh() {
        _studentDetailsScreenUIState.value.student?.let {
            loadStudentAssignedActivities(it.id)
        }
    }
}

data class StudentDetailsScreenUIState(
    val student: Student? = null,
    val assignedActivities: List<AssignedWorkActivity> = emptyList()
)