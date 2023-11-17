package com.techullurgy.tuitiontracker.android.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.Month
import java.time.Year
import java.util.Locale

private val days = listOf("Sun","Mon","Tue","Wed","Thu","Fri","Sat")

@Composable
fun ODatePickerField(
    label: String,
    date: LocalDate,
    onDateSelected: (LocalDate) -> Unit,
    colors: DatePickerColors = DatePickerDefaults.colors(),
    fontSize: TextUnit = LocalTextStyle.current.fontSize
) {
    Row {
        Text(text = "$label: ", fontSize = fontSize)
        Spacer(modifier = Modifier.width(30.dp))
        ODatePicker(date = date, onDateSelected = onDateSelected, fontSize = fontSize, colors = colors)
    }
}

@Composable
private fun ODatePicker(
    date: LocalDate,
    onDateSelected: (LocalDate) -> Unit,
    colors: DatePickerColors = DatePickerDefaults.colors(),
    fontSize: TextUnit = 20.sp
) {
    var datePickerShow by remember { mutableStateOf(false) }

    Text(text = date.toString(), fontSize = fontSize, color = MaterialTheme.colorScheme.onBackground, modifier = Modifier.clickable { datePickerShow = !datePickerShow })

    if(datePickerShow) {
        Dialog(onDismissRequest = { datePickerShow = !datePickerShow }) {
            ODateRangePickerDialog(
                selectedDate = date,
                onDateSelected = onDateSelected,
                onDismiss = { datePickerShow = !datePickerShow },
                colors = colors
            )
        }
    }
}

@Preview
@Composable
private fun ODateRangePickerDialog(
    selectedDate: LocalDate = LocalDate.now(),
    onDateSelected: (LocalDate) -> Unit = {},
    onDismiss: () -> Unit = {},
    label: String = "",
    subLabel: String = "",
    colors: DatePickerColors = DatePickerDefaults.colors()
) {
    var currentYear by remember {
        mutableIntStateOf(selectedDate.year)
    }

    var currentMonth by remember {
        mutableStateOf(selectedDate.month)
    }

    val currentDay by remember(selectedDate.dayOfMonth) {
        derivedStateOf {
            if(selectedDate.year == currentYear && selectedDate.month == currentMonth) {
                selectedDate.dayOfMonth
            } else -1
        }
    }

    val currentMonthStr = currentMonth.name.lowercase()
        .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() }

    val firstDayOfWeek = getSelectedMonthFirstDay(currentYear, currentMonth).value % 7

    val totalDaysInMonth = when(currentMonth) {
        Month.JANUARY, Month.MARCH, Month.MAY, Month.JULY, Month.AUGUST, Month.OCTOBER, Month.DECEMBER -> 31
        Month.APRIL, Month.JUNE, Month.SEPTEMBER, Month.NOVEMBER -> 30
        Month.FEBRUARY -> {
            val isLeapYear = Year.of(currentYear).isLeap
            if(isLeapYear) 29 else 28
        }
        else -> { -1 } // Won't happen
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .width(300.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(color = colors.containerColor)
            .padding(12.dp)
    ) {
        if(label.isNotEmpty()) {
            Text(
                text = buildAnnotatedString {
                    append("You're trying to select ")
                    val spanStyle = SpanStyle(fontWeight = FontWeight.Bold)
                    withStyle(style = spanStyle) { append(label) }
                    if(subLabel.isNotEmpty()) {
                        append(" in the ")
                        withStyle(style = spanStyle) { append(subLabel) }
                    }
                },
                textAlign = TextAlign.Center,
                color = colors.contentColor.copy(alpha = 0.7f),
                modifier = Modifier.fillMaxWidth(.75f)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            IconButton(onClick = { currentYear -= 1 }) {
                Icon(imageVector = Icons.Default.KeyboardArrowLeft, contentDescription = null, tint = colors.selectionContainerColor)
            }
            Text(
                text = "$currentYear",
                fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                fontWeight = FontWeight.SemiBold,
                color = colors.selectionContainerColor
            )
            IconButton(onClick = { currentYear += 1 }) {
                Icon(imageVector = Icons.Default.KeyboardArrowRight, contentDescription = null, tint = colors.selectionContainerColor)
            }
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            IconButton(onClick = { currentMonth = currentMonth.getPreviousMonth() }) {
                Icon(imageVector = Icons.Default.KeyboardArrowLeft, contentDescription = null, tint = colors.selectionContainerColor)
            }
            Text(
                text = currentMonthStr,
                fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                fontWeight = FontWeight.SemiBold,
                color = colors.selectionContainerColor
            )
            IconButton(onClick = { currentMonth = currentMonth.getNextMonth() }) {
                Icon(imageVector = Icons.Default.KeyboardArrowRight, contentDescription = null, tint = colors.selectionContainerColor)
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(7),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            itemsIndexed(days) { index, it ->
                val color = if(index == 0) MaterialTheme.colorScheme.error else colors.contentColor
                Text(
                    text = it,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    color = color
                )
            }

            // No of Spaces required to kept blank, to begin "Day 1".
            items(firstDayOfWeek) {
                Spacer(Modifier)
            }
            items(totalDaysInMonth) {
                val dayOfMonth = it + 1
                Text(
                    text = dayOfMonth.toString(),
                    color = if(dayOfMonth == currentDay) colors.selectionContentColor else colors.contentColor,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .clickable {
                            val newSelectedDate =
                                LocalDate.of(currentYear, currentMonth, dayOfMonth)
                            onDateSelected(newSelectedDate)
                        }
                        .drawBehind {
                            if (dayOfMonth == currentDay) {
                                drawCircle(color = colors.selectionContainerColor)
                            }
                        }
                        .padding(2.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = buildAnnotatedString {
                append("Selected Date: ")
                val spanStyle = SpanStyle(fontWeight = FontWeight.Bold, color = colors.selectionContainerColor)
                withStyle(style = spanStyle) { append("$selectedDate") }
            }
        )

        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = "OK",
            color = MaterialTheme.colorScheme.contentColorFor(colors.selectionContainerColor),
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp))
                .background(colors.selectionContainerColor)
                .padding(10.dp)
                .clickable { onDismiss() }
        )
    }
}

private fun getSelectedMonthFirstDay(year: Int, month: Month): DayOfWeek {
    return LocalDate.of(year, month, 1).dayOfWeek
}

private fun Month.getNextMonth(): Month {
    if(this == Month.DECEMBER) {
        return Month.JANUARY
    }

    val newMonthNumber = this.value + 1
    return Month.of(newMonthNumber)
}

private fun Month.getPreviousMonth(): Month {
    if(this == Month.JANUARY) {
        return Month.DECEMBER
    }

    val newMonthNumber = this.value - 1
    return Month.of(newMonthNumber)
}

class DatePickerDefaults {
    companion object {
        @Composable
        fun colors(): DatePickerColors = DatePickerColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
            selectionContainerColor = MaterialTheme.colorScheme.tertiary,
            selectionContentColor = MaterialTheme.colorScheme.onTertiary
        )
    }

}

data class DatePickerColors(
    val containerColor: Color,
    val contentColor: Color,
    val selectionContainerColor: Color,
    val selectionContentColor: Color
)

@Preview
@Composable
private fun DatePickerPreview() {
    var date by remember {
        mutableStateOf(LocalDate.now())
    }

    Box(modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.primary), contentAlignment = Alignment.Center) {

        Row {
            Text(text = "Expiry Date: ", fontSize = 20.sp)
            Spacer(modifier = Modifier.width(30.dp))
            ODatePicker(date = date, onDateSelected = { date = it }, fontSize = 20.sp)
        }
    }

}