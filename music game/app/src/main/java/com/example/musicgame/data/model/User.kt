package com.example.musicgame.data.model

data class User(
    val uid: String = "", // El ID único del usuario de Firebase Authentication
    val username: String = "", // El nombre de usuario que el usuario elija
    val email: String = "" // Opcional: duplicar el email aquí para facilitar consultas, aunque ya esté en Firebase Auth
    // Aquí puedes añadir más campos para el perfil del usuario, como:
    // val profilePicUrl: String = "",
    // val totalScore: Int = 0,
    // val completedLevels: List<Int> = emptyList()
)