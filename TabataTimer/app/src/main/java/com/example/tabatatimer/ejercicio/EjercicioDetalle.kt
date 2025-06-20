package com.example.tabatatimer.ejercicio

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text2.input.rememberTextFieldState
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.tabatatimer.R
import com.example.tabatatimer.model.Ejercicio
import com.example.tabatatimer.reproductor.Gif
import com.example.tabatatimer.reproductor.Reproductor
import com.example.tabatatimer.ui.theme.Blanco
import com.example.tabatatimer.ui.theme.Naranja
import com.example.tabatatimer.ui.theme.Naranja_Oscuro
import com.example.tabatatimer.ui.theme.Negro
import androidx.compose.ui.platform.LocalContext
import com.example.tabatatimer.temporizador.Temporizador


@RequiresApi(Build.VERSION_CODES.P)
@Composable
fun EjercicioDetalle(ejercicio: Ejercicio, onBack: () -> Unit, viewModel: EjercicioDetalleViewModel = EjercicioDetalleViewModel()){

    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(Negro, Negro, Naranja_Oscuro, Naranja))),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Row (
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(8.dp)
                .height(56.dp)
                .fillMaxWidth()
        ){
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
                Text(
                    text = ejercicio.nombre.orEmpty(),
                    color = Blanco,
                    style = MaterialTheme.typography.titleLarge
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        Video(ejercicio)

        Spacer(modifier = Modifier.height(24.dp))

        var peso by remember { mutableStateOf(1f) }
        var repeticiones by remember { mutableStateOf(1f) }

        Column (horizontalAlignment = Alignment.CenterHorizontally)
        {
            DatoSet("Peso (kg)", peso, onValorChange = { peso = it })
            Spacer(modifier = Modifier.height(5.dp))
            DatoSet("Repeticiones", repeticiones, onValorChange = { repeticiones = it })

            val context = LocalContext.current

            Button(onClick = {
                viewModel.guardarEjercicio(context = context, ejercicio = ejercicio, peso = peso, repeticiones = repeticiones.toInt())
            }) {
                Text("Guardar ejercicio")
            }
        }
        Temporizador()
    }
}

@RequiresApi(Build.VERSION_CODES.P)
@Composable
fun Video(ejercicio: Ejercicio){
    if (ejercicio.video?.endsWith(".mp4", ignoreCase = true) == true){
        Reproductor(
            url = ejercicio.video,
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp)
        )
    } else {
        Gif(
            url = ejercicio.video.toString(),
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp)
        )
    }
}

@Composable
fun DatoSet(texto: String, valorInicial: Float, onValorChange: (Float) -> Unit) {
    var valor by remember { mutableStateOf(valorInicial) }

    fun ajustarValor(delta: Float) {
        valor = (valor + delta).coerceAtLeast(0f)
        onValorChange(valor)
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = texto,
            color = Blanco,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.weight(0.4f)
        )

        Spacer(modifier = Modifier.weight(0.1f))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.5f)
        ) {
            RepetitiveClickButton(
                onClick = {
                    ajustarValor(if (texto == "Repeticiones") -1f else -0.5f)
                },
                modifier = Modifier.weight(0.3f),
                icono = R.drawable.ic_remove
            )

            Spacer(modifier = Modifier.width(2.dp))

            TextField(
                value = if (texto == "Repeticiones") valor.toInt().toString() else String.format("%.1f", valor),
                onValueChange = { nuevoValor ->
                    val valorNuevo: Float? = if (texto != "Repeticiones") {
                        nuevoValor.replace(',', '.').toFloatOrNull()
                    } else {
                        nuevoValor.toFloatOrNull()
                    }
                    if (valorNuevo != null) {
                        valor = valorNuevo
                        onValorChange(valor)
                    }
                },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Decimal),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    focusedIndicatorColor = Blanco,
                    unfocusedIndicatorColor = Blanco,
                    cursorColor = Blanco,
                    focusedTextColor = Blanco,
                    unfocusedTextColor = Blanco
                ),
                modifier = Modifier
                    .weight(0.4f)
                    .heightIn(min = 48.dp)
            )

            Spacer(modifier = Modifier.width(2.dp))

            RepetitiveClickButton(
                onClick = {
                    ajustarValor(if (texto == "Repeticiones") 1f else 0.5f)
                },
                modifier = Modifier.weight(0.3f),
                icono = R.drawable.ic_add
            )
        }
    }
}


@Composable
fun RepetitiveClickButton( onClick: () -> Unit, modifier: Modifier = Modifier, icono: Int) {
    val delayMillis = 100L
    val longPressDelay = 300L
    var isPressed by remember { mutableStateOf(false) }

    LaunchedEffect(isPressed) {
        if (isPressed) {
            kotlinx.coroutines.delay(longPressDelay)
            while (isPressed) {
                onClick()
                kotlinx.coroutines.delay(delayMillis)
            }
        }
    }

    Image(
        painter = painterResource(id = icono),
        contentDescription = null,
        modifier = modifier
            .clickable { onClick() }
            .pointerInput(Unit) {
                awaitPointerEventScope {
                    while (true) {
                        val event = awaitPointerEvent()
                        isPressed = event.changes.any { it.pressed }
                        if (!isPressed) break
                    }
                }
            }
    )
}

