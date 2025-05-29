package com.example.tabatatimer.nuevoejercicio

import android.os.Build
import androidx.annotation.RequiresApi
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
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tabatatimer.R
import com.example.tabatatimer.model.Musculos
import com.example.tabatatimer.model.TiposSets
import com.example.tabatatimer.ui.theme.Blanco
import com.example.tabatatimer.ui.theme.Naranja
import com.example.tabatatimer.ui.theme.Naranja_Oscuro
import com.example.tabatatimer.ui.theme.Negro

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NuevoEjercicio(viewModel: NuevoEjercicioViewModel = viewModel(), onBack: () -> Unit) {
    val context = LocalContext.current
    val musculos = Musculos.entries.map { it.name }
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .background(Brush.verticalGradient(listOf(Negro, Negro, Naranja_Oscuro, Naranja)))
            .imePadding()
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
                Text(
                    text = "Nuevo ejercicio",
                    color = Blanco,
                    style = MaterialTheme.typography.titleLarge
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = viewModel.nombre,
            onValueChange = { viewModel.onNombreChanged(it) },
            label = { Text("Nombre del ejercicio") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        SetDropdown(viewModel)

        Spacer(modifier = Modifier.height(8.dp))

        GrupoMuscularDropdown(musculos, viewModel)

        Spacer(modifier = Modifier.height(8.dp))

        EsfuerzoSelector(musculos, viewModel)

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = viewModel.imagen,
            onValueChange = { viewModel.onImagenChanged(it) },
            label = { Text("Url de imagen") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = viewModel.video,
            onValueChange = { viewModel.onVideoChanged(it) },
            label = { Text("URL de video/gif") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { viewModel.guardarEjercicio(context) },
            enabled = viewModel.datosCompletos()
        ) {
            Text("Guardar ejercicio")
        }

        Spacer(modifier = Modifier.height(80.dp))
    }
}

@Composable
fun GrupoMuscularDropdown(musculos: List<String>, viewModel: NuevoEjercicioViewModel) {
    var expanded by remember { mutableStateOf(false) }
    val selected = viewModel.grupoMuscular

    Box(modifier = Modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = selected.replace("_", " "),
            onValueChange = {},
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Grupo muscular principal") },
            readOnly = true,
            trailingIcon = {
                IconButton(onClick = { expanded = !expanded }) {
                    Icon(
                        painter = painterResource(R.drawable.ic_arrow_drop_down),
                        contentDescription = "Expand"
                    )
                }
            }
        )
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            musculos.forEach { musculo ->
                DropdownMenuItem(
                    text = { Text(musculo.replace("_", " ")) },
                    onClick = {
                        viewModel.onGrupoMuscularChanged(musculo)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
fun EsfuerzoSelector(musculos: List<String>, viewModel: NuevoEjercicioViewModel) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text("Esfuerzo por músculo (1-10)", style = MaterialTheme.typography.titleMedium)
        musculos.forEach { musculo ->
            val valor = viewModel.esfuerzo[musculo] ?: 1

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            ) {
                Text(
                    text = musculo.replace("_", " "),
                    modifier = Modifier.width(120.dp),
                    color = Blanco
                )
                Slider(
                    value = valor.toFloat(),
                    onValueChange = { viewModel.onEsfuerzoChanged(musculo, it.toInt()) },
                    valueRange = 1f..10f,
                    steps = 8,
                    modifier = Modifier.weight(1f)
                )
                Text(text = "$valor", color = Blanco)
            }
        }
    }
}

@Composable
fun SetDropdown(viewModel: NuevoEjercicioViewModel) {
    val sets = TiposSets.entries.map { it.name.replace("_", " ") }
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = viewModel.set.replace("_", " "),
            onValueChange = {},
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Set necesario") },
            readOnly = true,
            trailingIcon = {
                IconButton(onClick = { expanded = !expanded }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_arrow_drop_down),
                        contentDescription = "Expand"
                    )
                }
            }
        )
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            sets.forEach { item ->
                DropdownMenuItem(
                    text = { Text(item) },
                    onClick = {
                        viewModel.onSetChanged(item.replace(" ", "_"))
                        expanded = false
                    }
                )
            }
        }
    }
}

