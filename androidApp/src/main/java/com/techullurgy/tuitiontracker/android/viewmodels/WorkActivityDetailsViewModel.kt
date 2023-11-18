package com.techullurgy.tuitiontracker.android.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.techullurgy.tuitiontracker.android.domain.Repositories
import com.techullurgy.tuitiontracker.data.StudentWorkActivitiesRepository
import com.techullurgy.tuitiontracker.data.WorkActivitiesRepository
import com.techullurgy.tuitiontracker.data.models.AssignedWorkActivity
import com.techullurgy.tuitiontracker.data.models.WorkActivity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class WorkActivityDetailsViewModel: ViewModel() {

    private val studentWorkActivitiesRepository: StudentWorkActivitiesRepository = Repositories.studentWorkActivitiesRepository
    private val workActivitiesRepository: WorkActivitiesRepository = Repositories.workActivitiesRepository

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
                loadActivityAssignedStudents(assignedActivity.workActivity.id)
            } catch (e: Exception) {
                println(e.localizedMessage)
            }
        }
    }

    suspend fun loadWorkActivityDetails(activityId: Long) {
        try {
            val activity = workActivitiesRepository.getWorkActivityById(workActivityId = activityId)
            _workActivityDetailsScreenUIState.update {
                it.copy(
                    activity = activity
                )
            }
            activity?.let {
                loadActivityAssignedStudents(it.id)
            }
        } catch(e: Exception) {
            println(e.localizedMessage)
        }
    }

    private suspend fun loadActivityAssignedStudents(activityId: Long) {
        try {
            val assignedStudents = studentWorkActivitiesRepository.getAssignedStudentsForActivity(activityId = activityId)
            _workActivityDetailsScreenUIState.update {
                it.copy(
                    assignedStudents = assignedStudents
                )
            }
        } catch (e: Exception) {
            println(e.localizedMessage)
        }
    }
}

data class WorkActivityDetailsScreenUIState(
    val activity: WorkActivity? = null,
    val assignedStudents: List<AssignedWorkActivity> = emptyList()
)