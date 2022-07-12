package View

import Service.MonsterService
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun SearchInput(monsterService : MonsterService, modifier : Modifier) {

    var name by remember { mutableStateOf("") }
    Card(
        modifier = modifier
    ) {
        Row {
            TextField(
                name,
                onValueChange = {
                    name = it
                },
                modifier = modifier
            )
            Spacer(Modifier.width(10.dp))
            Button(
                onClick =  {
                    monsterService.monster.value = monsterService.getMonster(name)
                },
                modifier = modifier
            ) {

            }
        }
    }

}