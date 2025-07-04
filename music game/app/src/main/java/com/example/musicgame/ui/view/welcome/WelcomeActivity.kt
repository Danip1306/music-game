package com.example.musicgame.ui.view.welcome

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.musicgame.R
import com.example.musicgame.ui.theme.MusicGameTheme
// ⚠️ IMPORTANTE: Asegúrate de tener esta importación
import com.example.musicgame.ui.theme.* // Esto importa WelcomeGradientColorsLight y Dark
import com.example.musicgame.ui.view.dashboard.DashboardActivity
import com.example.musicgame.ui.view.test.TestActivity

class WelcomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MusicGameTheme {
                WelcomeScreen()
            }
        }
    }
}

@Composable
fun WelcomeScreen() {
    val context = LocalContext.current
    val isDarkTheme = isSystemInDarkTheme()

    var showContent by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        showContent = true
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = if (isDarkTheme) WelcomeGradientColorsDark else WelcomeGradientColorsLight
                )
            )
    ) {
        AnimatedVisibility(
            visible = showContent,
            enter = fadeIn(animationSpec = tween(durationMillis = 800)) +
                    slideInVertically(initialOffsetY = { it / 4 }, animationSpec = tween(durationMillis = 800)),
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 32.dp, vertical = 48.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // Título de Bienvenida
                Text(
                    text = stringResource(R.string.welcome_title),
                    style = MaterialTheme.typography.displaySmall.copy(
                        color = MaterialTheme.colorScheme.onBackground,
                        fontWeight = MaterialTheme.typography.displaySmall.fontWeight
                    ),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                // Subtítulo de elección
                Text(
                    text = stringResource(R.string.welcome_subtitle),
                    style = MaterialTheme.typography.headlineSmall.copy(
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.8f)
                    ),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 48.dp)
                )

                // Botón para Explorar Niveles
                Button(
                    onClick = {
                        val intent = Intent(context, DashboardActivity::class.java)
                        context.startActivity(intent)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                        .padding(vertical = 8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    ),
                    shape = RoundedCornerShape(16.dp),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 8.dp)
                ) {
                    Text(text = stringResource(R.string.welcome_explore_levels_button), style = MaterialTheme.typography.titleLarge)
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Botón para Tomar Test de Música
                Button(
                    onClick = {
                        val intent = Intent(context, TestActivity::class.java)
                        context.startActivity(intent)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                        .padding(vertical = 8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondary,
                        contentColor = MaterialTheme.colorScheme.onSecondary
                    ),
                    shape = RoundedCornerShape(16.dp),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 8.dp)
                ) {
                    Text(text = stringResource(R.string.welcome_take_test_button), style = MaterialTheme.typography.titleLarge)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewWelcomeScreen() {
    MusicGameTheme {
        WelcomeScreen()
    }
}