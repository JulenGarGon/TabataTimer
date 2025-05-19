package com.example.tabatatimer.ejercicio

import android.util.Log
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tabatatimer.R
import com.example.tabatatimer.model.Ejercicio
import com.example.tabatatimer.ui.theme.Blanco
import com.example.tabatatimer.ui.theme.Naranja
import com.example.tabatatimer.ui.theme.Naranja_Oscuro
import com.example.tabatatimer.ui.theme.Negro

//ejercicio: Ejercicio, onBack: () -> Unit
@Preview
@Composable
fun Ejercicio(){
    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(Negro, Negro, Naranja_Oscuro, Naranja)))
            .padding(16.dp),
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
                    .clickable { /*onBack()*/ }
            )

            Spacer(modifier = Modifier.width(8.dp))

            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Nombre ejercicio",
                    color = Blanco,
                    style = MaterialTheme.typography.titleLarge
                )
            }
        }


    }
}