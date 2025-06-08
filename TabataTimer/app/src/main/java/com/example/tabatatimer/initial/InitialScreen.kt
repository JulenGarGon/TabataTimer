package com.example.tabatatimer.initial

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tabatatimer.R
import com.example.tabatatimer.ui.theme.Blanco
import com.example.tabatatimer.ui.theme.Gris_Claro
import com.example.tabatatimer.ui.theme.Gris_Oscuro
import com.example.tabatatimer.ui.theme.Negro
import com.google.firebase.auth.FirebaseAuth


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InitialScreen( auth: FirebaseAuth, navigateToApp: () -> Unit = {}, navigateToSignUp: () -> Unit = {}){
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    val customFontFamily = FontFamily(
        Font(R.font.groteskmedium)
    )
    val context = LocalContext.current
    Scaffold (
        topBar = {
            TopAppBar(
                title = { Text(text = "") },
                navigationIcon = {
                    Text(
                        text = stringResource(R.string.app_name),
                        fontWeight = FontWeight.Bold,
                        fontSize = 28.sp,
                        modifier = Modifier
                            .padding(start = 1.dp),
                        style = TextStyle(
                            fontFamily = customFontFamily
                        )
                    )
                },
                actions = {
                    IconButton(
                        onClick = {},
                        modifier = Modifier
                            .padding(end = 16.dp)
                            .size(width = 100.dp, height = 100.dp)
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.logo_masorange),
                            contentDescription = "Logo",
                            modifier = Modifier.size(240.dp),
                            tint = Color.Unspecified
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Gris_Claro
                )
            )
        }
    ){
        innerPadding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            color = MaterialTheme.colorScheme.background
        ) {
            Column (
                modifier = Modifier
                    .fillMaxSize()
                    .background(Brush.verticalGradient(
                        listOf(Gris_Oscuro, Negro)
                    )),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .height(400.dp)
                        .background(Brush.verticalGradient(
                            listOf(Negro, Gris_Oscuro)
                        ), shape = RoundedCornerShape(8.dp)),
                    contentAlignment = Alignment.CenterStart
                ){
                    Column (
                        modifier = Modifier
                            .fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ){
                        Text(text = stringResource(R.string.bienvenido),
                            color = Blanco,
                            fontSize = 30.sp,
                            style = TextStyle(
                                fontFamily = customFontFamily
                            ),
                            modifier = Modifier
                                .padding(top = 60.dp)
                        )

                        TextField(
                            value = email,
                            onValueChange = {email = it},
                            label = { Text(stringResource(R.string.user_name)) },
                            modifier = Modifier
                                .fillMaxWidth(0.7f)
                                .padding(bottom = 8.dp, top = 16.dp),
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

                        TextField(
                            value = password,
                            onValueChange = {password = it},
                            label = { Text(stringResource(R.string.password)) },
                            modifier = Modifier
                                .fillMaxWidth(0.7f)
                                .padding(bottom = 16.dp, top = 8.dp),
                            singleLine = true,
                            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                            trailingIcon = {
                                val image = if (passwordVisible)
                                    Icons.Default.Visibility
                                else Icons.Default.VisibilityOff

                                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                    Icon(imageVector = image, contentDescription = null)
                                }
                            },
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

                        val isButtonEnabled = email.isNotBlank() && password.isNotBlank()

                        Button(
                            onClick = {
                                auth.signInWithEmailAndPassword(email, password).addOnCompleteListener{
                                    task ->
                                        if (task.isSuccessful){
                                            navigateToApp()
                                            Log.i("APP", "Login realizado")
                                        } else {
                                            Log.i("APP", "Error, usuario o contraseña erronea")
                                            Toast.makeText(context, "Usuario o contraseña incorrecto", Toast.LENGTH_SHORT).show()
                                        }
                                }
                            },
                            enabled = isButtonEnabled,
                            modifier = Modifier
                                .fillMaxWidth(0.8f)
                                .padding(top = 8.dp),
                            shape = RoundedCornerShape(4.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Blanco,
                                contentColor = Negro
                            )
                        ) {
                            Text(text = stringResource(R.string.login_now))
                        }

                        TextButton(
                            onClick = {
                                navigateToSignUp( )
                            }
                        ) {
                            Text(
                                text = stringResource(R.string.sin_cuenta),
                                color = Blanco
                            )
                        }
                    }
                }
            }
        }
    }
}