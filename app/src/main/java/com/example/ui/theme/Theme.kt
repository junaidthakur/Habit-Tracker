package com.example.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val VibrantColorScheme = lightColorScheme(
    primary = VibrantPrimary,
    onPrimary = VibrantOnPrimary,
    primaryContainer = VibrantPrimaryContainer,
    onPrimaryContainer = VibrantOnPrimaryContainer,
    secondary = VibrantSecondary,
    onSecondary = Color.White,
    background = VibrantBackground,
    onBackground = VibrantOnPrimaryContainer,
    surface = VibrantSurface,
    onSurface = VibrantOnPrimaryContainer,
    surfaceVariant = VibrantSurfaceVariant,
    onSurfaceVariant = VibrantSecondary,
    error = VibrantErrorContainer,
    onError = VibrantOnErrorContainer
)

private val SunsetColorScheme = lightColorScheme(
    primary = SunsetPrimary,
    onPrimary = Color.White,
    primaryContainer = SunsetPrimaryContainer,
    onPrimaryContainer = SunsetOnPrimaryContainer,
    secondary = Color(0xFFB45309),
    background = SunsetBackground,
    surface = Color.White,
    surfaceVariant = Color(0xFFFEF3C7)
)

private val MidnightColorScheme = darkColorScheme(
    primary = MidnightPrimary,
    onPrimary = Color.Black,
    primaryContainer = MidnightPrimaryContainer,
    onPrimaryContainer = MidnightOnPrimaryContainer,
    secondary = Color(0xFF34D399),
    background = MidnightBackground,
    surface = MidnightSurface,
    surfaceVariant = Color(0xFF243038)
)

private val SakuraColorScheme = lightColorScheme(
    primary = SakuraPrimary,
    onPrimary = Color.White,
    primaryContainer = SakuraPrimaryContainer,
    onPrimaryContainer = SakuraOnPrimaryContainer,
    secondary = Color(0xFFF43F5E),
    background = SakuraBackground,
    surface = Color.White,
    surfaceVariant = Color(0xFFFFE4E6)
)

private val MysticColorScheme = darkColorScheme(
    primary = MysticPrimary,
    onPrimary = Color.White,
    primaryContainer = MysticPrimaryContainer,
    onPrimaryContainer = MysticOnPrimaryContainer,
    secondary = Color(0xFFC084FC),
    background = MysticBackground,
    surface = MysticSurface,
    surfaceVariant = Color(0xFF3B2A58)
)

private val CyberColorScheme = darkColorScheme(
    primary = CyberPrimary,
    onPrimary = Color.Black,
    primaryContainer = CyberPrimaryContainer,
    onPrimaryContainer = CyberOnPrimaryContainer,
    secondary = Color(0xFFEC4899),
    background = CyberBackground,
    surface = CyberSurface,
    surfaceVariant = Color(0xFF334155)
)

private val OceanColorScheme = lightColorScheme(
    primary = OceanPrimary,
    onPrimary = Color.White,
    primaryContainer = OceanPrimaryContainer,
    onPrimaryContainer = OceanOnPrimaryContainer,
    secondary = Color(0xFF0D9488),
    background = OceanBackground,
    surface = Color.White,
    surfaceVariant = Color(0xFFE0F2FE)
)

@Composable
fun HabitTrackerTheme(
    selectedThemeId: String = "vibrant",
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = when (selectedThemeId) {
        "sunset" -> SunsetColorScheme
        "midnight" -> MidnightColorScheme
        "sakura" -> SakuraColorScheme
        "mystic" -> MysticColorScheme
        "cyber" -> CyberColorScheme
        "ocean" -> OceanColorScheme
        else -> VibrantColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
