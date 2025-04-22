package com.example.tabatatimer

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.tabatatimer.home.HomeScreen
import com.example.tabatatimer.initial.InitialScreen
import com.example.tabatatimer.signup.SignUpScreen
import com.google.firebase.auth.FirebaseAuth

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavigationWrapper(navHostController: NavHostController, auth: FirebaseAuth, destination: String){
    val navController = rememberNavController()

    NavHost(navController = navHostController, startDestination = destination){
        composable("initial"){
            InitialScreen(
                auth,
                navigateToApp = {navHostController.navigate("app")},
                navigateToSignUp = {navHostController.navigate("signup")}
            )
        }
        composable("signup"){
            SignUpScreen(
                auth,
                onBackPressed = { navHostController.navigate("initial")}
            )
        }
        composable("app"){
            HomeScreen()
        }
    }
}