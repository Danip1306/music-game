package com.example.musicgame.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.musicgame.R

object NotificationHelper {

    private const val CHANNEL_ID = "music_game_channel"
    private const val CHANNEL_NAME = "Music Game Notifications"
    private const val CHANNEL_DESCRIPTION = "Notifications for Music Game app updates and events"

    fun createNotificationChannel(context: Context) {
        // Solo necesitamos crear el canal si la versión de Android es Oreo (API 26) o superior
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Cambiamos la importancia a HIGH para que las notificaciones se desplieguen (heads-up)
            val importance = NotificationManager.IMPORTANCE_HIGH // CAMBIO AQUÍ: IMPORTANCE_HIGH
            val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, importance).apply {
                description = CHANNEL_DESCRIPTION
                // Opcional: Habilitar luces y vibración para alta importancia
                enableLights(true)
                enableVibration(true)
                // Puedes personalizar el patrón de vibración si lo deseas
                // vibrationPattern = longArrayOf(100, 200, 300, 400)
            }
            // Registra el canal con el sistema
            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    fun showTestNotification(context: Context, title: String, message: String) {
        val notificationBuilder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground) // Usa un ícono apropiado para tu app
            .setContentTitle(title)
            .setContentText(message)
            // Cambiamos la prioridad a HIGH para que se muestre como heads-up
            .setPriority(NotificationCompat.PRIORITY_HIGH) // CAMBIO AQUÍ: PRIORITY_HIGH
            .setAutoCancel(true) // Cierra la notificación automáticamente cuando el usuario la toca
            // Añade esto para que la notificación aparezca como heads-up
            .setDefaults(NotificationCompat.DEFAULT_ALL) // Usa valores por defecto de sonido, vibración y luz
            // Esta línea es crucial para forzar el heads-up en algunos casos, aunque HIGH/MAX ya lo implican
            .setVibrate(longArrayOf(0, 1000)) // Pequeña vibración para heads-up, personaliza si quieres
            .setLights(0xFF0000FF.toInt(), 300, 1000) // Luces: color azul, encendido 300ms, apagado 1000ms

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        // Usar un ID de notificación único si vas a enviar múltiples notificaciones.
        // Por ahora, 0 está bien para pruebas, pero si envías varias, se sobrescribirán.
        notificationManager.notify(System.currentTimeMillis().toInt(), notificationBuilder.build())
    }
}