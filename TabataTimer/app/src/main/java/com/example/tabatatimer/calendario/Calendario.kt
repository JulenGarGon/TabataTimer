package com.example.tabatatimer.calendario

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.style.TextAlign
import com.example.tabatatimer.model.EjercicioRealizado
import com.example.tabatatimer.ui.theme.Blanco
import com.example.tabatatimer.ui.theme.Gris_Claro
import com.example.tabatatimer.ui.theme.Gris_Oscuro
import com.example.tabatatimer.ui.theme.Negro
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun Calendario(viewModel: CalendarioViewModel = CalendarioViewModel()) {
    val calendar = CalendarLocale()
    var selectedDate by remember { mutableStateOf(LocalDate.now()) }

   val ejerciciosRealizados: State<List<EjercicioRealizado>> = viewModel.ejercicioDia.collectAsState()

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                //.padding(innerPadding)
        ) {
            Box(
                modifier = Modifier
                    .weight(0.4f)
                    .fillMaxWidth()
                    .background(Blanco)
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .padding(top = innerPadding.calculateTopPadding())
            ) {
                calendar.CalendarView(
                    selectedDate = selectedDate,
                    onDateSelected = { date ->
                        selectedDate = date
                        val fecha = ""
                        viewModel.setFechaSeleccionada("${selectedDate.dayOfMonth}-${selectedDate.monthValue}-${selectedDate.year}")
                    }
                )
                Text(
                    text = "Fecha seleccionada: ${selectedDate.dayOfMonth}/${selectedDate.monthValue}/${selectedDate.year}",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            Box(
                modifier = Modifier
                    .weight(0.6f)
                    .fillMaxWidth()
                    .background(
                        Brush.verticalGradient(
                            listOf(Negro, Gris_Oscuro, Gris_Claro)
                        )
                    )
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    LazyColumn {
                        if (ejerciciosRealizados.value.isEmpty()) {
                            item{ Text(text = "Este día no entrenaste",
                                        color = Blanco,
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier.padding(8.dp),
                                        style = MaterialTheme.typography.bodyMedium)
                            }
                        } else {
                            items(ejerciciosRealizados.value) {
                                EjerciciosRealizados(it)
                            }
                        }
                    }
                }
            }
        }
    }

}

@Composable
fun EjerciciosRealizados(ejercicioRealizado: EjercicioRealizado) {
    Box(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(0.9f)
            .height(60.dp)
            .background(
                color = Negro,
                shape = RoundedCornerShape(12.dp)
            )
            .border(
                width = 1.dp,
                color = Blanco,
                shape = RoundedCornerShape(12.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "${ejercicioRealizado.ejercicio} ${ejercicioRealizado.peso}kg - ${ejercicioRealizado.repeticiones}rep",
            color = Blanco,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(8.dp),
            style = MaterialTheme.typography.bodyMedium
        )
    }
}
