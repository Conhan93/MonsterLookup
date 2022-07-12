package View

import Model.Monster.Monster
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Preview
@Composable()
fun CharacterInfo(character : Monster, modifier : Modifier) {

    Card(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .background(color = Color.DarkGray)
                .padding(15.dp)
        ) {
            Row {
                Column {
                    Text("Name : ${character.name ?: "Unavailable"}")
                    Column { Text(text = character.desc.joinToString()) }
                }

            }
            Row {
                Text("STR : ${character.strength ?: "N/A" }")
                Spacer(Modifier.width(10.dp))
                Text("CON : ${character.constitution ?: "N/A" }")
                Spacer(Modifier.width(10.dp))
                Text("WIS : ${character.wisdom ?: "N/A" }")
                Spacer(Modifier.width(10.dp))

            }
            Row {
                Text("DEX : ${character.dexterity ?: "N/A" }")
                Spacer(Modifier.width(10.dp))
                Text("INT : ${character.intelligence ?: "N/A" }")
                Spacer(Modifier.width(10.dp))
                Text("CHA : ${character.intelligence ?: "N/A" }")
                Spacer(Modifier.width(10.dp))
            }
        }
    }
}