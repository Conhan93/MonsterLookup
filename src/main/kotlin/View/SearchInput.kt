package View

import Service.MonsterService
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchInput(monsterService : MonsterService, modifier : Modifier = Modifier) {

    var name by remember { mutableStateOf("") }

    val padding = PaddingValues(5.dp)

    // Coroutine scope for the http request
    val requestScope = CoroutineScope(Dispatchers.IO)

    fun performRequest(name : String) {
        requestScope.launch {
            monsterService.monster.value = monsterService.getMonster(name)
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
            placeholder = { Text("Enter name of monster") },
        )

        Spacer(Modifier.width(10.dp))

        // search button
        Button(
            onClick =  {
                       performRequest(name)
            },
            modifier = modifier
                .padding(padding)
        ) {
            Text("Search")
        }
    }
}