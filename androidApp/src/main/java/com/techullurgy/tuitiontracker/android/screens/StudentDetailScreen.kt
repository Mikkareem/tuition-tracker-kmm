package com.techullurgy.tuitiontracker.android.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.techullurgy.tuitiontracker.android.viewmodels.StudentDetailsViewModel

@Composable
fun StudentDetailScreen(
    viewModel: StudentDetailsViewModel,
    studentId: Long,
    navigateBack: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Button(onClick = navigateBack) {
            Text(text = "Student Details for $studentId")
        }
    }
}