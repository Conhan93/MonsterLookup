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

val lightOchre = Color(0xFFD27C19)
val bronze = Color(0xFFD58F3F)
val ceruleanBlue = Color(0xF03B60E8)
val glacous = Color(0xFF6280DB)
val alabaster = Color(0xFFEEECE4)

val lightColours = lightColors(
    primary = lightOchre,
    primaryVariant = bronze,
    secondary = glacous,
    secondaryVariant = ceruleanBlue,
    background = alabaster,
)