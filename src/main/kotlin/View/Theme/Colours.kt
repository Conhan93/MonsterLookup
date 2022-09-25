package Theme

import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.ui.graphics.Color

/**
 * Colours and colour names generated with coolors.co
 */

object DarkRedColourTheme {
    val primary = Color(0xFF581E13)
    val primaryVariant = Color(0xFF614A49)
    val secondary = Color(0xFFA08680)
    val secondaryVariant = Color(0xFF9A82A3)
    val background = Color(0xFF2D2D2A)
    val surface = Color(0xFF3D3D3D)
    val onSurface = Color(0xFFFBFBFB)
}

val darkColours = darkColors(
    primary = DarkRedColourTheme.primary,
    primaryVariant = DarkRedColourTheme.primaryVariant,
    secondary = DarkRedColourTheme.primary,
    secondaryVariant = DarkRedColourTheme.secondary,
    background = DarkRedColourTheme.background,
    onBackground = DarkRedColourTheme.onSurface,
    onSurface = DarkRedColourTheme.onSurface,
    surface = DarkRedColourTheme.surface,
    onPrimary = DarkRedColourTheme.onSurface,
    onSecondary = DarkRedColourTheme.onSurface
)


object LightRedColourTheme {
    val primary = Color(0xFF581E13)
    val primaryVariant = Color(0xFF614A49)
    val secondary = Color(0xFFA08680)
    val secondaryVariant = Color(0xFF9A82A3)
    val background = Color(0xFFFBFBFB)
    val surface = Color(0xFFECECEC)
    val onSurface = Color(0xFF3D3D3D)
    val onSecondary = surface
}

val lightColours = lightColors(
    primary = LightRedColourTheme.primary,
    primaryVariant = LightRedColourTheme.primaryVariant,
    secondary = LightRedColourTheme.primary,
    secondaryVariant = LightRedColourTheme.secondary,
    background = LightRedColourTheme.background,
    onBackground = LightRedColourTheme.onSurface,
    onSurface = LightRedColourTheme.primary,
    onPrimary = LightRedColourTheme.surface,
    onSecondary = LightRedColourTheme.onSecondary
)