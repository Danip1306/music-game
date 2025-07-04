package com.example.musicgame.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musicgame.data.model.Question
import com.example.musicgame.data.repository.QuestionRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import android.util.Log

class TestViewModel(
    private val questionRepository: QuestionRepository = QuestionRepository()
) : ViewModel() {

    private val _questions = MutableStateFlow<List<Question>>(emptyList())
    val questions: StateFlow<List<Question>> = _questions.asStateFlow()

    private val _currentQuestionIndex = MutableStateFlow(0)
    val currentQuestionIndex: StateFlow<Int> = _currentQuestionIndex.asStateFlow()

    private val _selectedAnswers = MutableStateFlow<MutableMap<Int, String?>>(mutableMapOf())
    val selectedAnswers: StateFlow<MutableMap<Int, String?>> = _selectedAnswers.asStateFlow()

    private val _correctAnswersCount = MutableStateFlow(0)
    val correctAnswersCount: StateFlow<Int> = _correctAnswersCount.asStateFlow()

    private val _allQuestionsAnswered = MutableStateFlow(false)
    val allQuestionsAnswered: StateFlow<Boolean> = _allQuestionsAnswered.asStateFlow()

    private var currentTestLevelId: String = ""

    fun loadQuestionsForLevel(levelId: String) {
        currentTestLevelId = levelId
        viewModelScope.launch {
            _questions.value = questionRepository.getQuestionsForLevel(levelId)
            if (_questions.value.isNotEmpty()) {
                _currentQuestionIndex.value = 0
                val initialSelectedAnswers = mutableMapOf<Int, String?>()
                (0 until _questions.value.size).forEach { index ->
                    initialSelectedAnswers[index] = null
                }
                _selectedAnswers.value = initialSelectedAnswers

                _correctAnswersCount.value = 0
                _allQuestionsAnswered.value = false
            } else {
                Log.w("TestViewModel", "No se encontraron preguntas para el nivel: $levelId.")
            }
            Log.d("TestViewModel", "Preguntas cargadas para nivel $levelId: ${_questions.value.size}")
        }
    }

    fun onOptionSelected(questionIndex: Int, selectedOption: String) {
        val updatedMap = _selectedAnswers.value.toMutableMap()
        updatedMap[questionIndex] = selectedOption
        _selectedAnswers.value = updatedMap
        checkAllQuestionsAnswered()
    }

    fun goToNextQuestion() {
        if (_currentQuestionIndex.value < _questions.value.size - 1) {
            _currentQuestionIndex.value = _currentQuestionIndex.value + 1
        }
    }

    fun submitTest() {
        var correctCount = 0
        _questions.value.forEachIndexed { index, question ->
            if (_selectedAnswers.value[index] == question.correctAnswer) {
                correctCount++
            }
        }
        _correctAnswersCount.value = correctCount
        _allQuestionsAnswered.value = true
        Log.d("TestViewModel", "Test enviado. Correctas: $correctCount")
    }

    private fun checkAllQuestionsAnswered() {
        _allQuestionsAnswered.value = _questions.value.all { question ->
            val questionIndex = _questions.value.indexOf(question)
            _selectedAnswers.value[questionIndex] != null
        }
    }
}