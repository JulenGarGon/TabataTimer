package com.example.tabatatimer.temporizador

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TemporizadorViewModel: ViewModel() {

    private val _tiempoRestante = MutableStateFlow(180)
    val tiempoRestante: StateFlow<Int> = _tiempoRestante

    private val _valorBarra = MutableStateFlow(180f)
    val valorBarra: StateFlow<Float> = _valorBarra

    private val _activado = MutableStateFlow(false)
    val activado: StateFlow<Boolean> = _activado

    private var cuentaAtras: Job? = null

    fun actualizaBarra(valor: Float){
        _valorBarra.value = valor
        if (!_activado.value){
            _tiempoRestante.value = valor.toInt()
        }
    }

    fun start() {
        if (_activado.value || _tiempoRestante.value <= 0) return

        _activado.value = true
        cuentaAtras = viewModelScope.launch {
            while (_tiempoRestante.value > 0){
                delay(1000L)
                _tiempoRestante.value = _tiempoRestante.value - 1
            }
            _activado.value = false
        }
    }

    fun pause(){
        _activado.value = false
        cuentaAtras?.cancel()
    }

    fun reset(){
        pause()
        _tiempoRestante.value = _valorBarra.value.toInt()
    }

    private fun enviarNotificacion(context: Context, accion: String) {
        val intent = Intent(context, TemporizadorNotificacionService::class.java).apply {
            this.action = accion
            if (action == "START" || action == "UPDATE"){
                putExtra("tiempo", _tiempoRestante.value)
            }
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) context.startForegroundService(intent)
        else context.startService(intent)
    }

    fun startNotificacion(context: Context){
        if (_activado.value || _tiempoRestante.value <= 0) return

        _activado.value = true
        enviarNotificacion(context, "START")

        cuentaAtras = viewModelScope.launch {
            while (_tiempoRestante.value > 0 && _activado.value) {
                enviarNotificacion(context, "UPDATE")
                delay(1000L)
                _tiempoRestante.value = _tiempoRestante.value - 1
            }

            _activado.value = false
            _tiempoRestante.value = _valorBarra.value.toInt()
            enviarNotificacion(context, "STOP")
        }
    }


    fun pauseNotificacion(context: Context){
        if (!_activado.value) return

        pause()
        enviarNotificacion(context, "STOP")
    }

    fun resetNotificacion(context: Context){
        if (_tiempoRestante.value == _valorBarra.value.toInt()) return

        reset()
        enviarNotificacion(context, "STOP")
    }
}