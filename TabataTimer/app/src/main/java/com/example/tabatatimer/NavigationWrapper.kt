package com.example.tabatatimer

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.tabatatimer.initial.InitialScreen
import com.google.firebase.auth.FirebaseAuth

@Composable
fun NavigationWrapper(navHostController: NavHostController, auth: FirebaseAuth){

    NavHost(navController = navHostController, startDestination = "initial"){
        composable("initial"){
            InitialScreen(
                auth,
                navigateToApp = {navHostController.navigate("app")},
                navigateToSignUp = {navHostController.navigate("signUp")}
            )
        }
    }
}