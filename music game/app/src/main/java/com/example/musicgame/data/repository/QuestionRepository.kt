package com.example.musicgame.data.repository

import android.util.Log
import com.example.musicgame.data.model.Question
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class QuestionRepository {

    private val firestore = FirebaseFirestore.getInstance()
    private val questionsCollection = firestore.collection("questions")

    suspend fun getQuestionsForLevel(levelId: String): List<Question> {
        return try {
            val querySnapshot = questionsCollection
                .whereEqualTo("levelId", levelId)
                .get()
                .await()

            val questions = querySnapshot.documents.mapNotNull { document ->
                document.toObject(Question::class.java)
            }
            Log.d("QuestionRepository", "Preguntas cargadas para nivel $levelId: ${questions.size}")
            questions
        } catch (e: Exception) {
            Log.e("QuestionRepository", "Error al cargar preguntas para nivel $levelId: ${e.message}", e)
            emptyList()
        }
    }
}