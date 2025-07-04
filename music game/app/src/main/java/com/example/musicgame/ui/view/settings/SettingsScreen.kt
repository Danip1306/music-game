package com.example.musicgame.ui.view.settings

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import kotlinx.coroutines.launch
import com.example.musicgame.data.preferences.AppSettingsDataStore
import com.example.musicgame.notifications.NotificationHelper// --- NUEVA IMPORTACIÓN ---

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen() {
    val context = LocalContext.current
    val appSettingsDataStore = AppSettingsDataStore(context)
    val isDarkMode by appSettingsDataStore.isDarkMode.collectAsState(initial = false)
    val areSoundEffectsEnabled by appSettingsDataStore.areSoundEffectsEnabled.collectAsState(initial = true)
    // --- NUEVA VARIABLE PARA NOTIFICACIONES ---
    val areNotificationsEnabled by appSettingsDataStore.areNotificationsEnabled.collectAsState(initial = true)
    val coroutineScope = rememberCoroutineScope()

    // --- LÓGICA DE VIBRACIÓN ---
    val vibratorService = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
    } else {
        @Suppress("DEPRECATION")
        context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    }

    val performHapticFeedback: () -> Unit = {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                (vibratorService as VibratorManager).defaultVibrator.vibrate(VibrationEffect.createOneShot(50, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                @Suppress("DEPRECATION")
                (vibratorService as Vibrator).vibrate(VibrationEffect.createOneShot(50, VibrationEffect.DEFAULT_AMPLITUDE))
            }
        } else {
            @Suppress("DEPRECATION")
            (vibratorService as Vibrator).vibrate(50)
        }
    }
    // --- FIN LÓGICA DE VIBRACIÓN ---

    // --- LANZADOR DE PERMISOS PARA NOTIFICACIONES (Android 13+) ---
    val requestPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            // Permiso concedido, puedes actualizar la preferencia
            coroutineScope.launch {
                appSettingsDataStore.saveNotificationsPreference(true)
            }
            // Opcional: mostrar una notificación de prueba para confirmar
            NotificationHelper.showTestNotification(
                context,
                "Notificaciones activadas",
                "¡Has activado las notificaciones del juego!"
            )
        } else {
            // Permiso denegado, asegúrate de que el switch refleje esto
            coroutineScope.launch {
                appSettingsDataStore.saveNotificationsPreference(false)
            }
            // Opcional: Mostrar un mensaje al usuario sobre la denegación del permiso
        }
    }


    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Ajustes") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary, // Usa el color primario de tu tema
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top
        ) {
            // Opción para cambiar el tema
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Tema Oscuro",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Switch(
                    checked = isDarkMode,
                    onCheckedChange = { isChecked ->
                        coroutineScope.launch {
                            appSettingsDataStore.saveDarkModePreference(isChecked)
                        }
                        performHapticFeedback()
                    }
                )
            }

            // Opción para efectos de sonido
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Efectos de Sonido",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Switch(
                    checked = areSoundEffectsEnabled,
                    onCheckedChange = { isChecked ->
                        coroutineScope.launch {
                            appSettingsDataStore.saveSoundEffectsPreference(isChecked)
                        }
                        performHapticFeedback()
                    }
                )
            }

            // --- NUEVA OPCIÓN PARA NOTIFICACIONES ---
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Notificaciones",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Switch(
                    checked = areNotificationsEnabled,
                    onCheckedChange = { isChecked ->
                        if (isChecked) {
                            // Intentando activar notificaciones
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                                // Para Android 13 (Tiramisu) y superior, pedir permiso en tiempo de ejecución
                                if (ContextCompat.checkSelfPermission(
                                        context,
                                        Manifest.permission.POST_NOTIFICATIONS
                                    ) == PackageManager.PERMISSION_GRANTED
                                ) {
                                    // Permiso ya concedido
                                    coroutineScope.launch {
                                        appSettingsDataStore.saveNotificationsPreference(true)
                                    }
                                    NotificationHelper.showTestNotification(
                                        context,
                                        "Notificaciones activadas",
                                        "¡Has activado las notificaciones del juego!"
                                    )
                                } else {
                                    // Pedir permiso
                                    requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                                }
                            } else {
                                // Para versiones anteriores a Android 13, el permiso se concede automáticamente
                                coroutineScope.launch {
                                    appSettingsDataStore.saveNotificationsPreference(true)
                                }
                                NotificationHelper.showTestNotification(
                                    context,
                                    "Notificaciones activadas",
                                    "¡Has activado las notificaciones del juego!"
                                )
                            }
                        } else {
                            // Desactivando notificaciones
                            coroutineScope.launch {
                                appSettingsDataStore.saveNotificationsPreference(false)
                            }
                            // No se muestra ninguna notificación aquí
                        }
                        performHapticFeedback() // Vibrar al cambiar la preferencia
                    }
                )
            }


            Text(
                text = "Más opciones de ajuste...",
                modifier = Modifier.padding(top = 16.dp),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}