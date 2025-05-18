package com.example.tabatatimer.musculos

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
class MusculosViewModel : ViewModel() {

    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    private val _fechaActual = mutableStateOf(LocalDate.now())
    val fechaActual: State<LocalDate> = _fechaActual

    private val _esfuerzoMuscular = mutableStateOf<Map<String, Float>>(emptyMap())
    val esfuerzoMuscular: State<Map<String, Float>> = _esfuerzoMuscular

    private val _ejerciciosDelDia = mutableStateOf<List<EjercicioRealizadoConEsfuerzo>>(emptyList())
    val ejerciciosDelDia: State<List<EjercicioRealizadoConEsfuerzo>> = _ejerciciosDelDia

    init {
        cargarEsfuerzo()
    }

    fun avanzarDia() {
        _fechaActual.value = _fechaActual.value.plusDays(1)
        cargarEsfuerzo()
    }

    fun retrocederDia() {
        _fechaActual.value = _fechaActual.value.minusDays(1)
        cargarEsfuerzo()
    }

    fun obtenerTextoFecha(): String {
        return if (_fechaActual.value == LocalDate.now()) {
            "Hoy"
        } else {
            _fechaActual.value.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))
        }
    }

    private fun cargarEsfuerzo() {
        val userEmail = auth.currentUser?.email ?: return
        val fecha = _fechaActual.value.format(DateTimeFormatter.ofPattern("d-M-yyyy"))
        val ref = db.collection("ej_realizado").document(userEmail).collection(fecha)

        _ejerciciosDelDia.value = emptyList()
        _esfuerzoMuscular.value = emptyMap()

        ref.get().addOnSuccessListener { docs ->
            if (docs.isEmpty) return@addOnSuccessListener

            val tareas = mutableListOf<Task<DocumentSnapshot>>()
            val datosLocales = mutableListOf<EjercicioRealizadoConEsfuerzo>()
            val esfuerzoAcumulado = mutableMapOf<String, Int>()

            for (doc in docs) {
                val id = doc.getString("id") ?: continue
                val nombre = doc.getString("ejercicio") ?: id
                val peso = doc.getLong("peso")?.toInt() ?: 0
                val repeticiones = doc.getLong("repeticiones")?.toInt() ?: 0

                val tarea = db.collection("ejercicios").document(id).get()
                    .addOnSuccessListener { ejercicioDoc ->
                        val esfuerzoMap = (ejercicioDoc.get("esfuerzo") as? Map<String, Long>)?.mapValues { it.value.toInt() } ?: emptyMap()

                        esfuerzoMap.forEach { (musculo, valor) ->
                            esfuerzoAcumulado[musculo] = esfuerzoAcumulado.getOrDefault(musculo, 0) + valor
                        }

                        datosLocales.add(
                            EjercicioRealizadoConEsfuerzo(
                                nombre = nombre,
                                peso = peso,
                                repeticiones = repeticiones,
                                esfuerzo = esfuerzoMap
                            )
                        )
                    }

                tareas.add(tarea)
            }

            Tasks.whenAllSuccess<DocumentSnapshot>(tareas).addOnSuccessListener {
                val normalizado = esfuerzoAcumulado.mapValues { (_, valor) ->
                    (valor.coerceAtMost(10) / 10f)
                }

                Log.d("MUSCULOS_VIEWMODEL", "Esfuerzo final: $esfuerzoAcumulado")
                Log.d("MUSCULOS_VIEWMODEL", "Normalizado: $normalizado")

                _ejerciciosDelDia.value = datosLocales.toList()
                _esfuerzoMuscular.value = normalizado.toMap()
            }
        }
    }

    data class EjercicioRealizadoConEsfuerzo(
        val nombre: String,
        val peso: Int,
        val repeticiones: Int,
        val esfuerzo: Map<String, Int>
    )
}