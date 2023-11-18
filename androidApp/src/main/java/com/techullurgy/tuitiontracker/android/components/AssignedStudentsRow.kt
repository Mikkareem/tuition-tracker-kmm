package com.techullurgy.tuitiontracker.android.components

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.techullurgy.tuitiontracker.data.models.Student

@Composable
fun AssignedStudentRow(
    student: Student,
    isCompleted: Boolean,
    onCompletionChange: (Boolean) -> Unit
) {
    val containerColor: Color = MaterialTheme.colorScheme.tertiaryContainer

    val labelMediumTextStyle = MaterialTheme.typography.labelMedium.copy(fontFamily = LocalTextStyle.current.fontFamily)
    val labelLargeTextStyle = MaterialTheme.typography.labelLarge.copy(fontFamily = LocalTextStyle.current.fontFamily)

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth()
            .height(60.dp)
            .clip(RoundedCornerShape(10))
            .background(containerColor)
            .padding(vertical = 8.dp, horizontal = 16.dp)
    ) {
        Text(
            text = student.name,
            style = labelLargeTextStyle,
            color = MaterialTheme.colorScheme.contentColorFor(containerColor)
        )
        Crossfade(targetState = isCompleted, label = "") {
            if(it) {
                IconButton(onClick = { onCompletionChange(false) }) {
                    Icon(
                        imageVector = Icons.Default.Done,
                        contentDescription = "Done",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(48.dp)
                    )
                }
            } else {
                Text(
                    text = "Complete",
                    color = MaterialTheme.colorScheme.primary,
                    style = labelMediumTextStyle,
                    modifier = Modifier.clickable { onCompletionChange(true) }
                )
            }
        }
    }
}