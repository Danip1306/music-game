package com.example.musicgame

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.musicgame.ui.theme.MusicGameTheme
import androidx.compose.ui.platform.LocalContext // ¡Necesario para startActivity en Composable!

class LevelsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MusicGameTheme {
                // Este LevelActivity ya no será el punto de entrada principal para los niveles,
                // sino que el composable LevelsScreen será llamado desde DashboardActivity.
                // Sin embargo, si aún necesitas esta Activity por alguna razón,
                // puedes dejarla y llamará a LevelsScreen, pero el usuario llegará a LevelsScreen
                // principalmente a través de DashboardActivity.
                LevelsScreen(onLevelClick = { levelNumber ->
                    val intent = Intent(this, LevelDetailActivity::class.java).apply {
                        putExtra("levelNumber", levelNumber)
                    }
                    startActivity(intent)
                })
            }
        }
    }
}

// Mueve esta función Composable fuera de la clase LevelsActivity
// para que pueda ser llamada directamente por NavHost.
@Composable
fun LevelsScreen(onLevelClick: (Int) -> Unit) {
    val levels = (1..10).toList()

    // El contexto lo obtenemos aquí para poder lanzar intents
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = "Selecciona un Nivel",
            fontSize = 28.sp,
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(levels) { level ->
                LevelItem(levelNumber = level, onLevelClick = {
                    // Aquí es donde realmente se lanza la LevelDetailActivity
                    val intent = Intent(context, LevelDetailActivity::class.java).apply {
                        putExtra("levelNumber", level)
                    }
                    context.startActivity(intent)
                })
            }
        }
    }
}

@Composable
fun LevelItem(levelNumber: Int, onLevelClick: () -> Unit) { // onLevelClick ahora no necesita Int
    Card(
        modifier = Modifier
            .fillMaxWidth(0.8f)
            .height(80.dp)
            .clickable { onLevelClick() } // Llama al onLevelClick genérico
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Nivel $levelNumber",
                fontSize = 24.sp,
                fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LevelsScreenPreview() {
    MusicGameTheme {
        LevelsScreen(onLevelClick = {})
    }
}