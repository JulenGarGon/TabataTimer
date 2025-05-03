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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
fun EjercicioResumen(topEjercicio: TopEjercicio, onItemSelected: (TopEjercicio) -> Unit){
    Box(modifier = Modifier
        .fillMaxWidth()
        .height(100.dp)
        .padding(top = 5.dp, bottom = 5.dp, end = 5.dp, start = 5.dp)
        .background(Negro)
    ){
        Column {
            Text(text = "Ejercicio: ${topEjercicio.nombre}", color = Blanco)
            Row (
                horizontalArrangement = Arrangement.Center
            ){
                Text(text = "Repeticiones: ${topEjercicio.repeticiones}",
                    color = Blanco,
                    modifier = Modifier.weight(0.4f))
                Spacer(modifier = Modifier.weight(0.2f))
                Text(text = "Peso: ${topEjercicio.peso}",
                    color = Blanco,
                    modifier = Modifier.weight(0.4f))
            }
        }

    }

}