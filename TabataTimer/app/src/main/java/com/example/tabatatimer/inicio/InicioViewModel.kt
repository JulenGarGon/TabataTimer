package com.example.tabatatimer.inicio

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tabatatimer.model.Ejercicio
import com.example.tabatatimer.model.Musculo
import com.example.tabatatimer.model.Sets
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class InicioViewModel: ViewModel() {
    private val database = Firebase.database
    private var db: FirebaseFirestore = Firebase.firestore

    private val _ejercicios = MutableStateFlow<List<Ejercicio>>(emptyList())
    val ejercicio: StateFlow<List<Ejercicio>> = _ejercicios

    private val _sets = MutableStateFlow<List<Sets>>(emptyList())
    val set: StateFlow<List<Sets>> = _sets

    private val _musculos = MutableStateFlow<List<Musculo>>(emptyList())
    val musculo: StateFlow<List<Musculo>> = _musculos

    init {
        getEjercicio()
        getSets()
        getMusculos()
    }

    private fun getEjercicio(){
        viewModelScope.launch {
            val result: List<Ejercicio> = withContext(Dispatchers.IO) {
                getAllEjercicios()
            }
            _ejercicios.value = result
        }
    }

    suspend fun getAllEjercicios(): List<Ejercicio>{
        return try{
            db.collection("ejercicios")
                .get()
                .await()
                .documents
                .mapNotNull { it ->
                    it.toObject(Ejercicio::class.java)
                }
        } catch (e: Exception){
            Log.i("FIRESTORE", e.toString())
            emptyList()
        }
    }

    private fun getSets(){
        viewModelScope.launch {
            val result: List<Sets> = withContext(Dispatchers.IO) {
                getAllSets()
            }
            _sets.value = result
        }
    }

    suspend fun getAllSets(): List<Sets>{
        return try{
            db.collection("sets")
                .get()
                .await()
                .documents
                .mapNotNull { it ->
                    it.toObject(Sets::class.java)
                }
        } catch (e: Exception){
            Log.i("FIRESTORE", e.toString())
            emptyList()
        }
    }

    private fun getMusculos(){
        viewModelScope.launch {
            val result: List<Musculo> = withContext(Dispatchers.IO){
                getAllMusculos()
            }
            _musculos.value = result
        }
    }

    suspend fun getAllMusculos(): List<Musculo>{
        return try {
            db.collection("musculos")
                .get()
                .await()
                .documents
                .mapNotNull { it ->
                    it.toObject(Musculo::class.java)
                }
        } catch (e: Exception){
            Log.i("FIRESTORE", e.toString())
            emptyList()
        }
    }
}