package View.Common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

private val isPopUpEnabled = mutableStateOf( false )
private var popupComposable : @Composable () -> Unit = {}

@Composable
fun FullscreenPopUpEnabledApp(
    application : @Composable () -> Unit
) {
        Box(Modifier.fillMaxSize()) {
            // create app
            application()

            if (isPopUpEnabled.value)
                popupComposable()
        }


}

fun FullScreenPopup(
    backgroundColour : Color = Color.LightGray.copy(alpha = 0.5f),
    innerBoxSize : Float = 1f,
    content : @Composable BoxScope.() -> Unit
) {
    popupComposable = {
        // outer box
        Box(
            modifier = Modifier
                .background(backgroundColour)
                .fillMaxSize()
                .clickable { isPopUpEnabled.value = false }
        ) {
            // inner box
            Box(
                modifier = Modifier
                    .fillMaxSize(innerBoxSize)
                    .align(Alignment.Center)
            ) { content() }
        }
    }

    isPopUpEnabled.value = true
}