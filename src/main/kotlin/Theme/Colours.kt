package Theme

import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.ui.graphics.Color

/**
 * Colours and colour names generated with coolors.co
 */


val jet = Color(0xFF2D2D2A)
val goldenBrown = Color(0xFF99672E)
val darkOchre = Color(0xFFC17926)
val darkCornFlowerBlue = Color(0xF0153D7D)
val queenBlue = Color(0xFF4B6582)

val darkColours = darkColors(
    primary = darkOchre,
    primaryVariant = goldenBrown,
    secondary = queenBlue,
    secondaryVariant = darkCornFlowerBlue,
    background = jet,
)


object LightRedColourTheme {
    val primary = Color(0xFF581E13)
    val primaryVariant = Color(0xFF614A49)
    val secondary = Color(0xFFA08680)
    val secondaryVariant = Color(0xFF9A82A3)
    val background = Color(0xFFFBFBFB)
    val surface = Color(0xFFECECEC)
    val onSurface = Color(0xFF3D3D3D)
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
    onSecondary = LightRedColourTheme.onSurface
)