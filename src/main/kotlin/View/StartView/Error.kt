package View

import Service.MonsterContentService
import State.State
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun Error(
    state: MutableState<State?>
) {
    startBackground {
        startBox {
            ErrorMessage(
                error = state.value as State.Error,
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(vertical = 10.dp)
            )

            Search(
                state = state,
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