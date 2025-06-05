package com.example.musicgame

import android.content.Intent
import android.widget.Toast
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.musicgame.ui.theme.MusicGameTheme

class WelcomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MusicGameTheme {
                WelcomeScreen(
                    onTakeTestClick = {
                        // Test de Conocimientos
                        Toast.makeText(this, "Navegando a Test", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this, TestActivity::class.java))
                    },
                    onStartFromZeroClick = {
                        // Niveles
                        Toast.makeText(this, "Navegando a Niveles", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this, LevelsActivity::class.java))
                    }
                )
            }
        }
    }
}

@Composable
fun WelcomeScreen(
    onTakeTestClick: () -> Unit,
    onStartFromZeroClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "¡Hola, [Nombre de Usuario]!",
            fontSize = 28.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Text(
            text = "¿Listo para comenzar tu aventura musical?",
            fontSize = 18.sp,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        Button(
            onClick = onTakeTestClick,
            modifier = Modifier.fillMaxSize(0.8f)
        ) {
            Text("Realizar Test de Conocimientos")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onStartFromZeroClick,
            modifier = Modifier.fillMaxSize(0.8f)
        ) {
            Text("Empezar desde Nivel Cero")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WelcomeScreenPreview() {
    MusicGameTheme {
        WelcomeScreen(
            onTakeTestClick = {},
            onStartFromZeroClick = {}
        )
    }
}