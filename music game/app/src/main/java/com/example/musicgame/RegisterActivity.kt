package com.example.musicgame

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val editTextRegisterUsername = findViewById<EditText>(R.id.editTextRegisterUsername)
        val editTextRegisterEmail = findViewById<EditText>(R.id.editTextRegisterEmail)
        val editTextRegisterPassword = findViewById<EditText>(R.id.editTextRegisterPassword)
        val editTextConfirmPassword = findViewById<EditText>(R.id.editTextConfirmPassword)
        val buttonCreateAccount = findViewById<Button>(R.id.buttonCreateAccount)
        val buttonBackToLogin = findViewById<Button>(R.id.buttonBackToLogin)

        buttonCreateAccount.setOnClickListener {
            val username = editTextRegisterUsername.text.toString().trim()
            val email = editTextRegisterEmail.text.toString().trim()
            val password = editTextRegisterPassword.text.toString().trim()
            val confirmPassword = editTextConfirmPassword.text.toString().trim()

            if (username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(this, "Por favor, completa todos los campos.", Toast.LENGTH_SHORT).show()
            } else if (password != confirmPassword) {
                Toast.makeText(this, "Las contraseñas no coinciden.", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Cuenta creada exitosamente para $username.", Toast.LENGTH_SHORT).show()
                // navegar de vuelta al login o directamente a la pantalla principal, si, login = exitoso
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

        buttonBackToLogin.setOnClickListener {
            // Simplemente cierra esta actividad para volver a la anterior (LoginActivity)
            finish()
        }
    }
}