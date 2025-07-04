package com.example.musicgame.ui.view.dashboard

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
// Elimina la importación de Home si ya no se usa, pero la mantengo si se utiliza en algún otro lado del proyecto
// import androidx.compose.material.icons.filled.Home // Importación necesaria para el icono de Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.os.LocaleListCompat
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.musicgame.R
import com.example.musicgame.data.preferences.AppSettingsDataStore
import com.example.musicgame.notifications.MyFirebaseMessagingService
import com.example.musicgame.ui.theme.MusicGameTheme
import com.example.musicgame.ui.view.about.AboutScreen
import com.example.musicgame.ui.view.language.LanguageScreen
import com.example.musicgame.ui.view.levels.LevelsScreen
import com.example.musicgame.ui.view.leveldetail.LevelDetailActivity
import com.example.musicgame.ui.view.profile.ProfileScreen
import com.example.musicgame.ui.view.settings.SettingsScreen
// Elimina la importación de HomeScreen ya que no se usará
// import com.example.musicgame.ui.view.home.HomeScreen // Importación de tu nueva HomeScreen
import com.example.musicgame.ui.view.test.TestActivity // Importación de TestActivity
import com.example.musicgame.ui.viewmodel.DashboardViewModel
import com.example.musicgame.ui.viewmodel.DashboardNavigationEvent
import com.example.musicgame.notifications.NotificationHelper
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.flow.collectLatest

class DashboardActivity : ComponentActivity() {
    private val dashboardViewModel: DashboardViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        NotificationHelper.createNotificationChannel(this)

        // Opcional: Obtener y registrar el token FCM (para depuración)
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w("FCM_Token", "Fetching FCM registration token failed", task.exception)
                return@addOnCompleteListener
            }
            val token = task.result
            Log.d("FCM_Token", "FCM Registration Token: $token")
        }

        setContent {
            val context = LocalContext.current
            val appSettingsDataStore = AppSettingsDataStore(context)
            val isDarkMode by appSettingsDataStore.isDarkMode.collectAsState(initial = false)
            val appLanguage by appSettingsDataStore.appLanguage.collectAsState(initial = "system")

            LaunchedEffect(appLanguage) {
                val currentLocales = AppCompatDelegate.getApplicationLocales()
                val targetLocale = if (appLanguage == "system") {
                    LocaleListCompat.getEmptyLocaleList()
                } else {
                    LocaleListCompat.forLanguageTags(appLanguage)
                }

                // Solo cambia si las locales actuales son diferentes a las deseadas
                // Esto ayuda a evitar recargas innecesarias
                if (currentLocales.toLanguageTags() != targetLocale.toLanguageTags()) {
                    AppCompatDelegate.setApplicationLocales(targetLocale)
                    Log.d("LanguageSwitch", "Applying new language: $appLanguage")
                } else {
                    Log.d("LanguageSwitch", "Language $appLanguage already applied or is system default.")
                }
            }

            MusicGameTheme(darkTheme = isDarkMode) {
                DashboardScreen(dashboardViewModel = dashboardViewModel)
            }
        }
    }
}

// Pantallas para el nav
sealed class Screen(val route: String, @StringRes val labelResId: Int, val icon: @Composable () -> Unit) {
    // ELIMINADO: Definición de la pantalla de inicio
    // data object Home : Screen("home", R.string.nav_home, { Icon(Icons.Filled.Home, contentDescription = stringResource(R.string.content_description_home_icon)) })
    // Corregido: Usando R.string.nav_levels para el texto de la barra de navegación
    data object Levels : Screen("levels", R.string.nav_levels, { Icon(Icons.Filled.List, contentDescription = stringResource(R.string.content_description_levels_icon)) })
    // Corregido: Usando R.string.nav_settings para el texto de la barra de navegación
    data object Settings : Screen("settings", R.string.nav_settings, { Icon(Icons.Filled.Settings, contentDescription = stringResource(R.string.content_description_settings_icon)) })
    // Corregido: Usando R.string.nav_language para el texto de la barra de navegación
    data object Language : Screen("language", R.string.nav_language, { Icon(Icons.Filled.Language, contentDescription = stringResource(R.string.content_description_language_icon)) })
    // Corregido: Usando R.string.nav_profile para el texto de la barra de navegación
    data object Profile : Screen("profile", R.string.nav_profile, { Icon(Icons.Filled.Person, contentDescription = stringResource(R.string.content_description_profile_icon)) })
    // Corregido: Usando R.string.nav_about para el texto de la barra de navegación
    data object About : Screen("about", R.string.nav_about, { Icon(Icons.Filled.Info, contentDescription = stringResource(R.string.content_description_about_icon)) })
}

// Lista de elementos navegación inferior
val items = listOf(
    // ELIMINADO: Screen.Home de la barra de navegación inferior
    Screen.Levels,
    Screen.Settings,
    Screen.Language,
    Screen.Profile,
    Screen.About
)

@Composable
fun DashboardScreen(dashboardViewModel: DashboardViewModel) { // Ahora recibe el ViewModel
    val navController = rememberNavController()
    val context = LocalContext.current

    // Observar los eventos de navegación del ViewModel
    LaunchedEffect(Unit) {
        dashboardViewModel.navigationEvents.collectLatest { event ->
            when (event) {
                // Eliminado: ya no navegamos directamente a Levels desde aquí si el Home solo tiene Test
                // DashboardNavigationEvent.NavigateToLevels -> navController.navigate(Screen.Levels.route)
                DashboardNavigationEvent.NavigateToTests -> {
                    // Acción para lanzar TestActivity
                    val intent = Intent(context, TestActivity::class.java)
                    context.startActivity(intent)
                }
            }
        }
    }

    Scaffold(
        bottomBar = {
            NavigationBar {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination

                items.forEach { screen ->
                    NavigationBarItem(
                        icon = screen.icon,
                        label = { Text(stringResource(id = screen.labelResId)) },
                        selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                        onClick = {
                            navController.navigate(screen.route) {
                                // Pop up to the start destination of the graph to avoid building up a large
                                // back stack on the back button and to reset state
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                // Avoid multiple copies of the same destination when reselecting the same item
                                launchSingleTop = true
                                // Restore state when reselecting a previously selected item
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Levels.route, // CAMBIADO: La aplicación ahora inicia en la pantalla de Niveles
            modifier = Modifier.padding(innerPadding)
        ) {
            // ELIMINADO: La ruta y el contenido de la HomeScreen
            /*
            composable(Screen.Home.route) {
                // Pasa la función para navegar a Tests desde HomeScreen
                HomeScreen(
                    onNavigateToTests = { dashboardViewModel.onNavigateToTestsClicked() }
                )
            }
            */
            composable(Screen.Levels.route) {
                LevelsScreen(onLevelClick = { levelNumber ->
                    val intent = Intent(context, LevelDetailActivity::class.java).apply {
                        putExtra("levelNumber", levelNumber)
                    }
                    context.startActivity(intent)
                })
            }
            composable(Screen.Settings.route) { SettingsScreen() }
            composable(Screen.Language.route) { LanguageScreen() }
            composable(Screen.Profile.route) { ProfileScreen() }
            composable(Screen.About.route) { AboutScreen() }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DashboardScreenPreview() {
    MusicGameTheme {
        // En el preview, puedes pasar un ViewModel dummy o mock si es necesario.
        DashboardScreen(dashboardViewModel = DashboardViewModel())
    }
}