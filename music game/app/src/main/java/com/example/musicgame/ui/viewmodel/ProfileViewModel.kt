package com.example.musicgame.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import android.util.Log
import java.text.SimpleDateFormat
import java.util.*
import com.example.musicgame.data.repository.UserRepository // ¡NUEVA IMPORTACIÓN!
import com.example.musicgame.data.model.User // ¡IMPORTA TU DATA CLASS USER!

class ProfileViewModel : ViewModel() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val userRepository: UserRepository = UserRepository() // ¡INSTANCIA DE USERREPOSITORY!

    private val _currentUser = MutableStateFlow<FirebaseUser?>(null)
    val currentUser: StateFlow<FirebaseUser?> = _currentUser.asStateFlow()

    private val _username = MutableStateFlow<String?>("Cargando...") // ¡NUEVA STATEFLOW PARA EL NOMBRE DE USUARIO!
    val username: StateFlow<String?> = _username.asStateFlow()

    private val _creationDate = MutableStateFlow<String?>(null)
    val creationDate: StateFlow<String?> = _creationDate.asStateFlow()

    private val _isLoggedOut = MutableStateFlow(false)
    val isLoggedOut: StateFlow<Boolean> = _isLoggedOut.asStateFlow()

    init {
        // Obtener el usuario actual de FirebaseAuth
        val firebaseAuthUser = auth.currentUser
        _currentUser.value = firebaseAuthUser

        // Si hay un usuario logeado, intentar cargar su perfil de Firestore
        firebaseAuthUser?.let { user ->
            // Obtener fecha de creación
            user.metadata?.creationTimestamp?.let { timestamp ->
                val date = Date(timestamp)
                val formatter = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
                _creationDate.value = formatter.format(date)
            }

            // ¡NUEVA LÓGICA! Cargar el perfil de usuario desde Firestore
            viewModelScope.launch {
                try {
                    val userProfile = userRepository.getUserProfile(user.uid) // Usar el UID para obtener el perfil
                    _username.value = userProfile?.username ?: user.displayName ?: "Usuario" // Preferir el de Firestore, luego displayName, luego genérico
                    Log.d("ProfileViewModel", "Username from Firestore: ${_username.value}")
                } catch (e: Exception) {
                    Log.e("ProfileViewModel", "Error cargando perfil de usuario de Firestore: ${e.message}", e)
                    _username.value = user.displayName ?: "Error al cargar nombre" // Fallback si hay error
                }
            }
        } ?: run {
            // No hay usuario logeado
            _username.value = "No logeado"
            _creationDate.value = null
        }
        Log.d("ProfileViewModel", "Current user email: ${_currentUser.value?.email}")
    }

    fun logout() {
        viewModelScope.launch {
            try {
                auth.signOut()
                _currentUser.value = null
                _username.value = null // Limpiar el nombre de usuario también al cerrar sesión
                _creationDate.value = null
                _isLoggedOut.value = true
                Log.d("ProfileViewModel", "User logged out successfully.")
            } catch (e: Exception) {
                Log.e("ProfileViewModel", "Error logging out: ${e.message}", e)
            }
        }
    }

    fun resetLogoutState() {
        _isLoggedOut.value = false
    }
}