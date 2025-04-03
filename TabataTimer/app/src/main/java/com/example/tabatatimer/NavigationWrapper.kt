package com.example.tabatatimer

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.tabatatimer.initial.InitialScreen
import com.example.tabatatimer.signup.SignUpScreen
import com.google.firebase.auth.FirebaseAuth

@Composable
fun NavigationWrapper(navHostController: NavHostController, auth: FirebaseAuth){
    val navController = rememberNavController()

    NavHost(navController = navHostController, startDestination = "initial"){
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
    }
}