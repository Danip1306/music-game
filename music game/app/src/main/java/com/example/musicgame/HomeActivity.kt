package com.example.musicgame

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        // WelcomeActivity
        navigateToWelcomeScreen()
    }

    private fun navigateToWelcomeScreen() {
        val intent = Intent(this, WelcomeActivity::class.java)
        startActivity(intent)
        // Opcional: Finaliza HomeActivity para que el usuario no pueda volver a ella con el botón "Atrás"
        finish()
    }

}