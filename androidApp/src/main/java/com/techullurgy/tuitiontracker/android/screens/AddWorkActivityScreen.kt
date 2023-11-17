package com.techullurgy.tuitiontracker.android.screens

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import com.techullurgy.tuitiontracker.android.components.ConstantPickerField
import com.techullurgy.tuitiontracker.android.components.ODatePickerField
import com.techullurgy.tuitiontracker.android.components.OptionsPickerField
import com.techullurgy.tuitiontracker.android.viewmodels.LoadingState
import com.techullurgy.tuitiontracker.android.viewmodels.WorkActivitiesViewModel
import java.time.LocalDate as JLocalDate
import kotlinx.datetime.toLocalDate

@Composable
fun AddWorkActivityScreen(
    viewModel: WorkActivitiesViewModel,
    navigateBack: () -> Unit = {},
    isToAddIndividualActivity: Boolean = false,
    activityStudentId: Long? = null
) {

    require(
        (!isToAddIndividualActivity && activityStudentId == null) ||
                (isToAddIndividualActivity && activityStudentId != null)
    ) {
        "Called from Wrong place with wrong arguments"
    }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {

        var activityName by rememberSaveable { mutableStateOf("") }
        var activityDescription by rememberSaveable { mutableStateOf("") }
        var activityGroupKey by rememberSaveable(isToAddIndividualActivity) {
            if(!isToAddIndividualActivity) {
                mutableStateOf("")
            } else {
                mutableStateOf("INDIVIDUAL")
            }
        }
        var activityGroupValue by rememberSaveable(isToAddIndividualActivity) {
            if(!isToAddIndividualActivity) {
                mutableStateOf("")
            } else {
                mutableStateOf(activityStudentId!!.toString())
            }
        }
        var activityCreationDate by rememberSaveable { mutableStateOf(JLocalDate.now().toString()) }
        var activityExpirationDate by rememberSaveable { mutableStateOf(JLocalDate.now().toString()) }

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
            isAllFieldsValid(
                name = activityName,
                description= activityDescription,
                groupKey= activityGroupKey,
                groupValue= activityGroupValue,
                creationDate= activityCreationDate,
                expirationDate= activityExpirationDate
            )
        }

        val getErrorMessage: () -> String = {
            getErrorMessage(
                name = activityName,
                description= activityDescription,
                groupKey= activityGroupKey,
                groupValue= activityGroupValue,
                creationDate= activityCreationDate,
                expirationDate= activityExpirationDate
            )
        }

        val addStudent: () -> Unit = {
            viewModel.addWorkActivity(name = activityName,
                description= activityDescription,
                groupKey= activityGroupKey,
                groupValue= activityGroupValue,
                createdDate= activityCreationDate.toLocalDate(),
                expirationDate= activityExpirationDate.toLocalDate()
            )
        }

        val onSaveClick: () -> Unit = {
            if(isAllFieldsValid()) {
                hasError = false

                addStudent()
                navigateBack()
            } else {
                hasError = true
                errorMessage = getErrorMessage()
            }
        }

        val onErrorAlertDismiss: () -> Unit = {
            hasError = false
            errorMessage = ""
        }

        AddWorkActivityScreen(
            activityName = activityName,
            activityDescription = activityDescription,
            activityGroupKey = activityGroupKey,
            activityGroupValue = activityGroupValue,
            activityCreationDate = activityCreationDate,
            activityExpirationDate = activityExpirationDate,
            hasError = hasError,
            errorMessage = errorMessage,
            onActivityNameChange = { activityName = it },
            onActivityDescriptionChange = { activityDescription = it },
            onActivityGroupKeyChange = {
                activityGroupKey = it
                activityGroupValue = when(it) {
                    "GENDER" -> "Male"
                    "STANDARD" -> "1"
                    "ALL" -> "ALL"
                    else -> ""
                }
            },
            onActivityGroupValueChange = { activityGroupValue = it },
            onActivityCreationDateChange = { activityCreationDate = it },
            onActivityExpirationDateChange = { activityExpirationDate = it },
            onSaveClick = onSaveClick,
            onErrorAlertDismiss = onErrorAlertDismiss,
            navigateBack = navigateBack,
            isToAddIndividualActivity = isToAddIndividualActivity,
        )
    }
}

@Composable
private fun AddWorkActivityScreen(
    modifier: Modifier = Modifier,
    activityName: String = "",
    activityDescription: String = "",
    activityGroupKey: String = "",
    activityGroupValue: String = "",
    activityCreationDate: String = "",
    activityExpirationDate: String = "",
    hasError: Boolean = false,
    errorMessage: String = "",
    onActivityNameChange: (String) -> Unit = {},
    onActivityDescriptionChange: (String) -> Unit = {},
    onActivityGroupKeyChange: (String) -> Unit = {},
    onActivityGroupValueChange: (String) -> Unit = {},
    onActivityCreationDateChange: (String) -> Unit = {},
    onActivityExpirationDateChange: (String) -> Unit = {},
    onErrorAlertDismiss: () -> Unit = {},
    onSaveClick: () -> Unit = {},
    navigateBack: () -> Unit = {},
    isToAddIndividualActivity: Boolean,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(32.dp)
    ) {
        OutlinedTextField(
            value = activityName,
            onValueChange = onActivityNameChange,
            placeholder = { Text("Enter Activity Name") },
            modifier = Modifier.border(2.dp, color = MaterialTheme.colorScheme.onBackground)
        )
        OutlinedTextField(
            value = activityDescription,
            onValueChange = onActivityDescriptionChange,
            placeholder = { Text("Enter Activity Description") },
            modifier = Modifier.border(2.dp, color = MaterialTheme.colorScheme.onBackground)
        )

        if(!isToAddIndividualActivity) {
            OptionsPickerField(
                label = "Group Key",
                options = listOf("GENDER", "STANDARD", "ALL"),
                value = activityGroupKey,
                onValueChange = onActivityGroupKeyChange,
                title = "Select Group Key"
            )

            if(activityGroupKey.isNotEmpty()) {
                val groupValues = getGroupValueFor(activityGroupKey)

                if(groupValues.isNotEmpty()) {
                    OptionsPickerField(
                        label = "Group Value",
                        options = groupValues,
                        value = activityGroupValue,
                        onValueChange = onActivityGroupValueChange,
                        title = "Select Group Value"
                    )
                } else {
                    ConstantPickerField(label = "Group Value", value = activityGroupValue)
                }
            }
        } else {
            ConstantPickerField(label = "Group Key", value = activityGroupKey)
            ConstantPickerField(label = "Group Value", value = activityGroupValue)
        }


        // TODO: Date pickers creation/expiration
        ODatePickerField(
            label = "Creation Date",
            date = JLocalDate.parse(activityCreationDate),
            onDateSelected = { onActivityCreationDateChange(it.toString()) }
        )

        ODatePickerField(
            label = "Expiration Date",
            date = JLocalDate.parse(activityExpirationDate),
            onDateSelected = { onActivityExpirationDateChange(it.toString()) }
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
                textContentColor = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
    }
}

// TODO: We need to move this to Shared section
private fun isAllFieldsValid(
     name: String,
     description: String,
     groupKey: String,
     groupValue: String,
     creationDate: String,
     expirationDate: String
): Boolean {
    if(name.isEmpty() || description.isEmpty() || groupKey.isEmpty() || groupValue.isEmpty() || creationDate.isEmpty() || expirationDate.isEmpty()) return false

    if(groupKey !in listOf("GENDER", "INDIVIDUAL", "STANDARD", "ALL")) return false

    when(groupKey) {
        "GENDER" -> {
            if(groupValue !in listOf("Male", "Female")) return false
        }
        "STANDARD" -> {
            if(groupValue !in listOf("1","2","3","4","5","6","7","8","9","10","11","12")) return false
        }
        "ALL" -> {
            if(groupValue != "ALL") return false
        }
    }

    // TODO: Date Validations

    return true
}

private fun getErrorMessage(
    name: String,
    description: String,
    groupKey: String,
    groupValue: String,
    creationDate: String,
    expirationDate: String
): String {
    if(name.isEmpty()) {
        return "Activity Name is mandatory"
    } else if(description.isEmpty()) {
        return "Activity Description is mandatory"
    } else if(groupKey.isEmpty()) {
        return "Activity Group Key is mandatory"
    } else if(groupValue.isEmpty()) {
        return "Activity Group Value is mandatory"
    } else if(creationDate.isEmpty()) {
        return "Activity Creation Date is mandatory"
    } else if(expirationDate.isEmpty()) {
        return "Activity Expiration Date is mandatory"
    }

    when(groupKey) {
        "GENDER" -> {
            if(groupValue !in listOf("Male", "Female")) return "Group Value is invalid"
        }
        "STANDARD" -> {
            if(groupValue !in listOf("1","2","3","4","5","6","7","8","9","10","11","12")) return "Group Value is invalid"
        }
        "ALL" -> {
            if(groupValue != "ALL") return "Group Value is invalid"
        }
    }

    return ""
}

private fun getGroupValueFor(groupKey: String): List<String> {
    return when(groupKey) {
        "GENDER" -> {
            listOf("Male", "Female")
        }
        "STANDARD" -> {
            listOf("1","2","3","4","5","6","7","8","9","10","11","12")
        }
        else -> emptyList()
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun AddStudentScreenPreview() {
    MyApplicationTheme {
        AddWorkActivityScreen(
            navigateBack = {},
            isToAddIndividualActivity = false,
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp)
        )
    }
}