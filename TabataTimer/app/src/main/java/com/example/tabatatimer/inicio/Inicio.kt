package com.example.tabatatimer.inicio

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.tabatatimer.ejercicio.EjercicioDetalle
import com.example.tabatatimer.model.Ejercicio
import com.example.tabatatimer.model.Musculo
import com.example.tabatatimer.model.Sets
import com.example.tabatatimer.nuevoejercicio.NuevoEjercicio
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tabatatimer.ejerciciosfiltrados.EjerciciosFiltrados
import com.google.firebase.auth.FirebaseAuth
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.example.tabatatimer.R
import com.example.tabatatimer.auth.eliminarCuenta
import com.example.tabatatimer.auth.logoutUsuario
import com.example.tabatatimer.ui.theme.Blanco
import com.example.tabatatimer.ui.theme.Gris_Claro
import com.example.tabatatimer.ui.theme.Gris_Oscuro
import com.example.tabatatimer.ui.theme.Naranja_Oscuro
import com.example.tabatatimer.ui.theme.Negro

@RequiresApi(Build.VERSION_CODES.P)
@Composable
fun Inicio(viewModel: InicioViewModel = viewModel(), navController: NavController){
    val ejercicios: State<List<Ejercicio>> = viewModel.ejercicio.collectAsState()
    val sets: State<List<Sets>> = viewModel.set.collectAsState()
    val musculos: State<List<Musculo>> = viewModel.musculo.collectAsState()

    var ejerciciosFiltrados by remember { mutableStateOf<List<Ejercicio>>(emptyList()) }
    var mostrarFiltrados by remember { mutableStateOf(false) }
    var tituloFiltrado by remember { mutableStateOf("") }

    val context = LocalContext.current

    var ejercicioSeleccionado by remember { mutableStateOf<Ejercicio?>(null) }
    var crearNuevoEjercicio by remember { mutableStateOf(false) }

    LaunchedEffect(true) {
        val correo = FirebaseAuth.getInstance().currentUser?.email
        if (correo != null) {
            viewModel.getAllEjerciciosUsuario(correo)
        }
    }

    var showMenu by remember { mutableStateOf(false) }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Negro)
    ) {
        Spacer(modifier = Modifier.height(12.dp))
        //EJERCICIOS
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.3f)
                .background(Brush.horizontalGradient(listOf(Gris_Oscuro, Blanco, Gris_Claro))),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "EJERCICIOS",
                    color = Negro
                )
                Spacer(modifier = Modifier.width(10.dp))
                LazyRow {
                    items(ejercicios.value) {
                        EjercicioItem(ejercicio = it, onItemSelected = { selectedEjercicio ->
                            ejercicioSeleccionado = selectedEjercicio
                        })
                        Spacer(modifier = Modifier.width(2.dp))
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(12.dp))

        //EJERCICIOS USUARIO
        val ejerciciosUsuario by viewModel.ejerciciosUsuario.collectAsState()

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.3f)
                .background(Brush.horizontalGradient(listOf(Gris_Claro, Blanco, Gris_Oscuro))),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "MIS EJERCICIOS",
                    color = Negro
                )
                Spacer(modifier = Modifier.width(10.dp))

                LazyRow {
                    items(ejerciciosUsuario) {
                        EjercicioItem(
                            ejercicio = it,
                            onItemSelected = { selectedEjercicio ->
                                ejercicioSeleccionado = selectedEjercicio
                            },
                            personalizado = true,
                            onDeleteConfirmed = { ejercicio ->
                                viewModel.eliminarEjercicioUsuario(ejercicio)
                                Toast.makeText(context, "Ejercicio eliminado", Toast.LENGTH_SHORT).show()
                            }
                        )
                        Spacer(modifier = Modifier.width(2.dp))
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))
        //SETS
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.3f)
                .background(Brush.horizontalGradient(listOf(Gris_Claro, Blanco, Gris_Oscuro))),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "SETS",
                    color = Negro
                )
                Spacer(modifier = Modifier.width(10.dp))
                LazyRow {
                    items(sets.value) {
                        SetItem(set = it, onItemSelected = { selectedSet ->
                            val correo = FirebaseAuth.getInstance().currentUser?.email ?: return@SetItem
                            viewModel.getEjerciciosPorSet(correo, selectedSet.nombre ?: "") {
                                ejerciciosFiltrados = it
                                tituloFiltrado = "Ejercicios con ${selectedSet.nombre}"
                                mostrarFiltrados = true
                            }
//                            Toast.makeText(
//                                context,
//                                "Set seleccionado ${selectedSet.nombre.orEmpty()}",
//                                Toast.LENGTH_SHORT
//                            ).show()
                        })
                        Spacer(modifier = Modifier.width(2.dp))
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(12.dp))
        //MUSCULOS
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.3f)
                .background(Brush.horizontalGradient(listOf(Gris_Oscuro, Blanco, Gris_Claro))),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "MÚSCULOS",
                    color = Negro
                )
                Spacer(modifier = Modifier.width(10.dp))
                LazyRow {
                    items(musculos.value) {
                        SetMusculo(musculo = it, onItemSelected = { selectedMusculo ->
                            val correo = FirebaseAuth.getInstance().currentUser?.email ?: return@SetMusculo
                            viewModel.getEjerciciosPorMusculo(correo, selectedMusculo.nombre ?: "") {
                                ejerciciosFiltrados = it
                                tituloFiltrado = "Ejercicios para ${selectedMusculo.nombre}"
                                mostrarFiltrados = true
                            }
//                            Toast.makeText(
//                                context,
//                                "Músculo seleccionado ${selectedMusculo.nombre.orEmpty()}",
//                                Toast.LENGTH_SHORT
//                            ).show()
                        })
                        Spacer(modifier = Modifier.width(2.dp))
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(12.dp))
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        var showMenu by remember { mutableStateOf(false) }

        // FAB crear nuevo ejercicio (abajo derecha)
        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            FloatingActionButton(
                onClick = {
                    crearNuevoEjercicio = true
                },
                containerColor = MaterialTheme.colorScheme.primary,
                shape = CircleShape
            ) {
                Text(text = "+")
            }
        }

        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(16.dp)
        ) {
            FloatingActionButton(
                onClick = { showMenu = true },
                containerColor = MaterialTheme.colorScheme.primary,
                shape = CircleShape
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_profile),
                    contentDescription = "Menú usuario"
                )
            }

            DropdownMenu(
                expanded = showMenu,
                onDismissRequest = { showMenu = false }
            ) {
                DropdownMenuItem(
                    text = { Text("Cerrar sesión") },
                    onClick = {
                        showMenu = false
                        logoutUsuario(context, navController)
                    }
                )
                DropdownMenuItem(
                    text = { Text("Eliminar cuenta") },
                    onClick = {
                        showMenu = false
                        eliminarCuenta(context, navController)
                    }
                )
            }
        }
    }




    if (crearNuevoEjercicio) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Negro.copy(alpha = 0.5f))
                .clickable(enabled = true, onClick = {})
        )
        NuevoEjercicio(onBack = {
            crearNuevoEjercicio = false
            val correo = FirebaseAuth.getInstance().currentUser?.email
            if (correo != null) {
                viewModel.getAllEjerciciosUsuario(correo)
            }
        })
    }
    if (mostrarFiltrados) {
        EjerciciosFiltrados(
            titulo = tituloFiltrado,
            ejercicios = ejerciciosFiltrados,
            onBack = { mostrarFiltrados = false },
            onEjercicioSeleccionado = { ejercicio -> ejercicioSeleccionado = ejercicio }
        )
    }
    ejercicioSeleccionado?.let { ejercicio ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Negro.copy(alpha = 0.5f))
                .clickable(enabled = true, onClick = {})
        )
        EjercicioDetalle(ejercicio = ejercicio, onBack = {ejercicioSeleccionado = null})
    }
}
@Composable
fun EjercicioItem(ejercicio: Ejercicio,
                  onItemSelected: (Ejercicio) -> Unit,
                  personalizado: Boolean = false,
                  onDeleteConfirmed: ((Ejercicio) -> Unit)? = null,
                  modifier: Modifier = Modifier
){
    val context = LocalContext.current
    var showDialog by remember { mutableStateOf(false) }

    if (showDialog) {
        androidx.compose.material3.AlertDialog(
            onDismissRequest = { showDialog = false },
            confirmButton = {
                Text(
                    "Eliminar",
                    modifier = Modifier.clickable {
                        onDeleteConfirmed?.invoke(ejercicio)
                        showDialog = false
                    },
                    color = Naranja_Oscuro
                )
            },
            dismissButton = {
                Text("Cancelar", modifier = Modifier.clickable { showDialog = false })
            },
            title = { Text("Eliminar ejercicio") },
            text = { Text("¿Deseas eliminar este ejercicio personalizado?") }
        )
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .pointerInput(personalizado) {
                detectTapGestures(
                    onTap = { onItemSelected(ejercicio) },
                    onLongPress = {
                        if (personalizado) {
                            showDialog = true
                        } else {
                            Toast
                                .makeText(
                                    context,
                                    "Solo puedes eliminar ejercicios personalizados",
                                    Toast.LENGTH_SHORT
                                )
                                .show()
                        }
                    }
                )
            }
            .padding(top = 10.dp, bottom = 10.dp)
            .background(Negro)
            .width(160.dp)
            .height(160.dp)
    ) {
        Spacer(modifier = Modifier.height(4.dp))
        AsyncImage(
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape),
            model = ejercicio.imagen,
            contentDescription = ejercicio.nombre
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = ejercicio.nombre.orEmpty(), color = Blanco)
    }
}

@Composable
fun SetItem(set: Sets, onItemSelected: (Sets) -> Unit){
    Column(horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .clickable { onItemSelected(set) }
            .padding(top = 10.dp, bottom = 10.dp)
            .background(Negro)
            .width(160.dp)
            .height(160.dp)
    ){
        Spacer(modifier = Modifier.height(4.dp))
        AsyncImage(
            modifier = Modifier
                .size(80.dp)
                .clip(CircleShape),
            model = set.imagen,
            contentDescription = set.nombre
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = set.nombre.orEmpty().ifEmpty { "[NOMBRE NO RECIBIDO]" }, color = Blanco)
    }
}

@Composable
fun SetMusculo(musculo: Musculo, onItemSelected: (Musculo) -> Unit){
    Column (horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .clickable { onItemSelected(musculo) }
            .padding(top = 10.dp, bottom = 10.dp)
            .background(Negro)
            .width(160.dp)
            .height(160.dp)
    ){
        Spacer(modifier = Modifier.height(4.dp))
        AsyncImage(
            modifier = Modifier
                .size(80.dp)
                .clip(CircleShape),
            model = musculo.imagen,
            contentDescription = musculo.nombre
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = musculo.nombre.orEmpty(), color = Blanco)
    }
}