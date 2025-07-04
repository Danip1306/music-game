package com.example.musicgame.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musicgame.data.model.Level // Importa tu data class Level
import com.example.musicgame.data.repository.LevelsRepository // Importa tu LevelsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import android.util.Log // Para depuración

class LevelsViewModel(
    private val levelsRepository: LevelsRepository = LevelsRepository() // Inyecta el repositorio
) : ViewModel() {

    // Cambiamos el tipo de la lista de Int a Level
    private val _levels = MutableStateFlow<List<Level>>(emptyList())
    val levels: StateFlow<List<Level>> = _levels.asStateFlow()

    init {
        Log.d("LevelsViewModel", "LevelsViewModel initialized. Loading levels from repository...")
        loadLevels()
        // Opcional: Descomenta la siguiente línea solo la primera vez para añadir niveles de prueba
        // Esto solo es para poblar Firestore si está vacío. Quítalo en producción.
        // viewModelScope.launch { levelsRepository.addSampleLevels() }
    }

    private fun loadLevels() {
        viewModelScope.launch {
            _levels.value = levelsRepository.getLevels()
            Log.d("LevelsViewModel", "Niveles cargados en ViewModel: ${_levels.value.size}")
        }
    }

    fun onLevelClick(levelNumber: Int) {
        // Aquí podrías añadir lógica para interactuar con el ViewModel
        // por ejemplo, para registrar que un nivel fue clicado,
        // o para comprobar si está desbloqueado antes de navegar.
        Log.d("LevelsViewModel", "Nivel $levelNumber clicado. (Lógica en desarrollo)")
    }
}