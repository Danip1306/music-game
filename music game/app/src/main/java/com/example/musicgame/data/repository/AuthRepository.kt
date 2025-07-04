package com.example.musicgame.data.repository

import android.util.Log
import com.example.musicgame.data.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class AuthRepository {

    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()
    private val usersCollection = db.collection("users")

    suspend fun login(email: String, password: String): Boolean {
        return try {
            auth.signInWithEmailAndPassword(email, password).await()
            true
        } catch (e: Exception) {
            Log.e("AuthRepository", "Error en login: ${e.message}", e)
            false
        }
    }

    suspend fun register(username: String, email: String, password: String): Boolean {
        return try {
            val userCredential = auth.createUserWithEmailAndPassword(email, password).await()
            val userId = userCredential.user?.uid

            if (userId != null) {
                val newUser = User(
                    uid = userId,
                    username = username,
                    email = email
                )
                usersCollection.document(userId).set(newUser).await()
                true
            } else {
                Log.e("AuthRepository", "UID de usuario nulo después del registro.")
                false
            }
        } catch (e: FirebaseAuthUserCollisionException) {
            Log.e("AuthRepository", "El correo electrónico ya está en uso: ${e.message}", e)
            false
        } catch (e: Exception) {
            Log.e("AuthRepository", "Error en registro: ${e.message}", e)
            false
        }
    }

    fun getCurrentUserUid(): String? {
        return auth.currentUser?.uid
    }

    fun logout() {
        auth.signOut()
    }
}