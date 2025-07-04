package com.example.musicgame.data.model

data class Level(
    val id: String = "", // Usaremos el ID del documento de Firestore como identificador único
    val number: Int = 0, // Un número de nivel para mostrar (ej: Nivel 1, Nivel 2)
    val title: String = "", // Título del nivel (ej: "Introducción a las Notas")
    val description: String = "", // Breve descripción del nivel
    val unlocked: Boolean = false // Si el nivel está desbloqueado por defecto o se desbloquea con progreso
    // Puedes añadir más campos como:
    // val songUrl: String = "",
    // val requiredScore: Int = 0,
    // val imageUrl: String = ""
)