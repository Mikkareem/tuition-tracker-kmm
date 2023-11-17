package com.techullurgy.tuitiontracker.android.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.techullurgy.tuitiontracker.android.domain.Repositories
import com.techullurgy.tuitiontracker.data.StudentsRepository
import com.techullurgy.tuitiontracker.data.models.Student
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.lang.Exception

class StudentsViewModel: ViewModel() {

    private val studentsRepository: StudentsRepository = Repositories.studentsRepository

    private val _studentsScreenUIState = MutableStateFlow(StudentsScreenUIState())
    val studentsScreenUIState: StateFlow<StudentsScreenUIState>
        get() = _studentsScreenUIState.asStateFlow()

    var loadingState by mutableStateOf<LoadingState>(LoadingState.Undefined)
        private set

    fun addStudent(name: String, age: String, gender: String, standard: String) {
        val studentAge = try { age.toInt() } catch (e: Exception) { return }

        loadingState = LoadingState.Loading
        viewModelScope.launch {
            try {
                studentsRepository.addStudent(
                    student = Student(
                        id = -1L,
                        name = name,
                        age = studentAge,
                        gender = gender,
                        standard = standard
                    )
                )
                loadStudents()
                loadingState = LoadingState.Completed
            } catch(e: Exception) {
                println(e.localizedMessage)
                loadingState = LoadingState.Completed
            }
        }
    }

    suspend fun loadStudents() {
        try {
            val students = studentsRepository.getAllStudents()
            _studentsScreenUIState.update {
                it.copy(
                    students = students
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

data class StudentsScreenUIState(
    val students: List<Student> = emptyList()
)

sealed interface LoadingState {
    data object Undefined: LoadingState
    data object Loading: LoadingState
    data object Completed: LoadingState
}