package com.example.tabatatimer.signup

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.tabatatimer.R
import com.example.tabatatimer.initial.InitialScreen
import com.example.tabatatimer.ui.theme.Blanco
import com.example.tabatatimer.ui.theme.Gris_Claro
import com.example.tabatatimer.ui.theme.Gris_Oscuro
import com.example.tabatatimer.ui.theme.Negro
import com.google.firebase.auth.FirebaseAuth

@Composable
fun SignUpScreen(auth: FirebaseAuth, onBackPressed: () -> Unit){
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val customFontFamily = FontFamily(
        Font(R.font.groteskmedium)
    )
    Column (modifier = Modifier
        .fillMaxSize()
        .background(Gris_Oscuro),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row {
            IconButton(
                onClick = { onBackPressed() },
                modifier = Modifier.padding(vertical = 24.dp, horizontal = 12.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_arrow_back),
                    contentDescription = "",
                    tint = Blanco
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Text("REGISTRO",
                fontSize = 28.sp,
                style = TextStyle(
                    fontFamily = customFontFamily
                ),
                modifier = Modifier.padding(vertical = 24.dp, horizontal = 12.dp)
            )
        }
        Text("Introduzca sus datos")
    }
}

@Preview
@Composable
fun visualizacion(){
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val customFontFamily = FontFamily(
        Font(R.font.groteskmedium)
    )
    Column (modifier = Modifier
        .fillMaxSize()
        .background(Gris_Oscuro),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row {
            Image(
                painter = painterResource(id = R.drawable.ic_arrow_back),
                contentDescription = "",
                modifier = Modifier
                    .padding(vertical = 24.dp, horizontal = 12.dp)
                    .clickable { /*onBackPressed()*/ }
            )
            Spacer(modifier = Modifier.weight(1f))
            Text("REGISTRO",
                fontSize = 28.sp,
                style = TextStyle(
                    fontFamily = customFontFamily
                ),
                modifier = Modifier.padding(vertical = 24.dp, horizontal = 12.dp)
            )
        }
        Spacer(modifier = Modifier.height(50.dp))
        Text("Introduzca sus datos",
            fontSize = 25.sp,
            style = TextStyle(
                fontFamily = customFontFamily
            ),
            color = Blanco
        )
        Spacer(modifier = Modifier.height(100.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .height(400.dp)
                .background(Brush.verticalGradient(
                    listOf(Gris_Claro, Negro)
                ), shape = RoundedCornerShape(8.dp)
                )

        ) {
            Column (
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Spacer(modifier = Modifier.height(20.dp))
                TextField(
                    value = email,
                    onValueChange = {password = it},
                    label = { Text(stringResource(R.string.password))},
                    modifier = Modifier
                        .fillMaxWidth(0.85f),
                    singleLine = true,
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Negro,
                        unfocusedContainerColor = Blanco,
                        focusedTextColor = Blanco,
                        unfocusedTextColor = Negro,
                        focusedIndicatorColor = Gris_Claro,
                        unfocusedIndicatorColor = Gris_Oscuro,
                        focusedLabelColor = Blanco,
                        cursorColor = Blanco
                    )
                )
            }
        }
    }
}