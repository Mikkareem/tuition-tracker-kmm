package com.techullurgy.tuitiontracker.android.screens

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.techullurgy.tuitiontracker.android.MyApplicationTheme
import com.techullurgy.tuitiontracker.android.components.StudentRow
import com.techullurgy.tuitiontracker.android.viewmodels.StudentsViewModel


@Composable
fun StudentListScreen(
    viewModel: StudentsViewModel = viewModel(),
    navigateToStudentDetailsScreen: (Long) -> Unit = {},
    navigateToAddStudentScreen: () -> Unit = {}
) {

    val bodySmallTextStyle = MaterialTheme.typography.bodySmall.copy(fontFamily = LocalTextStyle.current.fontFamily)
    val titleLargeTextStyle = MaterialTheme.typography.titleLarge.copy(fontFamily = LocalTextStyle.current.fontFamily)

    val screenState by viewModel.studentsScreenUIState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadStudents()
    }

    Column(
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
                text = "My Students",
                style = titleLargeTextStyle,
                color = MaterialTheme.colorScheme.contentColorFor(
                    MaterialTheme.colorScheme.background
                )
            )

            IconButton(onClick = navigateToAddStudentScreen) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Add Student",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(48.dp)
                )
            }
        }
        if(screenState.students.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(
                    text = "No students available right now. Please add new students to show in this list.",
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Center,
                    style = bodySmallTextStyle,
                    modifier = Modifier.fillMaxWidth(.6f)
                )
            }
        } else {
            LazyColumn {
                items(screenState.students) { student ->
                    StudentRow(
                        student = student,
                        onDelete = viewModel::deleteStudent
                    ) {
                        navigateToStudentDetailsScreen(it.id)
                    }
                }
            }
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, name = "dark")
@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO, name = "light")
@Composable
fun StudentsListScreenPreview() {
    MyApplicationTheme {
        StudentListScreen()
    }
}