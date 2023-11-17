package com.techullurgy.tuitiontracker.android.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.techullurgy.tuitiontracker.android.domain.Repositories
import com.techullurgy.tuitiontracker.data.StudentWorkActivitiesRepository
import com.techullurgy.tuitiontracker.data.models.AssignedWorkActivity
import com.techullurgy.tuitiontracker.data.models.WorkActivity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class WorkActivityDetailsViewModel: ViewModel() {

    private val studentWorkActivitiesRepository: StudentWorkActivitiesRepository = Repositories.studentWorkActivitiesRepository

    private val _workActivityDetailsScreenUIState = MutableStateFlow(
        WorkActivityDetailsScreenUIState()
    )
    val workActivityDetailsScreenUIState: StateFlow<WorkActivityDetailsScreenUIState>
        get() = _workActivityDetailsScreenUIState.asStateFlow()

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

    suspend fun loadActivityAssignedStudents(activity: WorkActivity) {
        try {
            val assignedActivities = studentWorkActivitiesRepository.getAssignedStudentsForActivity(activityId = activity.id)
            _workActivityDetailsScreenUIState.update {
                it.copy(
                    assignedActivities = assignedActivities
                )
            }
        } catch (e: Exception) {
            println(e.localizedMessage)
        }
    }
}

data class WorkActivityDetailsScreenUIState(
    val assignedActivities: List<AssignedWorkActivity> = emptyList()
)