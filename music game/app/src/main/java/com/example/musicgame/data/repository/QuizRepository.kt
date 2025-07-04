package com.example.musicgame.data.repository

import com.example.musicgame.data.model.Question
import com.google.firebase.firestore.FirebaseFirestore // Importa FirebaseFirestore
import kotlinx.coroutines.tasks.await // Importa para usar await en tareas de Firebase

class QuizRepository {

    private val db = FirebaseFirestore.getInstance() // Obtén una instancia de Firestore

    suspend fun getQuizQuestions(): List<Question> {
        val questionsList = mutableListOf<Question>()
        try {
            // Accede a la colección 'questions' y obtén todos los documentos
            val result = db.collection("questions")
                .get()
                .await() // Espera a que la operación se complete

            // Itera sobre los documentos y conviértelos a objetos Question
            for (document in result.documents) {
                val question = document.toObject(Question::class.java)
                question?.let {
                    questionsList.add(it)
                }
            }
        } catch (e: Exception) {
            // Manejo de errores, por ejemplo, loguear el error
            println("Error al cargar preguntas de Firestore: ${e.message}")
            // Podrías devolver una lista vacía o lanzar el error de nuevo
        }
        return questionsList.shuffled() // Opcional: mezclarlas
    }
}