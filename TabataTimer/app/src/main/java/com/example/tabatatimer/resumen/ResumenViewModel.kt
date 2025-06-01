package com.example.tabatatimer.resumen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tabatatimer.model.TopEjercicio
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class ResumenViewModel: ViewModel() {
    private val usuario = FirebaseAuth.getInstance().currentUser?.email
    private var db: FirebaseFirestore = Firebase.firestore

    private val _ejercicios = MutableStateFlow<List<TopEjercicio>>(emptyList())
    val ejercicios: StateFlow<List<TopEjercicio>> = _ejercicios.asStateFlow()

    init {
        getResumen()
    }

    private fun getResumen() {
        val usuarioEmail = usuario ?: return

        db.collection("resumen")
            .document(usuarioEmail)
            .collection("ultima")
            .addSnapshotListener { snapshots, error ->
                if (error != null || snapshots == null) {
                    Log.w("FIRESTORE", "Error al escuchar cambios en resumen", error)
                    return@addSnapshotListener
                }

                viewModelScope.launch {
                    val lista = mutableListOf<TopEjercicio>()

                    for (docUltima in snapshots.documents) {
                        val nombre = docUltima.id.replace("_", " ").replaceFirstChar { it.uppercase() }

                        val pesoUltimo = docUltima.getDouble("peso") ?: 0.0
                        val repsUltimo = docUltima.getLong("repeticiones")?.toInt() ?: 0

                        val docMejor = db.collection("resumen")
                            .document(usuarioEmail)
                            .collection("mejor")
                            .document(docUltima.id)
                            .get()
                            .await()

                        val pesoMejor = docMejor.getDouble("peso") ?: 0.0
                        val repsMejor = docMejor.getLong("repeticiones")?.toInt() ?: 0

                        lista.add(
                            TopEjercicio(
                                nombre = nombre,
                                pesoUltimo = pesoUltimo,
                                repeticionesUltimo = repsUltimo,
                                pesoMejor = pesoMejor,
                                repeticionesMejor = repsMejor
                            )
                        )
                    }

                    _ejercicios.value = lista
                }
            }
    }

    suspend fun getAllResumen(): List<TopEjercicio>{

        val listaFinal = mutableListOf<TopEjercicio>()

        return try {
            val usuarioEmail = usuario ?: return emptyList()

            val ultimosEjercicios = db.collection("resumen")
                .document(usuarioEmail)
                .collection("ultima")
                .get()
                .await()

            for (ejercicio in ultimosEjercicios){
                val nombre = ejercicio.id.replace("_", " ").replaceFirstChar { it.uppercase() }

                val pesoUltimo = ejercicio.getDouble("peso") ?: 0.0
                val repsUltimo = ejercicio.getLong("repeticiones")?.toInt() ?: 0

                val ejercicioMejor = db.collection("resumen")
                    .document(usuarioEmail)
                    .collection("mejor")
                    .document(ejercicio.id)
                    .get()
                    .await()

                val pesoMejor = ejercicioMejor.getDouble("peso") ?: 0.0
                val repsMejor = ejercicioMejor.getLong("repeticiones")?.toInt() ?: 0

                listaFinal.add(
                    TopEjercicio(
                        nombre = nombre,
                        pesoUltimo = pesoUltimo,
                        pesoMejor = pesoMejor,
                        repeticionesUltimo = repsUltimo,
                        repeticionesMejor = repsMejor
                    )
                )
            }
            listaFinal
        } catch (e: Exception){
            Log.i("FIRESTORE", e.toString())
            emptyList()
        }
    }
}