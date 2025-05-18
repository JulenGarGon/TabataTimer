package com.example.tabatatimer.musculos

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tabatatimer.R
import com.example.tabatatimer.ui.theme.Blanco
import com.example.tabatatimer.ui.theme.Gris_Claro
import com.example.tabatatimer.ui.theme.Gris_Oscuro
import com.example.tabatatimer.ui.theme.Negro

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun Musculos(viewModel: MusculosViewModel = viewModel()){
    val imagenBase = painterResource(id = R.drawable.cuerpo_completo)
    val fechaTexto = viewModel.obtenerTextoFecha()

    val imagenesSuperpuestas = listOf(
        OverlayImage(R.drawable.abdominales, Offset(-77.3f, -15.2f), 0.5f),
        OverlayImage(R.drawable.antebrazos, Offset(-70.5f, -15.2f), 0.5f),
        OverlayImage(R.drawable.antebrazostrasero, Offset(67.7f, 5.3f), 0.5f),
        OverlayImage(R.drawable.biceps, Offset(-76f, -31.3f), 0.5f),
        OverlayImage(R.drawable.cuadriceps, Offset(-67f, 32f), 0.5f),
        OverlayImage(R.drawable.cuadricepstrasero, Offset(59f, -27f), 0.5f),
        OverlayImage(R.drawable.dorsal, Offset(-70.5f, 0f), 0.5f),
        OverlayImage(R.drawable.espalda, Offset(63.5f, -54.5f), 0.5f),
        OverlayImage(R.drawable.gluteos, Offset(64f, -0.5f), 0.5f),
        OverlayImage(R.drawable.hombrosfrontal, Offset(-67f, 12.5f), 0.5f),
        OverlayImage(R.drawable.hombrostrasera, Offset(52f, -0.5f), 0.5f),
        OverlayImage(R.drawable.muslofrontal, Offset(-80f, 32.6f), 0.5f),
        OverlayImage(R.drawable.muslotrasero, Offset(58f, -45f), 0.5f),
        OverlayImage(R.drawable.oblicuo, Offset(-68f, -22f), 0.5f),
        OverlayImage(R.drawable.oblicuotrasero, Offset(61.5f, -51f), 0.5f),
        OverlayImage(R.drawable.pecho, Offset(-69f, -25f), 0.5f),
        OverlayImage(R.drawable.piernafrontal, Offset(-4.5f, -23f), 0.5f),
        OverlayImage(R.drawable.piernatrasera, Offset(64.5f, -34.5f), 0.5f),
        OverlayImage(R.drawable.triceps, Offset(64.5f, -37f), 0.5f),
    )


    Column(modifier = Modifier
                        .fillMaxSize()
                        .background(Negro),
        horizontalAlignment = Alignment.CenterHorizontally) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { viewModel.retrocederDia() }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_arrow_back),
                    tint = Blanco,
                    contentDescription = "Anterior"
                )
            }

            Text(
                text = fechaTexto,
                color = Blanco,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            IconButton(onClick = { viewModel.avanzarDia() }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_arrow_forward),
                    tint = Blanco,
                    contentDescription = "Siguiente"
                )
            }
        }

        Box(
            modifier = Modifier
                .width(360.dp)
                .height(400.dp)
                .padding(top = 0.dp)
                .background(Brush.verticalGradient(listOf(Gris_Oscuro, Negro, Gris_Oscuro)))
        ) {
            Image(
                painter = imagenBase,
                contentDescription = "Musculos",
                modifier = Modifier.fillMaxSize()
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