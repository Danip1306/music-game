package com.example.musicgame

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.platform.LocalContext
import com.example.musicgame.ui.theme.MusicGameTheme

class LevelsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MusicGameTheme {
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
fun LevelsScreen(onLevelClick: (Int) -> Unit) {
    val levels = (1..10).toList()
    val context = LocalContext.current
    var expanded by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Niveles", color = MaterialTheme.colorScheme.onPrimary,
                        fontSize = 20.sp) },
                actions = {
                    IconButton(onClick = { expanded = true }) {
                        Icon(Icons.Filled.MoreVert, contentDescription = "Menú de opciones", tint = MaterialTheme.colorScheme.onPrimary)
                    }
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        // Progreso y Estadísticas
                        DropdownMenuItem(
                            text = { Text("Progreso y Estadísticas") },
                            onClick = {
                                expanded = false
                                val intent = Intent(context, ProgressActivity::class.java)
                                context.startActivity(intent)
                                Toast.makeText(context, "Abriendo Progreso...", Toast.LENGTH_SHORT).show()
                            }
                        )
                        // Ayuda / Tutorial
                        DropdownMenuItem(
                            text = { Text("Ayuda / Tutorial") },
                            onClick = {
                                expanded = false
                                val intent = Intent(context, HelpActivity::class.java)
                                context.startActivity(intent)
                                Toast.makeText(context, "Abriendo Ayuda...", Toast.LENGTH_SHORT).show()
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
                    items(levels) { level ->
                        LevelItem(levelNumber = level, onLevelClick = {
                            val intent = Intent(context, LevelDetailActivity::class.java).apply {
                                putExtra("levelNumber", level)
                            }
                            context.startActivity(intent)
                        })
                    }
                }
            }
        }
    )
}

@Composable
fun LevelItem(levelNumber: Int, onLevelClick: () -> Unit) {
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