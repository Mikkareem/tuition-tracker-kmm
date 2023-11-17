package com.techullurgy.tuitiontracker.android.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

@Composable
fun OptionsPicker(
    options: List<String>,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    title: String = "Please Select one option",
    containerColor: Color = MaterialTheme.colorScheme.tertiaryContainer,
    accentColor: Color = MaterialTheme.colorScheme.onTertiaryContainer,
    fontSize: TextUnit = MaterialTheme.typography.labelLarge.fontSize,
    isDestructive: Boolean = true
) {
    require(options.isNotEmpty() && (options.contains(value) || value.isEmpty()))

    var optionShow by remember {
        mutableStateOf(false)
    }

    Text(
        text = value.ifEmpty { "Select" },
        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.85f),
        fontSize = fontSize,
        modifier = modifier.clickable { optionShow = !optionShow }
    )

    if(optionShow) {
        OptionsPickerDialog(
            isDestructive = isDestructive,
            title = title,
            options = options,
            onValueChange = onValueChange,
            onDismissRequest = { optionShow = !optionShow },
            containerColor = containerColor,
            accentColor = accentColor
        )
    }
}

@Composable
private fun OptionsPickerDialog(
    isDestructive: Boolean,
    title: String,
    options: List<String>,
    containerColor: Color,
    accentColor: Color,
    onDismissRequest: () -> Unit = {},
    onValueChange: (String) -> Unit = {}
) {
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(
            dismissOnClickOutside = isDestructive,
            dismissOnBackPress = isDestructive
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .heightIn(max = 300.dp)
                .clip(RoundedCornerShape(25f))
                .background(containerColor)
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = title)
            LazyColumn(modifier = Modifier.weight(1f)) {
                items(options) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                onValueChange(it)
                                onDismissRequest()
                            }
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = it,
                            color = accentColor,
                            style = MaterialTheme.typography.labelLarge
                        )
                    }
                    Divider()
                }
            }

            if(isDestructive) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            onDismissRequest()
                        }
                        .clip(RoundedCornerShape(25))
                        .background(Color.Red.copy(alpha = 0.7f))
                        .padding(8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "Cancel", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}