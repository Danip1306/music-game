package com.example.musicgame.data.repository

import com.example.musicgame.data.model.Level
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import android.util.Log // Para depuración

class LevelsRepository {

    private val firestore = FirebaseFirestore.getInstance()
    private val levelsCollection = firestore.collection("levels") // Nombre de tu colección en Firestore

    suspend fun getLevels(): List<Level> {
        return try {
            val querySnapshot = levelsCollection
                .orderBy("number") // Ordenar por el campo 'number' para asegurar el orden correcto
                .get()
                .await() // Espera a que la operación de Firebase se complete

            val levels = querySnapshot.documents.mapNotNull { document ->
                // Mapear cada documento a un objeto Level
                // Asegúrate de que los nombres de los campos en Level coincidan con los de Firestore
                document.toObject(Level::class.java)?.copy(id = document.id)
            }
            Log.d("LevelsRepository", "Niveles cargados desde Firestore: ${levels.size}")
            levels
        } catch (e: Exception) {
            Log.e("LevelsRepository", "Error al cargar niveles desde Firestore: ${e.message}", e)
            emptyList() // Devuelve una lista vacía en caso de error
        }
    }

    // Opcional: Función para añadir niveles de prueba si aún no los tienes en Firestore
    suspend fun addSampleLevels() {
        val sampleLevels = listOf(
            Level(number = 1, title = "Introducción a las Notas", description = "Aprende las notas básicas.", unlocked = true),
            Level(number = 2, title = "Ritmo y Compás", description = "Entiende los conceptos de ritmo y compás.", unlocked = true),
            Level(number = 3, title = "Melodías Simples", description = "Crea tus primeras melodías.", unlocked = false),
            Level(number = 4, title = "Armonía Básica", description = "Descubre los acordes y la armonía.", unlocked = false),
            Level(number = 5, title = "Escalas Musicales", description = "Explora las diferentes escalas.", unlocked = false)
            // Añade más niveles según necesites
        )

        sampleLevels.forEach { level ->
            try {
                // Si quieres que el ID sea autogenerado por Firestore, no uses 'id' en el document()
                levelsCollection.add(level).await()
                Log.d("LevelsRepository", "Nivel de muestra '${level.title}' añadido.")
            } catch (e: Exception) {
                Log.e("LevelsRepository", "Error al añadir nivel de muestra: ${e.message}", e)
            }
        }
    }
}