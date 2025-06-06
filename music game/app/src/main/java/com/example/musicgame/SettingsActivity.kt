package com.example.musicgame

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.musicgame.ui.theme.MusicGameTheme

class SettingsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MusicGameTheme {
                // el SettingsScreen será llamado desde DashboardActivity.
                SettingsScreen()
            }
        }
    }
}


@Composable
fun SettingsScreen() {
    val context = LocalContext.current

    val soundEnabled = remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            text = "Configuración",
            fontSize = 32.sp,
            fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Sonido del juego",
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.onSurface
            )
            Switch(
                checked = soundEnabled.value,
                onCheckedChange = { isChecked ->
                    soundEnabled.value = isChecked
                    Toast.makeText(context, "Sonido: ${if (isChecked) "Activado" else "Desactivado"}", Toast.LENGTH_SHORT).show()
                }
            )
        }

        Divider(modifier = Modifier.padding(vertical = 16.dp))

        Button(
            onClick = {
                Toast.makeText(context, "Progreso restablecido (simulado)", Toast.LENGTH_SHORT).show()
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text(
                text = "Restablecer Progreso",
                fontSize = 18.sp
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SettingsScreenPreview() {
    MusicGameTheme {
        SettingsScreen()
    }
}