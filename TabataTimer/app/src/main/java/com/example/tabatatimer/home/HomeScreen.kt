package com.example.tabatatimer.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.tabatatimer.R

@Preview
@Composable
fun HomeScreen(){
    var selectedTab by remember { mutableStateOf(0) }

    val tabs = listOf(
        TabItem(R.string.home, R.drawable.ic_home, R.drawable.ic_home_filled),
        TabItem(R.string.resumen, R.drawable.ic_trending_up, R.drawable.ic_trending_up_filled),
        TabItem(R.string.calendario, R.drawable.ic_calendar, R.drawable.ic_calendar_filled),
        TabItem(R.string.musculos, R.drawable.ic_fitness, R.drawable.ic_fitness_filled)
    )

    val homeState by remember { mutableStateOf("Estado inicial de inicio") }
    val summaryState by remember { mutableStateOf("Estado inicial de resumen") }
    val calendarState by remember { mutableStateOf("Estado inicial de calendario") }
    val musclesState by remember { mutableStateOf("Estado inicial de musculos") }

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

            }
        }
    }

}

data class TabItem(val titleRes: Int, val iconRes: Int, val selectedIconRes: Int)