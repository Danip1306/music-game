package com.example.musicgame.ui.view.levels

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.musicgame.ui.view.help.HelpActivity
import com.example.musicgame.ui.view.leveldetail.LevelDetailActivity
import com.example.musicgame.ui.theme.MusicGameTheme
import com.example.musicgame.ui.viewmodel.LevelsViewModel
import com.example.musicgame.data.model.Level // ¡IMPORTACIÓN NECESARIA!

class LevelsActivity : ComponentActivity() {
    // La Activity sigue siendo el punto de entrada para la pantalla
    // pero delega la lógica al ViewModel
    private val levelsViewModel: LevelsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MusicGameTheme {
                // Pasamos la función de navegación como un lambda
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LevelsScreen(
    onLevelClick: (Int) -> Unit,
    levelsViewModel: LevelsViewModel = viewModel()
) {
    // La vista observa el estado del ViewModel
    val levels by levelsViewModel.levels.collectAsState() // Ahora 'levels' es List<Level>
    val context = LocalContext.current
    var expanded by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Niveles", color = MaterialTheme.colorScheme.onPrimary,
                    fontSize = 20.sp) }, // Este string irá a strings.xml
                actions = {
                    IconButton(onClick = { expanded = true }) {
                        Icon(Icons.Filled.MoreVert, contentDescription = "Menú de opciones", tint = MaterialTheme.colorScheme.onPrimary) // Este string irá a strings.xml
                    }
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        // Aquí eliminamos la opción "Progreso y Estadísticas"
                        // Solo dejamos "Ayuda / Tutorial"

                        // Ayuda / Tutorial
                        DropdownMenuItem(
                            text = { Text("Ayuda / Tutorial") }, // Este string irá a strings.xml
                            onClick = {
                                expanded = false
                                val intent = Intent(context, HelpActivity::class.java)
                                context.startActivity(intent)
                                Toast.makeText(context, "Abriendo Ayuda...", Toast.LENGTH_SHORT).show() // Este string irá a strings.xml
                            }
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    actionIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(24.dp))

                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Ahora iteramos sobre objetos Level, no Int
                    items(levels, key = { it.id }) { level -> // Usa el ID de Firebase como clave
                        LevelItem(
                            levelNumber = level.number, // Pasamos el número del Level
                            levelTitle = level.title, // Pasamos el título del Level
                            onLevelClick = {
                                // Pasamos el número del nivel al lambda onLevelClick
                                onLevelClick(level.number)
                                levelsViewModel.onLevelClick(level.number) // Opcional: Notificar al ViewModel del evento
                            }
                        )
                    }
                }
            }
        }
    )
}

@Composable
fun LevelItem(levelNumber: Int, levelTitle: String, onLevelClick: () -> Unit) { // ¡NUEVO PARÁMETRO levelTitle!
    Card(
        modifier = Modifier
            .fillMaxWidth(0.8f)
            .height(80.dp)
            .clickable { onLevelClick() }
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "Nivel $levelNumber", // Este string irá a strings.xml
                    fontSize = 24.sp,
                    fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = levelTitle, // Mostramos el título del nivel
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
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