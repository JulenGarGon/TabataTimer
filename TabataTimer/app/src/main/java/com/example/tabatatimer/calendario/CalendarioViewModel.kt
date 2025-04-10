package com.example.tabatatimer.calendario

import androidx.lifecycle.ViewModel
import com.example.tabatatimer.model.EjercicioRealizado
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class CalendarioViewModel : ViewModel(){
    private val usuario = FirebaseAuth.getInstance().currentUser
    private var db: FirebaseFirestore = Firebase.firestore

    private val _ejerciciosDia = MutableStateFlow<List<EjercicioRealizado>>(emptyList())
    val ejercicioDia: StateFlow<List<EjercicioRealizado>> = _ejerciciosDia

    val fecha: String? = null

    fun getFecha(fechaSeleccionada: String){

    }
}