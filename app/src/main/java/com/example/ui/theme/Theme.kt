package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// Light Color Schemes
private val VibrantLightColorScheme = lightColorScheme(
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

private val SunsetLightColorScheme = lightColorScheme(
    primary = SunsetPrimary,
    onPrimary = Color.White,
    primaryContainer = SunsetPrimaryContainer,
    onPrimaryContainer = SunsetOnPrimaryContainer,
    secondary = Color(0xFFB45309),
    background = SunsetBackground,
    onBackground = Color(0xFF1F1000),
    surface = Color.White,
    onSurface = Color(0xFF1F1000),
    surfaceVariant = Color(0xFFFEF3C7),
    onSurfaceVariant = Color(0xFF78350F)
)

private val SakuraLightColorScheme = lightColorScheme(
    primary = SakuraPrimary,
    onPrimary = Color.White,
    primaryContainer = SakuraPrimaryContainer,
    onPrimaryContainer = SakuraOnPrimaryContainer,
    secondary = Color(0xFFF43F5E),
    background = SakuraBackground,
    onBackground = Color(0xFF3B0712),
    surface = Color.White,
    onSurface = Color(0xFF3B0712),
    surfaceVariant = Color(0xFFFFE4E6),
    onSurfaceVariant = Color(0xFF9F1239)
)

private val OceanLightColorScheme = lightColorScheme(
    primary = OceanPrimary,
    onPrimary = Color.White,
    primaryContainer = OceanPrimaryContainer,
    onPrimaryContainer = OceanOnPrimaryContainer,
    secondary = Color(0xFF0D9488),
    background = OceanBackground,
    onBackground = Color(0xFF032B45),
    surface = Color.White,
    onSurface = Color(0xFF032B45),
    surfaceVariant = Color(0xFFE0F2FE),
    onSurfaceVariant = Color(0xFF0369A1)
)

// Dark Color Schemes
private val UniversalDarkColorScheme = darkColorScheme(
    primary = Color(0xFF4ADE80),
    onPrimary = Color(0xFF052E16),
    primaryContainer = Color(0xFF14532D),
    onPrimaryContainer = Color(0xFFDCFCE7),
    secondary = Color(0xFF34D399),
    onSecondary = Color(0xFF022C22),
    background = Color(0xFF121815),
    onBackground = Color(0xFFF0FDF4),
    surface = Color(0xFF1C2420),
    onSurface = Color(0xFFF0FDF4),
    surfaceVariant = Color(0xFF28332D),
    onSurfaceVariant = Color(0xFFA7F3D0)
)

private val SunsetDarkColorScheme = darkColorScheme(
    primary = Color(0xFFFBBF24),
    onPrimary = Color(0xFF451A03),
    primaryContainer = Color(0xFF78350F),
    onPrimaryContainer = Color(0xFFFEF3C7),
    secondary = Color(0xFFF59E0B),
    background = Color(0xFF18120B),
    onBackground = Color(0xFFFEF3C7),
    surface = Color(0xFF241C12),
    onSurface = Color(0xFFFEF3C7),
    surfaceVariant = Color(0xFF382A1B),
    onSurfaceVariant = Color(0xFFFDE68A)
)

private val MidnightDarkColorScheme = darkColorScheme(
    primary = MidnightPrimary,
    onPrimary = Color.Black,
    primaryContainer = MidnightPrimaryContainer,
    onPrimaryContainer = MidnightOnPrimaryContainer,
    secondary = Color(0xFF34D399),
    background = MidnightBackground,
    onBackground = Color(0xFFE2E8F0),
    surface = MidnightSurface,
    onSurface = Color(0xFFE2E8F0),
    surfaceVariant = Color(0xFF243038),
    onSurfaceVariant = Color(0xFF94A3B8)
)

private val SakuraDarkColorScheme = darkColorScheme(
    primary = Color(0xFFFB7185),
    onPrimary = Color(0xFF4C0519),
    primaryContainer = Color(0xFF881337),
    onPrimaryContainer = Color(0xFFFFE4E6),
    secondary = Color(0xFFF43F5E),
    background = Color(0xFF1F0D13),
    onBackground = Color(0xFFFFE4E6),
    surface = Color(0xFF2D141C),
    onSurface = Color(0xFFFFE4E6),
    surfaceVariant = Color(0xFF441C29),
    onSurfaceVariant = Color(0xFFFECDD3)
)

private val MysticDarkColorScheme = darkColorScheme(
    primary = MysticPrimary,
    onPrimary = Color.White,
    primaryContainer = MysticPrimaryContainer,
    onPrimaryContainer = MysticOnPrimaryContainer,
    secondary = Color(0xFFC084FC),
    background = MysticBackground,
    onBackground = Color(0xFFF3E8FF),
    surface = MysticSurface,
    onSurface = Color(0xFFF3E8FF),
    surfaceVariant = Color(0xFF3B2A58),
    onSurfaceVariant = Color(0xFFE9D5FF)
)

private val CyberDarkColorScheme = darkColorScheme(
    primary = CyberPrimary,
    onPrimary = Color.Black,
    primaryContainer = CyberPrimaryContainer,
    onPrimaryContainer = CyberOnPrimaryContainer,
    secondary = Color(0xFFEC4899),
    background = CyberBackground,
    onBackground = Color(0xFFE2E8F0),
    surface = CyberSurface,
    onSurface = Color(0xFFE2E8F0),
    surfaceVariant = Color(0xFF334155),
    onSurfaceVariant = Color(0xFFCBD5E1)
)

private val OceanDarkColorScheme = darkColorScheme(
    primary = Color(0xFF38BDF8),
    onPrimary = Color(0xFF0C4A6E),
    primaryContainer = Color(0xFF0369A1),
    onPrimaryContainer = Color(0xFFE0F2FE),
    secondary = Color(0xFF2DD4BF),
    background = Color(0xFF0A1926),
    onBackground = Color(0xFFE0F2FE),
    surface = Color(0xFF112233),
    onSurface = Color(0xFFE0F2FE),
    surfaceVariant = Color(0xFF1E3A52),
    onSurfaceVariant = Color(0xFFBAE6FD)
)

@Composable
fun HabitTrackerTheme(
    selectedThemeId: String = "vibrant",
    themeMode: String = "system", // "system", "light", "dark"
    content: @Composable () -> Unit
) {
    val isSystemDark = isSystemInDarkTheme()
    val isDark = when (themeMode) {
        "dark" -> true
        "light" -> false
        else -> isSystemDark
    }

    val colorScheme = if (isDark) {
        when (selectedThemeId) {
            "sunset" -> SunsetDarkColorScheme
            "midnight" -> MidnightDarkColorScheme
            "sakura" -> SakuraDarkColorScheme
            "mystic" -> MysticDarkColorScheme
            "cyber" -> CyberDarkColorScheme
            "ocean" -> OceanDarkColorScheme
            else -> UniversalDarkColorScheme
        }
    } else {
        when (selectedThemeId) {
            "sunset" -> SunsetLightColorScheme
            "midnight" -> MidnightDarkColorScheme
            "sakura" -> SakuraLightColorScheme
            "mystic" -> MysticDarkColorScheme
            "cyber" -> CyberDarkColorScheme
            "ocean" -> OceanLightColorScheme
            else -> VibrantLightColorScheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
