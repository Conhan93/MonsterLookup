package Theme

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable


@Composable
fun AppTheme(
    isDarkMode : Boolean = true,
    content : @Composable () -> Unit
) {
    MaterialTheme(
        colors =
            if(isDarkMode)
                darkColours
            else
                lightColours,
        content = content
    )
}