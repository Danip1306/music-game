package com.example.musicgame.ui.view.home

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.musicgame.R
import com.example.musicgame.ui.theme.MusicGameTheme

@Composable
fun HomeScreen(
    // Eliminamos onNavigateToLevels porque ya no lo usaremos desde aquí
    onNavigateToTests: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(R.string.home_welcome_title),
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Text(
            text = stringResource(R.string.home_welcome_subtitle),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        // Mantenemos solo el botón de Tests
        Button(
            onClick = onNavigateToTests,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .padding(horizontal = 24.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary, // Usamos el color primario para destacarlo
                contentColor = MaterialTheme.colorScheme.onPrimary
            )
        ) {
            Text(stringResource(R.string.home_button_tests))
        }

    }
}

@Preview(showBackground = true)
@Composable
fun PreviewHomeScreen() {
    MusicGameTheme {
        HomeScreen(
            onNavigateToTests = { /* Do nothing for preview */ }
        )
    }
}