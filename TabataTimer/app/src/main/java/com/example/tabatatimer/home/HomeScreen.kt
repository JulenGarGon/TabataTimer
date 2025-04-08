package com.example.tabatatimer.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.tabatatimer.R
import com.example.tabatatimer.inicio.Inicio
import com.example.tabatatimer.ui.theme.Gris_Claro
import com.example.tabatatimer.ui.theme.Negro

@Preview
@Composable
fun HomeScreen(){
    var selectedTab by remember { mutableStateOf(1) }

    val tabs = listOf(
        TabItem(R.string.home, R.drawable.ic_home, R.drawable.ic_home_filled),
        TabItem(R.string.resumen, R.drawable.ic_trending_up, R.drawable.ic_trending_up_filled),
        TabItem(R.string.calendario, R.drawable.ic_calendar, R.drawable.ic_calendar_filled),
        TabItem(R.string.musculos, R.drawable.ic_fitness, R.drawable.ic_fitness_filled)
    )

    val homeState = remember { mutableStateOf("Estado inicial de inicio") }
    val summaryState = remember { mutableStateOf("Estado inicial de resumen") }
    val calendarState = remember { mutableStateOf("Estado inicial de calendario") }
    val musclesState = remember { mutableStateOf("Estado inicial de musculos") }

    Scaffold (
        bottomBar = {
            NavigationBar {
                tabs.forEachIndexed{index, tab ->
                    NavigationBarItem(
                        icon = {
                            val iconRes = if (selectedTab == index) {
                                tab.selectedIconRes
                            } else {
                                tab.iconRes
                            }
                            Icon(
                                painter = painterResource(id = iconRes),
                                contentDescription = stringResource(id = tab.titleRes)
                            )
                        },
                        label = { Text(text = stringResource(id = tab.titleRes)) },
                        selected = selectedTab == index,
                        onClick = {selectedTab = index}
                    )
                }
            }
        }
    ){ innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)){
            when (selectedTab) {
                0 -> HomeContent(homeState)
                1 -> SummaryContent(summaryState)
                2 -> CalendarContent(calendarState)
                3 -> MusclesContent(musclesState)
            }
        }
    }

}

data class TabItem(val titleRes: Int, val iconRes: Int, val selectedIconRes: Int)

@Composable
fun HomeContent(state: MutableState<String>){
    //Inicio()
    Text(text = "Inicio")
}

@Composable
fun SummaryContent(state: MutableState<String>){
    Text(text = "Resumen")
}

@Composable
fun CalendarContent(state: MutableState<String>){
    Text(text = "Calendario")
}

@Composable
fun MusclesContent(state: MutableState<String>){
    Text(text = "Musculos")
}