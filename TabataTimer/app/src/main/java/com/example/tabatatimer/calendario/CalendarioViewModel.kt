package com.example.tabatatimer.calendario

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tabatatimer.model.EjercicioRealizado
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.time.LocalDate

class CalendarioViewModel : ViewModel() {
    private val usuario = FirebaseAuth.getInstance().currentUser?.email
    private val db: FirebaseFirestore = Firebase.firestore

    private val _ejerciciosDia = MutableStateFlow<List<EjercicioRealizado>>(emptyList())
    val ejercicioDia: StateFlow<List<EjercicioRealizado>> = _ejerciciosDia.asStateFlow()

    private val _fechaSeleccionada = MutableStateFlow<LocalDate?>(null)
    val fechaSeleccionada: MutableStateFlow<LocalDate?> = _fechaSeleccionada

    fun setFechaSeleccionada(fecha: LocalDate) {
        if (_fechaSeleccionada.value == fecha) return
        _fechaSeleccionada.value = fecha
        val formatoFirebase = "${fecha.dayOfMonth}-${fecha.monthValue}-${fecha.year}"

        getEjerciciosRealizados(formatoFirebase)
        //mostrarDatos(fecha)
    }

    private fun mostrarDatos(fecha: String) {
        db.collection("ej_realizado")
            .document(usuario.toString())
            .collection(fecha)
            .get()
            .addOnSuccessListener { result ->
                val ejercicios = result.map { it.toObject(EjercicioRealizado::class.java) }
                Log.i("DATOS OBTENIDOS", ejercicios.toString())
            }
            .addOnFailureListener { exception ->
                Log.e("DATOS OBTENIDOS", "Error al obtener datos", exception)
            }
    }

    private fun getEjerciciosRealizados(fecha: String) {
        viewModelScope.launch {
            val result: List<EjercicioRealizado> = withContext(Dispatchers.IO) {
                getAllEjerciciosDia(fecha)
            }
            _ejerciciosDia.value = result
        }
    }

    private suspend fun getAllEjerciciosDia(fecha: String): List<EjercicioRealizado> {
        return try {
            db.collection("ej_realizado")
                .document(usuario.toString())
                .collection(fecha)
                .get()
                .await()
                .documents
                .mapNotNull { it.toObject(EjercicioRealizado::class.java) }
        } catch (e: Exception) {
            Log.i("FIRESTORE", e.toString())
            emptyList()
        }
    }
}
