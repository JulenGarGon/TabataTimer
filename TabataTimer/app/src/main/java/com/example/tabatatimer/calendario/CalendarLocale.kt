package com.example.tabatatimer.calendario

import android.os.Build
import com.example.tabatatimer.R
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.tabatatimer.ui.theme.Blanco
import com.example.tabatatimer.ui.theme.Naranja
import com.example.tabatatimer.ui.theme.Negro
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale

@ExperimentalMaterial3Api
class CalendarLocale {

    @RequiresApi(Build.VERSION_CODES.O)
    @Composable
    fun CalendarView(
        selectedDate: LocalDate = LocalDate.now(),
        onDateSelected: (LocalDate) -> Unit = {},
        modifier: Modifier = Modifier
    ) {
        var currentMonth by remember { mutableStateOf(YearMonth.now()) }

        val days = remember(currentMonth) {
            val firstDayOfWeek = currentMonth.atDay(1).dayOfWeek.value % 7
            val daysInMonth = currentMonth.lengthOfMonth()
            List(firstDayOfWeek) { null } + (1..daysInMonth).toList()
        }

        Column(modifier = modifier.fillMaxSize().background(Negro)) {
            CalendarHeader(
                currentMonth = currentMonth,
                onPrevious = { currentMonth = currentMonth.minusMonths(1) },
                onNext = { currentMonth = currentMonth.plusMonths(1) }
            )

            Spacer(modifier = Modifier.height(8.dp))

            DaysOfWeekLabels()

            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(7),
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(days.size) { index ->
                        val day = days[index]
                        val date = day?.let { currentMonth.atDay(it) }
                        val isSelected = date == selectedDate

                        CalendarDayCell(
                            day = day,
                            isSelected = isSelected,
                            onClick = {
                                day?.let { onDateSelected(currentMonth.atDay(it)) }
                            }
                        )
                    }
                }
            }
        }
    }

    @Composable
    private fun CalendarDayCell(
        day: Int?,
        isSelected: Boolean,
        onClick: () -> Unit
    ) {
        Box(
            modifier = Modifier
                .aspectRatio(1f)
                .clickable(enabled = day != null, onClick = onClick)
                .padding(4.dp),
            contentAlignment = Alignment.Center
        ) {
            if (day != null) {
                Surface(
                    shape = MaterialTheme.shapes.small,
                    color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface,
                    tonalElevation = if (isSelected) 4.dp else 0.dp,
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(4.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = day.toString(),
                            color = if (isSelected) Naranja else Negro
                        )
                    }
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @Composable
    private fun CalendarHeader(
        currentMonth: YearMonth,
        onPrevious: () -> Unit,
        onNext: () -> Unit
    ) {
        val formatter = remember {
            DateTimeFormatter.ofPattern("MMMM yyyy", Locale.getDefault())
        }
        val monthText = currentMonth.atDay(1).format(formatter)
            .replaceFirstChar { it.uppercaseChar() }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onPrevious) {
                Icon(painter = painterResource(R.drawable.ic_arrow_back), contentDescription = "Mes anterior", tint = Naranja)
            }
            Text(text = monthText, color = Naranja)
            IconButton(onClick = onNext) {
                Icon(painter = painterResource(R.drawable.ic_arrow_forward), contentDescription = "Mes siguiente", tint = Naranja)
            }
        }
    }

    @Composable
    private fun DaysOfWeekLabels() {
        val daysOfWeek = listOf("L", "M", "X", "J", "V", "S", "D")
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            daysOfWeek.forEach {
                Text(
                    text = it,
                    color = Naranja,
                    modifier = Modifier.weight(1f),
                    style = MaterialTheme.typography.bodySmall,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}
