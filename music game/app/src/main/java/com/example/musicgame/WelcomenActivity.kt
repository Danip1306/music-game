package com.example.musicgame

import android.content.Intent
import android.widget.Toast
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.musicgame.ui.theme.MusicGameTheme
import com.example.musicgame.ui.theme.MusicGameColors

class WelcomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MusicGameTheme {
                WelcomeScreen(
                    onTakeTestClick = {
                        Toast.makeText(this, "Navegando a Test", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this, TestActivity::class.java))
                    },
                    onStartFromZeroClick = {
                        Toast.makeText(this, "Navegando a Niveles", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this, DashboardActivity::class.java))
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
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        MusicGameColors.Purple,
                        MusicGameColors.Blue,
                        MusicGameColors.Green
                    )
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Título principal
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 32.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MusicGameColors.White.copy(alpha = 0.9f)
                ),
                shape = RoundedCornerShape(20.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "🎵 Tonika 🎵",
                        style = MaterialTheme.typography.displayMedium,
                        color = MusicGameColors.Purple,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Text(
                        text = "¡Hola, [Nombre de Usuario]!",
                        style = MaterialTheme.typography.headlineMedium,
                        color = MusicGameColors.Black,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Text(
                        text = "¿Listo para comenzar tu aventura musical?",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MusicGameColors.Gray800,
                        textAlign = TextAlign.Center
                    )
                }
            }

            // Botones principales
            Button(
                onClick = onTakeTestClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp)
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MusicGameColors.Orange,
                    contentColor = MusicGameColors.White
                ),
                shape = RoundedCornerShape(16.dp),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 6.dp)
            ) {
                Text(
                    text = "🧠 Realizar Test de Conocimientos",
                    style = MaterialTheme.typography.labelLarge
                )
            }

            Button(
                onClick = onStartFromZeroClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp)
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MusicGameColors.Red,
                    contentColor = MusicGameColors.White
                ),
                shape = RoundedCornerShape(16.dp),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 6.dp)
            ) {
                Text(
                    text = "🎼 Empezar desde Nivel Cero",
                    style = MaterialTheme.typography.labelLarge
                )
            }

            // Tarjeta informativa
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MusicGameColors.White.copy(alpha = 0.8f)
                ),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Text(
                    text = "💡 Tip: Si eres principiante, te recomendamos empezar desde el nivel cero. Si ya tienes conocimientos musicales, ¡haz el test para encontrar tu nivel ideal!",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MusicGameColors.Gray800,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(16.dp)
                )
            }
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