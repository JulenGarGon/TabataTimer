package com.example.tabatatimer.musculos

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
class MusculosViewModel : ViewModel() {
    private val _fechaActual = mutableStateOf(LocalDate.now())
    val fechaActual: State<LocalDate> = _fechaActual

    fun avanzarDia(){
        _fechaActual.value = _fechaActual.value.plusDays(1)
    }

    fun retrocederDia(){
        _fechaActual.value = _fechaActual.value.minusDays(1)
    }

    fun obtenerTextoFecha(): String {
        return if (_fechaActual.value == LocalDate.now()){
            "Hoy"
        } else {
            _fechaActual.value.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))
        }
    }

}