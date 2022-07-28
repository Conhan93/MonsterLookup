package View

import Model.Monster.Monster
import Service.MonsterService
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
import androidx.compose.runtime.*


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchInput(
    state: MutableState<State<Monster>?>,
    monsterService : MonsterService,
    modifier : Modifier = Modifier
) {

    var name by remember { mutableStateOf("") }

    val padding = PaddingValues(5.dp)

    // Coroutine scope for the http request
    val requestScope = CoroutineScope(Dispatchers.IO)

    fun performRequest(name : String) {
        requestScope.launch {
            try {
                val monster = monsterService.getMonster(name)
                monsterService.monster.value = monster

                state.value = State.Content(monster)
            } catch (e : Exception) {
                state.value = State.Error(e)
            }
        }
    }

    Row {
        // search box
        OutlinedTextField(
            name,
            onValueChange = {
                    if(!it.contains("\n"))
                        name = it
            },
            modifier = modifier
                .padding(padding)
                .onKeyEvent {
                            if(it.key.equals(Key.Enter)) {
                                performRequest(name)
                                return@onKeyEvent true
                            } else
                                true
                },
            shape = CutCornerShape(5.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                backgroundColor = MaterialTheme.colors.secondary,
                focusedBorderColor = MaterialTheme.colors.secondaryVariant
            ),
            placeholder = { Text("Enter name of monster") },
        )

        Spacer(Modifier.width(10.dp))

        // search button
        searchButton(Modifier.padding(padding)) { performRequest(name) }
    }
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
        Text("Search")
    }
}