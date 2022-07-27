package View

import Model.Monster.Monster
import Service.MonsterService
import State.State
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.io.IOException

@Composable
fun Error(
    error : State.Error,
    state : MutableState<State<Monster>?>,
    monsterService: MonsterService
) {
    startBackground {
        startBox {
            ErrorMessage(
                error = error.error,
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(vertical = 10.dp)
            )

            SearchInput(
                state = state,
                monsterService = monsterService,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
            )
        }
    }
}

@Composable
private fun ErrorMessage(error : Throwable, modifier: Modifier = Modifier) {

    val message = when(error) {
        is InterruptedException -> "Request was interrupted"
        is IOException -> "Error sending request"
        is kotlinx.serialization.SerializationException ->
            "No monster with that name found"
        else -> error.localizedMessage
    }
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