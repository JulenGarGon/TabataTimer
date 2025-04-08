package com.example.tabatatimer.inicio

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
//import coil.compose.AsyncImage
import com.example.tabatatimer.model.Ejercicio
import com.example.tabatatimer.ui.theme.Blanco
import com.example.tabatatimer.ui.theme.Gris_Claro
import com.example.tabatatimer.ui.theme.Gris_Oscuro
import com.example.tabatatimer.ui.theme.Negro

@Preview
@Composable
fun Inicio(viewModel: InicioViewModel = InicioViewModel()){
    val ejercicios: State<List<Ejercicio>> = viewModel.ejercicio.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Negro)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.3f)
                .background(Brush.horizontalGradient(listOf(Gris_Oscuro, Blanco, Gris_Claro)))
        ){
            LazyRow {
                items(ejercicios.value){
                    EjercicioItem(ejercicio = it, onItemSelected = {
                        //TODO
                    })
                }
            }
        }
        Box(){}
        Box(){}
    }
}
@Composable
fun EjercicioItem(ejercicio: Ejercicio, onItemSelected: (Ejercicio) -> Unit){
    Row (modifier = Modifier.height(150.dp)
        .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ){
        /*AsyncImage(
            modifier = Modifier.size(60.dp).clip(CircleShape),
            model = ejercicio.url_imagen,
            contentDescription = ejercicio.nombre
        )*/
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = ejercicio.nombre.orEmpty(), color = Negro)
    }
}