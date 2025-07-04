package com.example.musicgame.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musicgame.data.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.example.musicgame.data.model.User // Asegúrate de que User esté importado si lo necesitas aquí

// Define el estado de los resultados de autenticación
sealed class AuthResult {
    object Idle : AuthResult()
    object Loading : AuthResult()
    class Success(val message: String) : AuthResult()
    class Error(val message: String) : AuthResult()
}

class AuthViewModel : ViewModel() {

    // Inyecta el repositorio de autenticación
    private val authRepository: AuthRepository = AuthRepository() // Instancia del repositorio

    private val _loginResult = MutableStateFlow<AuthResult>(AuthResult.Idle)
    val loginResult: StateFlow<AuthResult> = _loginResult

    private val _registerResult = MutableStateFlow<AuthResult>(AuthResult.Idle)
    val registerResult: StateFlow<AuthResult> = _registerResult

    // Si tu ViewModel necesita observar el usuario actual, deberías tener un StateFlow para él
    // private val _currentUser = MutableStateFlow<User?>(null)
    // val currentUser: StateFlow<User?> = _currentUser.asStateFlow()

    fun onLoginClicked(usernameOrEmail: String, password: String) {
        viewModelScope.launch {
            _loginResult.value = AuthResult.Loading
            val success = authRepository.login(usernameOrEmail, password)
            if (success) {
                _loginResult.value = AuthResult.Success("Inicio de sesión exitoso.")
                // Aquí podrías cargar los datos del usuario si el login es exitoso
                // val uid = authRepository.getCurrentUserUid()
                // if (uid != null) fetchUserData(uid)
            } else {
                _loginResult.value = AuthResult.Error("Credenciales incorrectas. Intenta de nuevo.")
            }
        }
    }

    fun onRegisterClicked(username: String, email: String, password: String, confirmPassword: String) {
        viewModelScope.launch {
            _registerResult.value = AuthResult.Loading
            if (password != confirmPassword) {
                _registerResult.value = AuthResult.Error("Las contraseñas no coinciden.")
                return@launch
            }

            val success = authRepository.register(username, email, password)
            if (success) {
                _registerResult.value = AuthResult.Success("Registro exitoso.")
                // Aquí podrías cargar los datos del usuario si el registro es exitoso
                // val uid = authRepository.getCurrentUserUid()
                // if (uid != null) fetchUserData(uid)
            } else {
                _registerResult.value = AuthResult.Error("Error al registrar el usuario. El usuario o email ya existen.")
            }
        }
    }

    fun resetLoginState() {
        _loginResult.value = AuthResult.Idle
    }

    fun resetRegisterState() {
        _registerResult.value = AuthResult.Idle
    }

    // Si tu AuthViewModel tenía una función para obtener datos de usuario, inclúyela aquí.
    // private fun fetchUserData(uid: String) {
    //     viewModelScope.launch {
    //         _currentUser.value = authRepository.getUserData(uid)
    //     }
    // }

    // Si tienes función de logout
    // fun logout() {
    //     authRepository.logout()
    //     _currentUser.value = null
    // }
}