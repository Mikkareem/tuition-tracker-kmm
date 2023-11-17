package com.techullurgy.tuitiontracker.android.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ConstantPickerField(
    label: String,
    value: String
) {
    Row {
        Text(text = "$label: ", color = MaterialTheme.colorScheme.onBackground)
        Spacer(modifier = Modifier.width(30.dp))
        Text(text = value, color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f))
    }
}