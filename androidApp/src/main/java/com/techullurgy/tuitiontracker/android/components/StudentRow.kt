package com.techullurgy.tuitiontracker.android.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.Icon
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
fun StudentRow(
    student: Student,
    onClick: (Student) -> Unit
) {
    val containerColor: Color = if(student.gender == "M") MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.secondaryContainer
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth()
            .height(60.dp)
            .clip(RoundedCornerShape(10))
            .background(containerColor)
            .clickable { onClick(student) }
            .padding(vertical = 8.dp, horizontal = 16.dp)
    ) {
        Text(
            text = student.name,
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.contentColorFor(containerColor)
        )
        Spacer(modifier = Modifier.weight(1f))
        Icon(imageVector = Icons.Outlined.Delete, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
    }
}