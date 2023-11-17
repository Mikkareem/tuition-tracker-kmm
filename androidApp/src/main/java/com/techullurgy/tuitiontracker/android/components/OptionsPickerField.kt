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
fun OptionsPickerField(
    label: String,
    options: List<String>,
    value: String,
    onValueChange: (String) -> Unit,
    title: String = "",
) {
    Row {
        Text(text = "$label: ", color = MaterialTheme.colorScheme.onBackground)
        Spacer(modifier = Modifier.width(30.dp))
        OptionsPicker(
            options = options,
            value = value,
            onValueChange = onValueChange,
            title = title
        )
    }
}