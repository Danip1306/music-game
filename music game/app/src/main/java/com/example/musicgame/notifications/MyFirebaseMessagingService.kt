package com.example.musicgame.notifications

import android.util.Log
import com.example.musicgame.notifications.NotificationHelper
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await // Importación necesaria para .await()

class MyFirebaseMessagingService : FirebaseMessagingService() {

    private val TAG = "MyFirebaseMsgService"
    private val firestore = FirebaseFirestore.getInstance()

    // Este método se llama cuando se genera un nuevo token de registro para el dispositivo.
    // Debes enviar este token a tu servidor/Firestore para poder enviar mensajes a este dispositivo.
    override fun onNewToken(token: String) {
        Log.d(TAG, "Refreshed token: $token")
        // Envía el token a tu servidor para que puedas enviar notificaciones a este dispositivo.
        // Por ahora, lo guardaremos en Firestore para simplificar las pruebas.
        sendRegistrationToServer(token)
    }

    // Este método se llama cuando recibes un mensaje FCM.
    // Se invoca cuando la app está en primer plano.
    // Si la app está en segundo plano y el mensaje tiene una carga 'notification',
    // Android muestra la notificación automáticamente. Si solo tiene carga 'data',
    // este método se llama incluso en segundo plano.
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Log.d(TAG, "From: ${remoteMessage.from}")

        // Revisa si el mensaje contiene una carga de datos.
        if (remoteMessage.data.isNotEmpty()) {
            Log.d(TAG, "Message data payload: ${remoteMessage.data}")

            val title = remoteMessage.data["title"] ?: "Nueva Notificación"
            val message = remoteMessage.data["message"] ?: "Has recibido un mensaje."

            // Muestra la notificación usando tu NotificationHelper
            NotificationHelper.showTestNotification(applicationContext, title, message)
        }

        // Revisa si el mensaje contiene una carga de notificación.
        // Si la app está en segundo plano, Android gestiona esto automáticamente.
        // Si la app está en primer plano, este bloque se ejecutará.
        remoteMessage.notification?.let {
            Log.d(TAG, "Message Notification Body: ${it.body}")
            // Puedes decidir si mostrar esta notificación también manualmente
            // o dejar que Android la muestre (si la app está en background).
            // Para ser consistente, vamos a mostrarla siempre nosotros si llega.
            NotificationHelper.showTestNotification(
                applicationContext,
                it.title ?: "Notificación",
                it.body ?: "Mensaje vacío"
            )
        }
    }

    // Función auxiliar para guardar el token en Firestore.
    // En una aplicación real, probablemente enviarías esto a tu propio backend.
    private fun sendRegistrationToServer(token: String?) {
        if (token == null) {
            Log.w(TAG, "Token is null, cannot save to server.")
            return
        }

        CoroutineScope(Dispatchers.IO).launch {
            try {
                // Puedes guardar el token en una colección 'fcmTokens'
                // Cada documento podría ser el token en sí mismo o un ID de usuario con su token.
                // Para simplificar, usaremos el token como ID del documento.
                firestore.collection("fcmTokens").document(token)
                    .set(mapOf("token" to token, "timestamp" to System.currentTimeMillis()))
                    .await()
                Log.d(TAG, "FCM token saved to Firestore: $token")
            } catch (e: Exception) {
                Log.e(TAG, "Error saving FCM token to Firestore: ${e.message}", e)
            }
        }
    }
}