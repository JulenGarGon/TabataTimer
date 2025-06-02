package com.example.tabatatimer.calendario

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.tabatatimer.model.EjercicioRealizado
import com.example.tabatatimer.ui.theme.Blanco
import com.example.tabatatimer.ui.theme.Gris_Claro
import com.example.tabatatimer.ui.theme.Gris_Oscuro
import com.example.tabatatimer.ui.theme.Negro
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Calendario(viewModel: CalendarioViewModel = CalendarioViewModel()) {
    val calendar = CalendarLocale()
    var selectedDate by remember { mutableStateOf(LocalDate.now()) }
    val ejerciciosRealizados by viewModel.ejercicioDia.collectAsState()
    var showDialog by remember { mutableStateOf(false) }
    var ejercicioSeleccionado by remember { mutableStateOf<EjercicioRealizado?>(null) }

    val context = LocalContext.current

    LaunchedEffect(selectedDate) {
        viewModel.setFechaSeleccionada(selectedDate)
    }

    if (showDialog && ejercicioSeleccionado != null) {
        AlertDialog(
            onDismissRequest = {
                Toast.makeText(context, "Borrado cancelado", Toast.LENGTH_SHORT).show()
                showDialog = false
                ejercicioSeleccionado = null
                (context as? androidx.activity.ComponentActivity)?.recreate()
            },
            title = { Text("Eliminar ejercicio") },
            text = { Text("¿Estás seguro de que quieres eliminar este ejercicio?") },
            confirmButton = {
                TextButton(onClick = {
                    viewModel.eliminarEjercicio(ejercicioSeleccionado!!, selectedDate)
                    Toast.makeText(context, "Borrado con éxito", Toast.LENGTH_SHORT).show()
                    showDialog = false
                    ejercicioSeleccionado = null
                    (context as? androidx.activity.ComponentActivity)?.recreate()
                }) {
                    Text("Eliminar")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    Toast.makeText(context, "Borrado cancelado", Toast.LENGTH_SHORT).show()
                    showDialog = false
                    ejercicioSeleccionado = null
                    (context as? androidx.activity.ComponentActivity)?.recreate()
                }) {
                    Text("Cancelar")
                }
            }
        )
    }

    Scaffold { innerPadding ->
        Column(modifier = Modifier.fillMaxSize()) {
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
                    }
                )
            }

            Text(
                text = "Fecha seleccionada: ${selectedDate.dayOfMonth}/${selectedDate.monthValue}/${selectedDate.year}",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(vertical = 4.dp)
            )

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
                        if (ejerciciosRealizados.isEmpty()) {
                            item {
                                Text(
                                    text = "Este día no entrenaste",
                                    color = Blanco,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.padding(8.dp),
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                        } else {
                            items(ejerciciosRealizados) {
                                EjerciciosRealizados(it) { ejercicio ->
                                    ejercicioSeleccionado = ejercicio
                                    showDialog = true
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun EjerciciosRealizados(
    ejercicioRealizado: EjercicioRealizado,
    onLongPress: (EjercicioRealizado) -> Unit
) {
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
            )
            .pointerInput(Unit) {
                detectTapGestures(onLongPress = {
                    onLongPress(ejercicioRealizado)
                })
            },
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
