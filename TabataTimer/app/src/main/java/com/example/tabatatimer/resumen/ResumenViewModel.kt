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
    private fun getResumen(){
        viewModelScope.launch {
            val result: List<TopEjercicio> = withContext(Dispatchers.IO){
                getAllResumen()
            }
            _ejercicios.value = result
        }
    }

    suspend fun getAllResumen(): List<TopEjercicio>{

        val listaFinal = mutableListOf<TopEjercicio>()

        return try {
            val nombresEjercicios = db.collection("ejercicios")
                .get()
                .await()
                .map { it.id }

            for (nombre in nombresEjercicios){
                val ejercicio = db.collection("resumen")
                    .document(usuario.toString())
                    .collection(nombre)
                    .get()
                    .await()

                val ejercicios = ejercicio.mapNotNull { it.toObject(TopEjercicio::class.java) }
                listaFinal.addAll(ejercicios)
            }
            listaFinal
        } catch (e: Exception){
            Log.i("FIRESTORE", e.toString())
            emptyList()
        }
    }
}