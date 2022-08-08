package View

import Service.MonsterContentService
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import State.State
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*


@Composable
fun Search(
    state: MutableState<State?>,
    monsterService : MonsterContentService,
    modifier : Modifier = Modifier
) {
    var name = mutableStateOf("")

    val padding = Modifier.padding(5.dp) //PaddingValues(5.dp)

    // Coroutine scope for the http request
    val requestScope = CoroutineScope(Dispatchers.IO)

    fun performRequest(name : String) {
        requestScope.launch {
            try {
                //val monster = monsterService.getContent(name)

                state.value = monsterService.getContent(name)
            } catch (e : Exception) {
                state.value = State.Error(e)
            }
        }
    }

    Row(modifier) {
        searchField(
            name = name,
            modifier = padding,
        ) { performRequest(name.value) }

        Spacer(Modifier.width(10.dp))

        searchButton(padding) { performRequest(name.value) }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun searchField(
    name : MutableState<String>,
    modifier: Modifier = Modifier,
    onEnterPressed : () -> Unit
) {

    OutlinedTextField(
        name.value,
        onValueChange = {
            if(!it.contains("\n"))
                name.value = it
        },
        modifier = modifier
            .onKeyEvent {
                if(it.key.equals(Key.Enter)) {
                    onEnterPressed()
                    return@onKeyEvent true
                } else
                    true
            },
        shape = CutCornerShape(5.dp),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            backgroundColor = MaterialTheme.colors.secondary,
            focusedBorderColor = MaterialTheme.colors.secondaryVariant
        ),
        placeholder = { Text("Enter name of monster", color = MaterialTheme.colors.background) },
    )
}

@Composable
fun searchButton(
    modifier: Modifier = Modifier,
    searchAction : () -> Unit,
) {
    Button(
        onClick =  { searchAction() },
        colors = ButtonDefaults.buttonColors(
            backgroundColor = MaterialTheme.colors.secondaryVariant
        ),
        modifier = modifier
            .padding(top = 3.dp)
            .height(IntrinsicSize.Min)
            .width(IntrinsicSize.Min)
    ) {
        Icon(
            imageVector = Icons.Filled.Search,
            contentDescription = "search button",
            tint = MaterialTheme.colors.background
        )
    }
}