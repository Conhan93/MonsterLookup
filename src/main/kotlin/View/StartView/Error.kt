package View

import View.State.State
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun Error(
    error: State.Error,
    onStateChange : (State) -> Unit
) {
    startBackground {
        startBox {
            ErrorMessage(
                error = error,
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(vertical = 10.dp)
            )

            Search(
                onStateChange = onStateChange,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
            )
        }
    }
}

@Composable
private fun ErrorMessage(error : State.Error, modifier: Modifier = Modifier) {

    val message = error.message

    OutlinedTextField(
        value = message,
        onValueChange = {},
        modifier = modifier,
        shape = CutCornerShape(5.dp),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            backgroundColor = MaterialTheme.colors.secondary,
            focusedBorderColor = MaterialTheme.colors.secondaryVariant
        )
    )
}