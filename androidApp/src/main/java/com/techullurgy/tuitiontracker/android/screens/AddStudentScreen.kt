package com.techullurgy.tuitiontracker.android.screens

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.techullurgy.tuitiontracker.android.MyApplicationTheme
import com.techullurgy.tuitiontracker.android.components.OptionsPickerField
import com.techullurgy.tuitiontracker.android.viewmodels.LoadingState
import com.techullurgy.tuitiontracker.android.viewmodels.StudentsViewModel

@Composable
fun AddStudentScreen(
    viewModel: StudentsViewModel,
    navigateBack: () -> Unit = {}
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {

        var studentName by rememberSaveable { mutableStateOf("") }
        var studentAge by rememberSaveable { mutableStateOf("") }
        var studentGender by rememberSaveable { mutableStateOf("") }
        var studentStandard by rememberSaveable { mutableStateOf("") }

        var hasError by rememberSaveable { mutableStateOf(false) }
        var errorMessage by rememberSaveable { mutableStateOf("") }

        var isLoading by rememberSaveable { mutableStateOf(false) }

        LaunchedEffect(key1 = Unit) {
            snapshotFlow {
                viewModel.loadingState
            }.collect {
                when(it) {
                    LoadingState.Undefined -> {
                        isLoading = false
                    }
                    LoadingState.Loading -> {
                        isLoading = true
                    }
                    LoadingState.Completed -> {
                        isLoading = false
                        navigateBack()
                        viewModel.reset()
                    }

                }
            }
        }

        val isAllFieldsValid: () -> Boolean = {
            isAllFieldsValid(name = studentName, age = studentAge, gender = studentGender, standard = studentStandard)
        }

        val getErrorMessage: () -> String = {
            getErrorMessage(
                name = studentName,
                age = studentAge,
                gender = studentGender,
                standard = studentStandard
            )
        }

        val addStudent: () -> Unit = {
            viewModel.addStudent(
                name = studentName,
                age = studentAge,
                gender = studentGender[0].toString(),
                standard = studentStandard
            )
        }

        val onSaveClick: () -> Unit = {
            if(isAllFieldsValid()) {
                hasError = false

                addStudent()
            } else {
                hasError = true
                errorMessage = getErrorMessage()
            }
        }

        val onErrorAlertDismiss: () -> Unit = {
            hasError = false
            errorMessage = ""
        }

        if(isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(
                    trackColor = MaterialTheme.colorScheme.primary
                )
            }
        } else {
            AddStudentScreen(
                studentName = studentName,
                studentAge = studentAge,
                studentGender = studentGender,
                studentStandard = studentStandard,
                hasError = hasError,
                errorMessage = errorMessage,
                onStudentNameChange = { studentName = it },
                onStudentAgeChange = { studentAge = it },
                onStudentGenderChange = { studentGender = it },
                onStudentStandardChange = { studentStandard = it },
                onSaveClick = onSaveClick,
                onErrorAlertDismiss = onErrorAlertDismiss,
                navigateBack = navigateBack
            )
        }
    }
}

@Composable
private fun AddStudentScreen(
    modifier: Modifier = Modifier,
    studentName: String = "",
    studentAge: String = "",
    studentGender: String = "",
    studentStandard: String = "",
    hasError: Boolean = false,
    errorMessage: String = "",
    onStudentNameChange: (String) -> Unit = {},
    onStudentAgeChange: (String) -> Unit = {},
    onStudentGenderChange: (String) -> Unit = {},
    onStudentStandardChange: (String) -> Unit = {},
    onErrorAlertDismiss: () -> Unit = {},
    onSaveClick: () -> Unit = {},
    navigateBack: () -> Unit = {}
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(32.dp)
    ) {
        OutlinedTextField(
            value = studentName,
            onValueChange = onStudentNameChange,
            placeholder = { Text("Enter Student Name") },
            modifier = Modifier.border(2.dp, color = MaterialTheme.colorScheme.onBackground)
        )
        OutlinedTextField(
            value = studentAge,
            onValueChange = onStudentAgeChange,
            placeholder = { Text("Enter Student Age") },
            modifier = Modifier.border(2.dp, color = MaterialTheme.colorScheme.onBackground)
        )

        OptionsPickerField(
            label = "Gender",
            options = listOf("Male", "Female"),
            value = studentGender,
            onValueChange = onStudentGenderChange,
            title = "Select Gender"
        )

        OptionsPickerField(
            label = "Standard",
            options = listOf("1","2","3","4","5","6","7","8","9","10","11","12"),
            value = studentStandard,
            onValueChange = onStudentStandardChange,
            title = "Select Standard"
        )

        Row {
            Button(onClick = onSaveClick) {
                Text(text = "Save")
            }
            Spacer(modifier = Modifier.width(30.dp))
            Button(
                onClick = navigateBack,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.error,
                    contentColor = MaterialTheme.colorScheme.onError
                )
            ) {
                Text(text = "Cancel")
            }
        }

        if(hasError) {
            AlertDialog(
                onDismissRequest = onErrorAlertDismiss,
                confirmButton = {},
                dismissButton = {
                    Text(
                        text = "OK",
                        color = MaterialTheme.colorScheme.secondary,
                        modifier = Modifier
                            .clickable(onClick = onErrorAlertDismiss)
                    )
                },
                properties = DialogProperties(
                    dismissOnBackPress = false,
                    dismissOnClickOutside = false
                ),
                text = { Text(text = errorMessage) },
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                textContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
            )
        }
    }
}

// TODO: We need to move this to Shared section
private fun isAllFieldsValid(
    name: String,
    age: String,
    gender: String,
    standard: String
): Boolean {
    if(name.isEmpty() || age.isEmpty() || gender.isEmpty() || standard.isEmpty()) return false

    try { age.toInt() } catch (_: Exception) { return false }

    if(gender != "Male" && gender != "Female") return false

    return true
}

private fun getErrorMessage(
    name: String,
    age: String,
    gender: String,
    standard: String
): String {
    if(name.isEmpty()) {
        return "Student name is mandatory"
    } else if(age.isEmpty()) {
        return "Student Age is mandatory"
    } else if(gender.isEmpty()) {
        return "Student Gender is mandatory"
    } else if(standard.isEmpty()) {
        return "Student Standard is mandatory"
    }

    try { age.toInt() } catch (_: Exception) { return "Age is invalid" }

    if(gender != "Male" && gender != "Female") return "Gender is invalid"

    return ""
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun AddStudentScreenPreview() {
    MyApplicationTheme {
        AddStudentScreen(
            navigateBack = {},
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp)
        )
    }
}