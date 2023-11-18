package com.techullurgy.tuitiontracker.android.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.techullurgy.tuitiontracker.android.components.AssignedWorkActivityRow
import com.techullurgy.tuitiontracker.android.viewmodels.StudentDetailsViewModel

@Composable
fun StudentDetailScreen(
    viewModel: StudentDetailsViewModel,
    navigateBack: () -> Unit,
    navigateToAddIndividualWorkActivityScreen: (Long) -> Unit
) {

    val screenState by viewModel.studentDetailsScreenUIState.collectAsState()

    val assignedActivities = screenState.assignedActivities
    val student = screenState.student

    val bodyLargeTextStyle = MaterialTheme.typography.bodyLarge.copy(fontFamily = LocalTextStyle.current.fontFamily)
    val displayMediumTextStyle = MaterialTheme.typography.displayMedium.copy(fontFamily = LocalTextStyle.current.fontFamily)
    val titleLargeTextStyle = MaterialTheme.typography.titleLarge.copy(fontFamily = LocalTextStyle.current.fontFamily)

    student?.let {
        Column(
            verticalArrangement = Arrangement.spacedBy(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = student.name,
                    color = MaterialTheme.colorScheme.onBackground,
                    style = displayMediumTextStyle
                )
                IconButton(onClick = navigateBack) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
            Text(
                text = "Age: ${student.age}",
                color = MaterialTheme.colorScheme.onBackground,
                style = bodyLargeTextStyle
            )
            Text(
                text = "Gender: ${if(student.gender == "M") "Male" else "Female"}",
                color = MaterialTheme.colorScheme.onBackground,
                style = bodyLargeTextStyle
            )
            Text(
                text = "Standard: ${student.standard}",
                color = MaterialTheme.colorScheme.onBackground,
                style = bodyLargeTextStyle
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Assigned Activities",
                    style = titleLargeTextStyle,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.75f)
                )
                IconButton(onClick = { navigateToAddIndividualWorkActivityScreen(student.id) }) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
            Divider()
            LazyColumn(
                modifier = Modifier.weight(1f)
            ) {
                items(assignedActivities) {
                    AssignedWorkActivityRow(
                        activity = it.workActivity,
                        isCompleted = it.isCompleted,
                        onCompletionChange = { _ ->
                            viewModel.toggleCompletionStatusOfAssignedActivity(it)
                        }
                    )
                }
            }
        }
    } ?: run {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                trackColor = MaterialTheme.colorScheme.tertiary
            )
        }
    }

}