package com.example.musicgame.ui.theme

import androidx.compose.ui.graphics.Color

// Nueva Paleta de Colores basada en tus especificaciones
val CustomPrimary = Color(0xFF5E35B1) // Morado oscuro
val CustomSecondary = Color(0xFF3949AB) // Azul oscuro
val CustomAccent = Color(0xFFFFD600) // Amarillo vibrante
val CustomLight = Color(0xFFEDE7F6) // Morado muy claro, casi blanco
val CustomDark = Color(0xFF1A237E) // Azul/Morado muy oscuro

// Colores "On" para contrastar con los de la paleta principal
val OnPrimary = Color(0xFFFFFFFF) // Texto blanco sobre CustomPrimary
val OnSecondary = Color(0xFFFFFFFF) // Texto blanco sobre CustomSecondary
val OnAccent = Color(0xFF000000) // Texto oscuro sobre CustomAccent

// Colores Neutros/Funcionales (adaptados a la nueva paleta o genéricos)
val White = Color(0xFFFFFFFF)
val Black = Color(0xFF000000)
val Gray = Color(0xFFBDBDBD) // Un gris para textos secundarios o enlaces como "registrarse"
val DarkGray = Color(0xFF424242) // Para textos o elementos más oscuros en contraste

// Colores de error (Material 3 por defecto)
val ErrorColor = Color(0xFFB00020)
val OnError = Color(0xFFFFFFFF)

// ⚠️ COLOR ESPECÍFICO PARA EL FONDO DE LOGIN/REGISTER
val LoginBackgroundPurple = Color(0xFFBA68C8) // El color exacto que especificaste

// Definiciones para MaterialTheme ColorScheme (usando los nuevos colores)
// Light Color Scheme
val LightPrimary = CustomPrimary
val LightOnPrimary = OnPrimary
val LightPrimaryContainer = CustomLight // Un color más claro para contenedores si se necesita
val LightOnPrimaryContainer = CustomDark

val LightSecondary = CustomSecondary
val LightOnSecondary = OnSecondary
val LightSecondaryContainer = CustomAccent // Podría ser el acento para contenedores secundarios
val LightOnSecondaryContainer = OnAccent

val LightTertiary = CustomAccent
val LightOnTertiary = OnAccent
val LightTertiaryContainer = CustomAccent.copy(alpha = 0.2f)
val LightOnTertiaryContainer = OnAccent

val LightBackground = CustomLight // El morado muy claro para el fondo principal
val LightOnBackground = CustomDark // Texto oscuro sobre el fondo claro

val LightSurface = White // Fondo para tarjetas, campos de texto, etc.
val LightOnSurface = Black // Texto sobre superficies blancas
val LightSurfaceVariant = Gray // Variantes de superficie
val LightOnSurfaceVariant = DarkGray // Texto sobre variantes de superficie

val LightOutline = Gray // Borde de campos de texto

// Dark Color Scheme
// Para el tema oscuro, invertiremos algunos o usaremos variantes más oscuras
val DarkPrimary = CustomPrimary // O una versión un poco más clara si es demasiado oscuro
val DarkOnPrimary = OnPrimary
val DarkPrimaryContainer = CustomDark // El azul/morado muy oscuro
val DarkOnPrimaryContainer = White

val DarkSecondary = CustomSecondary
val DarkOnSecondary = OnSecondary
val DarkSecondaryContainer = CustomAccent.copy(alpha = 0.3f) // Versión más sutil del acento
val DarkOnSecondaryContainer = OnAccent

val DarkTertiary = CustomAccent
val DarkOnTertiary = OnAccent
val DarkTertiaryContainer = CustomAccent.copy(alpha = 0.2f)
val DarkOnTertiaryContainer = OnAccent

val DarkBackground = CustomDark // El azul/morado muy oscuro para el fondo principal
val DarkOnBackground = White // Texto blanco sobre el fondo oscuro

val DarkSurface = CustomDark.copy(alpha = 0.8f) // Superficies un poco más claras que el fondo
val DarkOnSurface = White
val DarkSurfaceVariant = DarkGray
val DarkOnSurfaceVariant = White

val DarkOutline = Gray // Borde de campos de texto

// Definiciones para degradados comunes (para facilitar la reutilización)
// ⚠️ ASEGÚRATE DE QUE ESTOS ESTÉN AQUÍ
val WelcomeGradientColorsLight = listOf(CustomPrimary, CustomSecondary, CustomAccent)
val WelcomeGradientColorsDark = listOf(CustomDark, CustomSecondary, CustomPrimary)

val TestGradientColorsLight = listOf(Color(0xFF64B5F6), Color(0xFFBA68C8), Color(0xFFEF5350)) // Blue, Purple, Red (tus anteriores)
val TestGradientColorsDark = listOf(Color(0xFF3F51B5), Color(0xFF7B1FA2), Color(0xFFD32F2F)) // Versión más oscura