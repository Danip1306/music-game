package com.example.musicgame

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.musicgame.ui.theme.MusicGameTheme

class DashboardActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MusicGameTheme {
                DashboardScreen()
            }
        }
    }
}

//pantallas para el nav
sealed class Screen(val route: String, val label: String, val icon: @Composable () -> Unit) {
    data object Levels : Screen("levels", "Niveles", { Icon(Icons.Filled.List, contentDescription = null) })
    data object Settings : Screen("settings", "Ajustes", { Icon(Icons.Filled.Settings, contentDescription = null) })
    data object Language : Screen("language", "Idioma", { Icon(Icons.Filled.Language, contentDescription = null) })
    data object Profile : Screen("profile", "Perfil", { Icon(Icons.Filled.Person, contentDescription = null) })
    data object About : Screen("about", "Acerca de", { Icon(Icons.Filled.Info, contentDescription = null) })
}

// Lista de elementos navegación inferior
val items = listOf(
    Screen.Levels,
    Screen.Settings,
    Screen.Language,
    Screen.Profile,
    Screen.About
)

@Composable
fun DashboardScreen() {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            NavigationBar {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination

                items.forEach { screen ->
                    NavigationBarItem(
                        icon = screen.icon,
                        label = { Text(screen.label) },
                        selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                        onClick = {
                            navController.navigate(screen.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
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
            startDestination = Screen.Levels.route,
            modifier = Modifier.padding(innerPadding)
        ) {

            composable(Screen.Levels.route) {
                LevelsScreen(onLevelClick = { levelNumber ->

                })
            }
            composable(Screen.Settings.route) {
                SettingsScreen()
            }
            composable(Screen.Language.route) {
                LanguageScreen() // pantalla lenguaje
            }
            composable(Screen.Profile.route) {
                ProfileScreen() // pantalla Perfil
            }
            composable(Screen.About.route) {
                AboutScreen() // pantalla Acerca de
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DashboardScreenPreview() {
    MusicGameTheme {
        DashboardScreen()
    }
}