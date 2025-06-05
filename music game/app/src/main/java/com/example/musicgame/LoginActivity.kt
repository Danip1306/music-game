package com.example.musicgame

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {

    private val DEFAULT_USERNAME = "prueba"
    private val DEFAULT_EMAIL = "prueba@ejemplo.com" // Opcional, si tu login acepta email
    private val DEFAULT_PASSWORD = "prueba123"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val editTextUsername = findViewById<EditText>(R.id.editTextUsername)
        val editTextPassword = findViewById<EditText>(R.id.editTextPassword)
        val buttonLogin = findViewById<Button>(R.id.buttonLogin)
        val buttonRegister = findViewById<Button>(R.id.buttonRegister)

        editTextUsername.setText(DEFAULT_USERNAME)
        editTextPassword.setText(DEFAULT_PASSWORD)

        buttonLogin.setOnClickListener {
            val username = editTextUsername.text.toString().trim()
            val password = editTextPassword.text.toString().trim()

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Por favor, ingresa usuario y contraseña.", Toast.LENGTH_SHORT).show()
            } else {
                // Aquí iría tu lógica de autenticación real
                // Por ahora, solo un Toast de ejemplo
                if ((username == DEFAULT_USERNAME || username == DEFAULT_EMAIL) && password == DEFAULT_PASSWORD) {
                    Toast.makeText(this, "Inicio de sesión exitoso. ¡Bienvenido!", Toast.LENGTH_SHORT).show()
                    // Si el login es exitoso, navega a la siguiente pantalla (por ejemplo, HomeActivity)
                    val intent = Intent(this, HomeActivity::class.java) // Crea HomeActivity más adelante
                    startActivity(intent)
                    finish() // Para que el usuario no pueda volver al login con el botón atrás
                } else {
                    Toast.makeText(this, "Usuario o contraseña incorrectos.", Toast.LENGTH_SHORT).show()
                }
            }
        }

        buttonRegister.setOnClickListener {
            // Navegar a la pantalla de registro
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}

