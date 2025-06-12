package com.example.musicgame.ui.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// Paleta de colores personalizada
object MusicGameColors {
    val Purple = Color(0xFFA3659E)      // #A3659E
    val Blue = Color(0xFF76B0C2)        // #76B0C2
    val Green = Color(0xFFC7D48E)       // #C7D48E
    val Orange = Color(0xFFF09C60)      // #F09C60
    val Red = Color(0xFFED5A55)         // #ED5A55

    // Variaciones para el tema
    val PurpleDark = Color(0xFF8B4A85)
    val BlueDark = Color(0xFF5A8FA8)
    val GreenLight = Color(0xFFD4E2A1)
    val OrangeLight = Color(0xFFF5B080)
    val RedLight = Color(0xFFF17873)

    // Colores neutros
    val White = Color(0xFFFFFFFF)
    val Black = Color(0xFF1A1A1A)
    val Gray100 = Color(0xFFF5F5F5)
    val Gray200 = Color(0xFFE5E5E5)
    val Gray800 = Color(0xFF2D2D2D)
    val Gray900 = Color(0xFF1A1A1A)
}

// Tipografía personalizada
// Si tienes fuentes personalizadas, puedes agregarlas aquí
// val CustomFontFamily = FontFamily(
//     Font(R.font.your_font_regular, FontWeight.Normal),
//     Font(R.font.your_font_bold, FontWeight.Bold)
// )

val MusicGameTypography = Typography(
    displayLarge = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 32.sp,
        lineHeight = 40.sp,
        letterSpacing = (-0.5).sp
    ),
    displayMedium = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 28.sp,
        lineHeight = 36.sp,
        letterSpacing = (-0.25).sp
    ),
    headlineLarge = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
        lineHeight = 32.sp,
        letterSpacing = 0.sp
    ),
    headlineMedium = TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 20.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.15.sp
    ),
    bodyLarge = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp,
        lineHeight = 26.sp,
        letterSpacing = 0.5.sp
    ),
    bodyMedium = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.25.sp
    ),
    labelLarge = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp
    )
)

// Esquema de colores claro
private val LightColorScheme = lightColorScheme(
    primary = MusicGameColors.Purple,
    onPrimary = MusicGameColors.White,
    primaryContainer = MusicGameColors.PurpleDark,
    onPrimaryContainer = MusicGameColors.White,

    secondary = MusicGameColors.Blue,
    onSecondary = MusicGameColors.White,
    secondaryContainer = MusicGameColors.BlueDark,
    onSecondaryContainer = MusicGameColors.White,

    tertiary = MusicGameColors.Green,
    onTertiary = MusicGameColors.Black,
    tertiaryContainer = MusicGameColors.GreenLight,
    onTertiaryContainer = MusicGameColors.Black,

    background = MusicGameColors.Gray100,
    onBackground = MusicGameColors.Black,

    surface = MusicGameColors.White,
    onSurface = MusicGameColors.Black,
    surfaceVariant = MusicGameColors.Gray200,
    onSurfaceVariant = MusicGameColors.Gray800,

    error = MusicGameColors.Red,
    onError = MusicGameColors.White,
    errorContainer = MusicGameColors.RedLight,
    onErrorContainer = MusicGameColors.Black,

    outline = MusicGameColors.Gray800,
    outlineVariant = MusicGameColors.Gray200
)

// Esquema de colores oscuro
private val DarkColorScheme = darkColorScheme(
    primary = MusicGameColors.Purple,
    onPrimary = MusicGameColors.White,
    primaryContainer = MusicGameColors.PurpleDark,
    onPrimaryContainer = MusicGameColors.White,

    secondary = MusicGameColors.Blue,
    onSecondary = MusicGameColors.Black,
    secondaryContainer = MusicGameColors.BlueDark,
    onSecondaryContainer = MusicGameColors.White,

    tertiary = MusicGameColors.Green,
    onTertiary = MusicGameColors.Black,
    tertiaryContainer = MusicGameColors.GreenLight,
    onTertiaryContainer = MusicGameColors.Black,

    background = MusicGameColors.Gray900,
    onBackground = MusicGameColors.White,

    surface = MusicGameColors.Gray800,
    onSurface = MusicGameColors.White,
    surfaceVariant = MusicGameColors.Gray800,
    onSurfaceVariant = MusicGameColors.Gray200,

    error = MusicGameColors.Red,
    onError = MusicGameColors.White,
    errorContainer = MusicGameColors.RedLight,
    onErrorContainer = MusicGameColors.Black,

    outline = MusicGameColors.Gray200,
    outlineVariant = MusicGameColors.Gray800
)

@Composable
fun MusicGameTheme(
    darkTheme: Boolean = false, // Puedes cambiar esto según las preferencias del sistema
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) {
        DarkColorScheme
    } else {
        LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = MusicGameTypography,
        content = content
    )
}