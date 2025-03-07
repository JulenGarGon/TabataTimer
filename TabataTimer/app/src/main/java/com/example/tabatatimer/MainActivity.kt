package com.example.tabatatimer

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LoginScreen()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen() {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isButtonEnabled by remember { mutableStateOf(false) }

    // Verificar si los campos están completos
    LaunchedEffect(email, password) {
        isButtonEnabled = email.isNotEmpty() && password.isNotEmpty()
    }

    val context = LocalContext.current
    val customFontFamily = FontFamily(
        Font(R.font.groteskmedium)
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text="") },
                navigationIcon = {
                    Text(
                        text = stringResource(R.string.mas_orange),
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(start = 16.dp)
                    )
                },
                actions = {
                    IconButton(
                        onClick = {} ,
                        modifier = Modifier.padding(end = 16.dp)
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.logo_masorange),
                            contentDescription = "Logo",
                            modifier = Modifier
                                .size(240.dp)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = colorResource(R.color.naranja)
                )
            )
        }
    ){ innerPadding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center // Esto centra el contenido verticalmente
            ) {
                // Nombre de la app encima del registro
                Text(
                    text = stringResource(R.string.app_name),
                    style = TextStyle(
                        fontFamily = customFontFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 50.sp
                    ),
                    modifier = Modifier.padding(bottom = 20.dp) // Separación entre el texto y los demás elementos
                )

                // Título
                Text(
                    text = stringResource(R.string.login),
                    fontSize = 30.sp,
                    modifier = Modifier.padding(bottom = 20.dp)
                )

                // Campo de correo electrónico
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text(stringResource(R.string.user_name)) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = {
                            // Acción para mover al siguiente campo (contraseña)
                        }
                    )
                )

                // Campo de contraseña
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text(stringResource(R.string.password)) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done
                    ),
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            // Acción para cerrar el teclado cuando se presiona "Done"
                        }
                    )
                )

                // Botón de inicio de sesión
                Button(
                    onClick = {
                        Toast.makeText(context, "Login successful", Toast.LENGTH_SHORT).show()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    enabled = isButtonEnabled // Habilitar solo si ambos campos están completos
                ) {
                    Text(text = stringResource(R.string.login_now))
                }

                TextButton(
                    onClick = {
                        //Ir al crear cuenta
                    },
                    modifier = Modifier.padding(top = 16.dp)
                ) {
                    Text(
                        text = stringResource(R.string.sin_cuenta),
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    LoginScreen()
}
