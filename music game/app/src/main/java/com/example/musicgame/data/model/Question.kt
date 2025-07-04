package com.example.musicgame.data.model

data class Question(
    val text: String = "",
    val options: List<String> = emptyList(),
    val correctAnswer: String = ""
)