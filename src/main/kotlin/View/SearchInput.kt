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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


@Composable
fun SearchInput(monsterService : MonsterService, modifier : Modifier) {

    var name by remember { mutableStateOf("") }

    val padding = PaddingValues(5.dp)

    // Coroutine scope for the http request
    val requestScope = CoroutineScope(Dispatchers.IO)

    Row {

        OutlinedTextField(
            name,
            onValueChange = {
                name = it
            },
            modifier = modifier
                .padding(padding),
            shape = CutCornerShape(5.dp),
            placeholder = { Text("Enter name of monster") },
        )

        Spacer(Modifier.width(10.dp))

        Button(
            onClick =  {
                requestScope.launch {
                    monsterService.monster.value = monsterService.getMonster(name)
                }
            },
            modifier = modifier
                .padding(padding)
        ) {
            Text("Search")
        }
    }

}