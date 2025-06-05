package com.example.tabatatimer.auth

import android.content.Context
import android.widget.Toast
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth

fun logoutUsuario (context: Context, navController: NavController){
    FirebaseAuth.getInstance().signOut()
    Toast.makeText(context, "Sesión cerrada", Toast.LENGTH_SHORT).show()
    navController.navigate("initial") {
        popUpTo(0) { inclusive = true }
    }
}

fun eliminarCuenta(context: Context, navController: NavController) {
    val usuario = FirebaseAuth.getInstance().currentUser
    usuario?.delete()?.addOnCompleteListener { task ->
        if (task.isSuccessful) {
            Toast.makeText(context, "Cuenta eliminada", Toast.LENGTH_SHORT).show()
            navController.navigate("initial") {
                popUpTo(0) { inclusive = true }
            }
        } else {
            Toast.makeText(context, "Error: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
        }
    }
}