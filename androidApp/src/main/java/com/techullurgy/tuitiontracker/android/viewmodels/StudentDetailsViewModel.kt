package com.techullurgy.tuitiontracker.android.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.techullurgy.tuitiontracker.android.domain.Repositories
import com.techullurgy.tuitiontracker.data.StudentWorkActivitiesRepository
import com.techullurgy.tuitiontracker.data.models.AssignedWorkActivity
import com.techullurgy.tuitiontracker.data.models.Student
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.lang.Exception

class StudentDetailsViewModel: ViewModel() {

    private val studentWorkActivitiesRepository: StudentWorkActivitiesRepository = Repositories.studentWorkActivitiesRepository

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
            } catch (e: Exception) {
                println(e.localizedMessage)
            }
        }
    }

    suspend fun loadStudentAssignedActivities(student: Student) {
        try {
            val assignedActivities = studentWorkActivitiesRepository.getAllActivitiesForStudent(studentId = student.id)
            _studentDetailsScreenUIState.update {
                it.copy(
                    assignedActivities = assignedActivities
                )
            }
        } catch (e: Exception) {
            println(e.localizedMessage)
        }
    }
}

data class StudentDetailsScreenUIState(
    val assignedActivities: List<AssignedWorkActivity> = emptyList()
)