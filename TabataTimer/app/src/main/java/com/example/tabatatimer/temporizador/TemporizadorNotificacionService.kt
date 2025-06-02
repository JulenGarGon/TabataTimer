package com.example.tabatatimer.temporizador

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.tabatatimer.R

class TemporizadorNotificacionService : Service(){

    private val CANAL_ID = "temporizador_canal"
    private val CANAL_FINAL_ID = "temporizador_canal_final"
    private val NOTIFICACION_ID = 1
    private var tiempoActual = 0

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when(intent?.action){
            "START", "UPDATE" -> {
                val tiempo = intent.getIntExtra("tiempo", -1)
                if (tiempo != -1) {
                    tiempoActual = tiempo
                    val notificacion = buildNotificacion(tiempoActual)
                    val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                    if (intent.action == "START") {
                        Log.d("TEMPORIZADOR", "SE ESTÁ EJECUTANDO 3")
                        startForeground(NOTIFICACION_ID, notificacion)
                    } else {
                        manager.notify(NOTIFICACION_ID, notificacion)
                    }
                }
            }
            "STOP" -> {
                try {
                    stopForeground(STOP_FOREGROUND_REMOVE)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                try {
                    stopSelf()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            "FINAL" -> {
                val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                val notificacion = buildFinalNotificacion()
                manager.notify(999, notificacion)
            }

        }
        return START_NOT_STICKY
    }

    private fun buildNotificacion(tiempo: Int): Notification {
        val min = tiempo / 60
        val seg = tiempo % 60

        val canalId = CANAL_ID
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val canal = NotificationChannel(canalId, "Temporizador", NotificationManager.IMPORTANCE_LOW)
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(canal)
        }

        return NotificationCompat.Builder(this, canalId)
            .setContentTitle("Temporizador activado")
            .setContentText(String.format("%02d:%02d", min, seg))
            .setSmallIcon(R.drawable.ic_clock)
            .build()
    }

    override fun onBind(intent: Intent?): IBinder? = null


    private fun buildFinalNotificacion(): Notification {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val sonido = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

            val canal = NotificationChannel(
                CANAL_FINAL_ID,
                "Fin de descanso",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                enableVibration(true)
                vibrationPattern = longArrayOf(0, 500, 300, 500)
                setSound(sonido, null)
            }

            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(canal)
        }

        return NotificationCompat.Builder(this, CANAL_FINAL_ID)
            .setContentTitle("Descanso terminado")
            .setContentText("¡Es hora de volver al ejercicio!")
            .setSmallIcon(R.drawable.ic_clock)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .build()
    }

}