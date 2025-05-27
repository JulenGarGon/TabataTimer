package com.example.tabatatimer.temporizador

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tabatatimer.R
import com.example.tabatatimer.ui.theme.Blanco

@Composable
fun Temporizador(viewModel: TemporizadorViewModel = viewModel()){

    val tiempoRestante by viewModel.tiempoRestante.collectAsState()
    val activo by viewModel.activado.collectAsState()
    val context = LocalContext.current

    var valorBarra by remember { mutableStateOf(viewModel.valorBarra.value) }

    Column( horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp)
    ){
        Text(
            text = "${tiempoRestante / 60} : ${(tiempoRestante % 60).toString().padStart(2, '0')}",
            style = MaterialTheme.typography.headlineMedium,
            color = Blanco,
            modifier = Modifier.padding(top = 16.dp, bottom = 12.dp)
        )

        Slider(
            value = valorBarra,
            onValueChange = {
                valorBarra = it
                viewModel.actualizaBarra(it)},
            valueRange = 120f..300f,
            steps = ((300-120)/10)-1,
            enabled = !activo,
            modifier = Modifier.fillMaxWidth()
        )

        Row (verticalAlignment = Alignment.CenterVertically){
            Image(
                painter = painterResource(id = R.drawable.ic_play),
                contentDescription = "Play",
                modifier = Modifier
                    .size(48.dp)
                    .clickable { viewModel.startNotificacion(context) }
            )

            Spacer(modifier = Modifier.width(16.dp))

            Image(
                painter = painterResource(id = R.drawable.ic_pause),
                contentDescription = "Pause",
                modifier = Modifier
                    .size(48.dp)
                    .clickable { viewModel.pauseNotificacion(context) }
            )

            Spacer(modifier = Modifier.width(16.dp))

            Image(
                painter = painterResource(id = R.drawable.ic_reset),
                contentDescription = "Reset",
                modifier = Modifier
                    .size(48.dp)
                    .clickable { viewModel.resetNotificacion(context) }
            )

        }

    }
}