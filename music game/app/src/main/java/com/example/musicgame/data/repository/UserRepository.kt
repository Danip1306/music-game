package com.example.musicgame.data.repository

import com.example.musicgame.data.model.User
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import android.util.Log // Asegúrate de tener esta importación

class UserRepository {

    private val firestore = FirebaseFirestore.getInstance()
    private val usersCollection = firestore.collection("users") // Nombre de tu colección de usuarios

    /**
     * Crea o actualiza el perfil de un usuario en Firestore.
     * El UID del usuario se usa como ID del documento.
     */
    suspend fun createUserProfile(user: User) {
        try {
            // Esta debería ser la línea 18 si el conteo comienza en "suspend fun..."
            usersCollection.document(user.uid).set(user).await()
            Log.d("UserRepository", "Perfil de usuario creado/actualizado para UID: ${user.uid}")
        } catch (e: Exception) {
            Log.e("UserRepository", "Error creando/actualizando perfil para UID: ${user.uid}: ${e.message}", e)
            throw e
        }
    }

    /**
     * Obtiene el perfil de un usuario de Firestore por su UID.
     */
    suspend fun getUserProfile(uid: String): User? {
        return try {
            val documentSnapshot = usersCollection.document(uid).get().await()
            documentSnapshot.toObject(User::class.java)
        } catch (e: Exception) {
            Log.e("UserRepository", "Error obteniendo perfil para UID: $uid: ${e.message}", e)
            null // Devolver null en caso de error
        }
    }
}