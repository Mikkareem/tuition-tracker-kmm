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
import com.techullurgy.tuitiontracker.android.components.AssignedStudentRow
import com.techullurgy.tuitiontracker.android.viewmodels.WorkActivityDetailsViewModel

@Composable
fun WorkActivityDetailScreen(
    viewModel: WorkActivityDetailsViewModel,
    navigateBack: () -> Unit
) {
    val screenState by viewModel.workActivityDetailsScreenUIState.collectAsState()

    val assignedStudents = screenState.assignedStudents
    val activity = screenState.activity

    val bodyLargeTextStyle = MaterialTheme.typography.bodyLarge.copy(fontFamily = LocalTextStyle.current.fontFamily)
    val displayMediumTextStyle = MaterialTheme.typography.displayMedium.copy(fontFamily = LocalTextStyle.current.fontFamily)
    val titleLargeTextStyle = MaterialTheme.typography.titleLarge.copy(fontFamily = LocalTextStyle.current.fontFamily)

    activity?.let {
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
                    text = activity.name,
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
                text = "Description: ${activity.description}",
                color = MaterialTheme.colorScheme.onBackground,
                style = bodyLargeTextStyle
            )
            Text(
                text = "Group Key: ${activity.groupKey}",
                color = MaterialTheme.colorScheme.onBackground,
                style = bodyLargeTextStyle
            )
            Text(
                text = "Group Value: ${activity.groupValue}",
                color = MaterialTheme.colorScheme.onBackground,
                style = bodyLargeTextStyle
            )
            Text(
                text = "Creation Date: ${activity.createdDate}",
                color = MaterialTheme.colorScheme.onBackground,
                style = bodyLargeTextStyle
            )
            Text(
                text = "Expiration Date: ${activity.expirationDate}",
                color = MaterialTheme.colorScheme.onBackground,
                style = bodyLargeTextStyle
            )

            Text(
                text = "Assigned Students",
                style = titleLargeTextStyle,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.75f)
            )
            Divider()
            LazyColumn(
                modifier = Modifier.weight(1f)
            ) {
                items(assignedStudents) {
                    AssignedStudentRow(
                        student = it.student,
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