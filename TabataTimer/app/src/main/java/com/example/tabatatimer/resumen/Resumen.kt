package com.example.tabatatimer.resumen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tabatatimer.model.TopEjercicio
import com.example.tabatatimer.ui.theme.Blanco
import com.example.tabatatimer.ui.theme.Gris_Claro
import com.example.tabatatimer.ui.theme.Gris_Oscuro
import com.example.tabatatimer.ui.theme.Negro

@Composable
fun Resumen(viewModel: ResumenViewModel = viewModel()){
    val ejercicios: State<List<TopEjercicio>> = viewModel.ejercicios.collectAsState()

    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.horizontalGradient(listOf(Gris_Oscuro, Blanco, Gris_Claro)))
            .padding(top = 10.dp)
    ) {
        LazyColumn {
            items(ejercicios.value) {
                EjercicioResumen(topEjercicio = it, onItemSelected = { selectedEjercicio ->
                    Toast.makeText(
                        context,
                        "Ejercicio seleccionado ${selectedEjercicio.nombre.orEmpty()}",
                        Toast.LENGTH_SHORT
                    ).show()
                })
            }
        }
    }
}

@Composable
fun EjercicioResumen(topEjercicio: TopEjercicio, onItemSelected: (TopEjercicio) -> Unit) {
    val pesoColor = when {
        topEjercicio.pesoUltimo < topEjercicio.pesoMejor -> androidx.compose.ui.graphics.Color.Red
        topEjercicio.pesoUltimo > topEjercicio.pesoMejor -> androidx.compose.ui.graphics.Color.Green
        else -> Blanco
    }

    val repsColor = when {
        topEjercicio.repeticionesUltimo < topEjercicio.repeticionesMejor -> androidx.compose.ui.graphics.Color.Red
        topEjercicio.repeticionesUltimo > topEjercicio.repeticionesMejor -> androidx.compose.ui.graphics.Color.Green
        else -> Blanco
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .padding(5.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Negro)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize().padding(8.dp)
        ) {
            Text(text = topEjercicio.nombre, color = Blanco)

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row {
                    Text(text = "Última: ", color = Blanco)
                    Text(
                        text = "${topEjercicio.pesoUltimo} kg",
                        color = pesoColor
                    )
                    Text(text = " - ", color = Blanco)
                    Text(
                        text = "${topEjercicio.repeticionesUltimo} reps",
                        color = repsColor
                    )
                }
                Row {
                    Text(text = "Mejor: ", color = Blanco)
                    Text(text = "${topEjercicio.pesoMejor} kg", color = Blanco)
                    Text(text = " - ", color = Blanco)
                    Text(text = "${topEjercicio.repeticionesMejor} reps", color = Blanco)
                }
            }
        }
    }
}
