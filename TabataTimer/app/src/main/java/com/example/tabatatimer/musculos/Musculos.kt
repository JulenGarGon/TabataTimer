package com.example.tabatatimer.musculos

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tabatatimer.R
import com.example.tabatatimer.ui.theme.Blanco
import com.example.tabatatimer.ui.theme.Gris_Oscuro

@Preview
@Composable
fun Musculos(){
    val imagenBase = painterResource(id = R.drawable.cuerpo_completo)

    val imagenesSuperpuestas = listOf(
        OverlayImage(R.drawable.abdominales, Offset(-79.3f, -17f), 0.5f),
        OverlayImage(R.drawable.antebrazos, Offset(-72.5f, -17.5f), 0.5f),
        OverlayImage(R.drawable.antebrazostrasero, Offset(68.7f, 5f), 0.5f),
        OverlayImage(R.drawable.biceps, Offset(-78f, -32f), 0.5f),
        OverlayImage(R.drawable.cuadriceps, Offset(-68f, 32f), 0.5f),
        OverlayImage(R.drawable.cuadricepstrasero, Offset(60f, -29f), 0.5f),
        OverlayImage(R.drawable.dorsal, Offset(-72.5f, 0f), 0.5f),
        OverlayImage(R.drawable.espalda, Offset(65f, -56f), 0.5f),
        OverlayImage(R.drawable.gluteos, Offset(65f, -1.5f), 0.5f),
        OverlayImage(R.drawable.hombrosfrontal, Offset(-68f, 12f), 0.5f),
        OverlayImage(R.drawable.hombrostrasera, Offset(53f, -0.5f), 0.5f),
        OverlayImage(R.drawable.muslofrontal, Offset(-82f, 32.3f), 0.5f),
        OverlayImage(R.drawable.muslotrasero, Offset(59f, -47f), 0.5f),
        OverlayImage(R.drawable.oblicuo, Offset(-69f, -23f), 0.5f),
        OverlayImage(R.drawable.oblicuotrasero, Offset(63f, -53f), 0.5f),
        OverlayImage(R.drawable.pecho, Offset(-71f, -26f), 0.5f),
        OverlayImage(R.drawable.piernafrontal, Offset(-4.5f, -24f), 0.5f),
        OverlayImage(R.drawable.piernatrasera, Offset(65.5f, -36.5f), 0.5f),
        OverlayImage(R.drawable.triceps, Offset(66f, -39f), 0.5f),
    )


    Column(modifier = Modifier.fillMaxSize()) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { /* acción anterior */ }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_arrow_back),
                    tint = Blanco,
                    contentDescription = "Anterior"
                )
            }

            Text(
                text = "Hoy",
                color = Blanco,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            IconButton(onClick = { /* acción siguiente */ }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_arrow_forward),
                    tint = Blanco,
                    contentDescription = "Siguiente"
                )
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Image(
                painter = imagenBase,
                contentDescription = "Musculos",
                modifier = Modifier.fillMaxWidth()
            )

            imagenesSuperpuestas.forEach { imagen ->
                Image(
                    painter = painterResource(id = imagen.id),
                    contentDescription = null,
                    modifier = Modifier
                        .size(410.dp)
                        .absoluteOffset(
                            x = imagen.posicion.x.dp,
                            y = imagen.posicion.y.dp
                        )
                        .alpha(imagen.opacidad)
                )
            }
        }
    }
}

data class OverlayImage(
    val id: Int,
    val posicion: Offset,
    val opacidad: Float
)