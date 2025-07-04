package com.example.musicgame.ui.view.language

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.musicgame.R
import com.example.musicgame.ui.theme.MusicGameTheme
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LanguageScreen() {
    val context = LocalContext.current

    val currentSystemLocale = remember {
        Locale.getDefault().language
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.language_title)) }, // Correct string ID
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            Text(
                text = stringResource(R.string.select_language), // Correct string ID
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Option: System Default
            LanguageOption(
                languageName = stringResource(R.string.system_default), // Correct string ID
                languageCode = "default",
                isSelected = true // Or logic to determine if it's the actual system default
            ) { /* No action on click for system default */ }

            // Option: English
            LanguageOption(
                languageName = stringResource(R.string.language_english), // Correct string ID
                languageCode = "en",
                isSelected = currentSystemLocale == "en"
            ) { /* No action on click for system default */ }

            // Option: Spanish
            LanguageOption(
                languageName = stringResource(R.string.language_spanish), // Correct string ID
                languageCode = "es",
                isSelected = currentSystemLocale == "es"
            ) { /* No action on click for system default */ }
        }
    }
}

@Composable
fun LanguageOption(
    languageName: String,
    languageCode: String,
    isSelected: Boolean,
    onSelect: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onSelect(languageCode) } // Allow selection if needed
            .padding(vertical = 12.dp, horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = languageName, style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.onSurface)
        if (isSelected) {
            Icon(Icons.Default.Check, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LanguageScreenPreview() {
    MusicGameTheme {
        LanguageScreen()
    }
}