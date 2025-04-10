package com.example.tabatatimer.inicio

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.tabatatimer.model.Ejercicio
import com.example.tabatatimer.model.Musculo
import com.example.tabatatimer.model.Sets
import com.example.tabatatimer.ui.theme.Blanco
import com.example.tabatatimer.ui.theme.Gris_Claro
import com.example.tabatatimer.ui.theme.Gris_Oscuro
import com.example.tabatatimer.ui.theme.Negro

@Preview
@Composable
fun Inicio(viewModel: InicioViewModel = InicioViewModel()){
    val ejercicios: State<List<Ejercicio>> = viewModel.ejercicio.collectAsState()
    val sets: State<List<Sets>> = viewModel.set.collectAsState()
    val musculos: State<List<Musculo>> = viewModel.musculo.collectAsState()

    val context = LocalContext.current

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
        ){
            Column (
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ){
                Text(
                    text = "EJERCICIOS",
                    color = Negro
                )
                Spacer(modifier = Modifier.width(10.dp))
                LazyRow {
                    items(ejercicios.value) {
                        EjercicioItem(ejercicio = it, onItemSelected = {
                            selectedEjercicio -> Toast.makeText(context, "Ejercicio seleccionado: ${selectedEjercicio.nombre.orEmpty()}", Toast.LENGTH_SHORT).show()
                        })
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
        ){
            Column (
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ){
                Text(
                    text = "SETS",
                    color = Negro
                )
                Spacer(modifier = Modifier.width(10.dp))
                LazyRow {
                    items(sets.value) {
                        SetItem(set = it, onItemSelected = {
                            selectedSet -> Toast.makeText(context, "Set seleccionado ${selectedSet.nombre.orEmpty()}", Toast.LENGTH_SHORT).show()
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
        ){
            Column (
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ){
                Text(
                    text = "MÚSCULOS",
                    color = Negro
                )
                Spacer(modifier = Modifier.width(10.dp))
                LazyRow {
                    items(musculos.value){
                        SetMusculo(musculo = it, onItemSelected = {
                            selectedMusculo -> Toast.makeText(context, "Músculo seleccionado ${selectedMusculo.nombre.orEmpty()}", Toast.LENGTH_SHORT).show()
                        })
                        Spacer(modifier = Modifier.width(2.dp))
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(12.dp))
    }
}
@Composable
fun EjercicioItem(ejercicio: Ejercicio, onItemSelected: (Ejercicio) -> Unit){
    Column(horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .clickable { onItemSelected(ejercicio) }
            .padding(top = 10.dp, bottom = 10.dp)
            .background(Negro)
            .width(160.dp)
            .height(160.dp)
    ){
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