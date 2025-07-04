package com.example.musicgame.ui.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class SettingsViewModel : ViewModel() {

    // Estado para el switch de sonido
    private val _soundEnabled = MutableStateFlow(true)
    val soundEnabled: StateFlow<Boolean> = _soundEnabled.asStateFlow()

    // Función para manejar el evento de cambio de switch
    fun onSoundToggle(isEnabled: Boolean) {
        _soundEnabled.value = isEnabled
        // Aquí se podría guardar el estado en SharedPreferences o en un repositorio.
        // Por ejemplo: settingsRepository.saveSoundPreference(isEnabled)
    }

    // Función para manejar el evento de restablecer progreso
    fun onResetProgress() {
        // Lógica para restablecer el progreso del usuario
        // Por ejemplo, llamar a un repositorio:
        // progressRepository.resetUserProgress()
    }
}