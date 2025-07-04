package com.example.musicgame.ui.view.leveldetail

import android.content.Intent
import android.os.Bundle
import android.util.Log // Importación necesaria para Log.d
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.musicgame.ui.theme.MusicGameTheme
import com.example.musicgame.ui.view.test.TestActivity // Asegúrate de que esta importación sea correcta

class LevelDetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Obtener el número de nivel pasado desde LevelsActivity
        val levelNumber = intent.getIntExtra("levelNumber", 0) // 0 = valor por defecto
        Log.d("LevelDetailActivity", "Received levelNumber: $levelNumber") // <--- ¡ESTA LÍNEA ES CLAVE!

        setContent {
            MusicGameTheme {
                LevelDetailScreen(levelNumber = levelNumber)
            }
        }
    }
}

@Composable
fun LevelDetailScreen(levelNumber: Int) {
    val context = LocalContext.current

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
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Text(
            text = "Aquí es donde comenzarán los desafíos del Nivel $levelNumber.",
            fontSize = 18.sp,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        Button(
            onClick = {
                val intent = Intent(context, TestActivity::class.java).apply {
                    // Pasamos el levelNumber (Int) como String con la clave "levelId"
                    putExtra("levelId", levelNumber.toString())
                }
                context.startActivity(intent)
            },
            modifier = Modifier
                .padding(top = 16.dp)
                .height(56.dp)
                .fillMaxSize(0.7f)
        ) {
            Text(text = "Empezar Test del Nivel $levelNumber")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LevelDetailScreenPreview() {
    MusicGameTheme {
        LevelDetailScreen(levelNumber = 1)
    }
}