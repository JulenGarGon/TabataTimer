package com.example.tabatatimer.ejercicio

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.example.tabatatimer.model.Ejercicio
import com.example.tabatatimer.model.EjercicioRealizado
import android.content.Context
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.lang.Error
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID

class EjercicioDetalleViewModel: ViewModel() {

    fun guardarEjercicio( context: Context, ejercicio: Ejercicio, peso: Float, repeticiones: Int){
        val auth = Firebase.auth
        val db = Firebase.firestore

        val email = auth.currentUser?.email ?: return
        val fecha = SimpleDateFormat("d-M-yyyy", Locale.getDefault()).format(Date())
        val idAleatorio = UUID.randomUUID().toString()

        val ejercicioRealizado = EjercicioRealizado(
            ejercicio = ejercicio.nombre,
            id = ejercicio.idDocumento,
            peso = peso,
            repeticiones = repeticiones
        )

        db.collection("ej_realizado")
            .document(email)
            .collection(fecha)
            .document(idAleatorio)
            .set(ejercicioRealizado)
            .addOnSuccessListener {
                Log.d("EJERCICIO_DETALLE", "Ejercicio guardado")
                Toast.makeText(context, "Ejercicio guardado", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener{e ->
                Log.d("EJERCICIO_DETALLE", "Fallo en el guardado: ${e}")
                Toast.makeText(context, "Fallo en el guardado de ejercicio", Toast.LENGTH_SHORT).show()
            }
    }
}