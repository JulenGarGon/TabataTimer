package com.example.tabatatimer.calendario

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.tabatatimer.model.Ejercicio
import com.example.tabatatimer.model.EjercicioRealizado
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class CalendarioViewModel : ViewModel(){
    //TODO REVISIÓN EXTENSA DEL CÓDIGO Y MODIFICACIÓN 
    val db = FirebaseFirestore.getInstance()


    suspend fun getEjerciciosDia(fecha: String): List<EjercicioRealizado>{
        val email = FirebaseAuth.getInstance().currentUser?.email
        return if (email != null) {
            try {
                val ejercicio = db.collection("ej_realizado")
                    .document(email)
                    .collection("elevaciones_laterales")
                    .whereEqualTo("fecha", fecha)

                val solicitud = ejercicio.get().await()
                val ejerciciosFiltrados = mutableListOf<EjercicioRealizado>()

                for (document in solicitud) {
                    val nombreEjercicio = document.id
                    val ejercicioData = document.data

                    val fechaHora = ejercicioData["fecha_hora"] as? String
                    val peso = ejercicioData["peso"] as? Int
                    val repeticiones = ejercicioData["repeticiones"] as? Int
                    val series = ejercicioData["series"] as? Int

                    ejerciciosFiltrados.add(
                        EjercicioRealizado(
                            nombreEjercicio = nombreEjercicio,
                            fecha = fechaHora ?: "",
                            peso = peso ?: 0,
                            repeticiones = repeticiones ?: 0,
                            series = series ?: 0
                        )
                    )
                }
                ejerciciosFiltrados
            } catch (e: Exception) {
                Log.i("CALENDARIO", e.toString())
                emptyList()
            }
        } else {
            Log.d("CALENDARIO", "No hay un usuario registrado")
            emptyList()
        }
    }
}