package com.example.tabatatimer.EjerciciosFiltrados

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.tabatatimer.R
import com.example.tabatatimer.inicio.EjercicioItem
import com.example.tabatatimer.model.Ejercicio
import com.example.tabatatimer.ui.theme.Blanco
import com.example.tabatatimer.ui.theme.Naranja
import com.example.tabatatimer.ui.theme.Naranja_Oscuro
import com.example.tabatatimer.ui.theme.Negro

@Composable
fun EjerciciosFiltrados(
    titulo: String,
    ejercicios: List<Ejercicio>,
    onBack: () -> Unit,
    onEjercicioSeleccionado: (Ejercicio) -> Unit
){
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Negro.copy(alpha = 0.5f))
            .clickable(enabled = true, onClick = {})
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(Negro, Negro, Naranja_Oscuro, Naranja)))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(8.dp)
                .height(56.dp)
                .fillMaxWidth()
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_arrow_back),
                contentDescription = "Volver",
                modifier = Modifier
                    .size(24.dp)
                    .clickable { onBack() }
            )
            Spacer(modifier = Modifier.width(8.dp))
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = titulo, color = Blanco, style = MaterialTheme.typography.titleLarge)
            }
        }

        val filas = ejercicios.chunked(2)

        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(filas) { fila ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    for (ejercicio in fila) {
                        EjercicioItem(
                            ejercicio = ejercicio,
                            onItemSelected = { onEjercicioSeleccionado(it) },
                            modifier = Modifier
                                .weight(1f)
                                .padding(vertical = 8.dp)
                        )
                    }
                }
            }
        }

    }
}