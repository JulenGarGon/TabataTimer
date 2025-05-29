package com.example.tabatatimer.nuevoejercicio

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class NuevoEjercicioViewModel : ViewModel() {
    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    var nombre by mutableStateOf("")
        private set
    var set by mutableStateOf("")
        private set
    var grupoMuscular by mutableStateOf("")
        private set
    var esfuerzo by mutableStateOf<Map<String, Int>>(emptyMap())
        private set
    var imagen by mutableStateOf("")
        private set
    var video by mutableStateOf("")
        private set

    fun onNombreChanged(value: String) { nombre = value }
    fun onSetChanged(value: String) { set = value }
    fun onGrupoMuscularChanged(value: String) {
        grupoMuscular = value
        onEsfuerzoChanged(value, esfuerzo[value] ?: 1)
    }

    fun onEsfuerzoChanged(musculo: String, valor: Int) {
        esfuerzo = esfuerzo.toMutableMap().apply {
            this[musculo] = valor
        }
    }

    fun onImagenChanged(value: String) { imagen = value }
    fun onVideoChanged(value: String) { video = value }

    fun datosCompletos(): Boolean {
        return nombre.isNotBlank() &&
                set.isNotBlank() &&
                grupoMuscular.isNotBlank() &&
                imagen.isNotBlank() &&
                video.isNotBlank()
    }

    fun guardarEjercicio(context: android.content.Context) {
        val correoUsuario = auth.currentUser?.email ?: return
        val idDocumento = nombre.lowercase().replace(" ", "_")

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val usuarioRef = db.collection("ejercicio_usuario").document(correoUsuario)
                val documentos = usuarioRef.get().await().data ?: emptyMap()

                val maxId = documentos.values.mapNotNull {
                    (it as? Map<*, *>)?.get("id")?.toString()?.toIntOrNull()
                }.maxOrNull() ?: 0

                val id = maxId + 1

                val datos = hashMapOf(
                    "idDocumento" to idDocumento,
                    "id" to id,
                    "nombre" to nombre,
                    "grupoMuscular" to grupoMuscular,
                    "set" to set,
                    "esfuerzo" to esfuerzo,
                    "imagen" to imagen,
                    "video" to video
                )

                usuarioRef.set(mapOf(idDocumento to datos), SetOptions.merge())
                    .addOnSuccessListener {
                        Toast.makeText(context, "Ejercicio guardado", Toast.LENGTH_SHORT).show()
                        resetFormulario()
                    }
                    .addOnFailureListener {
                        Toast.makeText(context, "Error en el guardado", Toast.LENGTH_SHORT).show()
                    }
            } catch (e: Exception) {
                Toast.makeText(context, "Error al guardar: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
    fun resetFormulario() {
        nombre = ""
        set = ""
        grupoMuscular = ""
        esfuerzo = emptyMap()
        imagen = ""
        video = ""
    }
}

