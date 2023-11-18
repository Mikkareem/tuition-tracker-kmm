package com.techullurgy.tuitiontracker.android.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.techullurgy.tuitiontracker.android.domain.Repositories
import com.techullurgy.tuitiontracker.data.WorkActivitiesRepository
import com.techullurgy.tuitiontracker.data.models.WorkActivity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate

class WorkActivitiesViewModel: ViewModel() {

    private val workActivitiesRepository: WorkActivitiesRepository = Repositories.workActivitiesRepository

    private val _workActivitiesScreenState = MutableStateFlow(WorkActivitiesScreenState())
    val workActivitiesScreenState: StateFlow<WorkActivitiesScreenState>
        get() = _workActivitiesScreenState.asStateFlow()

    var loadingState: LoadingState by mutableStateOf(LoadingState.Undefined)

    fun addWorkActivity(name: String, description: String, groupKey: String, groupValue: String, createdDate: LocalDate, expirationDate: LocalDate) {
        val workActivity = WorkActivity(
            id = -1,
            name = name,
            description = description,
            groupKey = groupKey,
            groupValue = if(groupKey == "GENDER") groupValue[0].toString() else groupValue,
            createdDate = createdDate,
            expirationDate = expirationDate
        )

        loadingState = LoadingState.Loading
        viewModelScope.launch {
            assignThisWorkActivity(workActivity = workActivity)
            loadingState = LoadingState.Completed
        }
    }

    private suspend fun assignThisWorkActivity(workActivity: WorkActivity) {
        try {
            workActivitiesRepository.addWorkActivity(workActivity)
            loadWorkActivities()
        } catch(e: Exception) {
            println(e.localizedMessage)
        }
    }

    suspend fun loadWorkActivities() {
        try {
            val workActivities = workActivitiesRepository.getAllWorkActivities()
            _workActivitiesScreenState.update {
                it.copy(
                    activities = workActivities
                )
            }
        } catch (e: Exception) {
            println(e.localizedMessage)
        }
    }

    fun reset() {
        loadingState = LoadingState.Undefined
    }
}

data class WorkActivitiesScreenState(
    val activities: List<WorkActivity> = emptyList()
)