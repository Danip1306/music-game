package com.example.musicgame

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.musicgame.ui.theme.MusicGameTheme

class LevelDetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Obtener el número de nivel pasado desde LevelsActivity
        val levelNumber = intent.getIntExtra("levelNumber", 0) //0 = default valor

        setContent {
            MusicGameTheme {
                LevelDetailScreen(levelNumber = levelNumber)
            }
        }
    }
}

@Composable
fun LevelDetailScreen(levelNumber: Int) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "¡Ingreso al Nivel $levelNumber!",
            fontSize = 32.sp,
            fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface, // Se adapta al tema
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Text(
            text = "Aquí es donde comenzarán los desafíos del Nivel $levelNumber.",
            fontSize = 18.sp,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LevelDetailScreenPreview() {
    MusicGameTheme {
        LevelDetailScreen(levelNumber = 1) //solo para la pre visualizacion
    }
}