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
import androidx.compose.ui.unit.em

@Preview
@Composable()
fun CharacterInfo(character : Monster, modifier : Modifier = Modifier) {


        Row(
            modifier = Modifier
                .padding(15.dp)
                .then(modifier)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Row {
                    Column {
                        Text("Name : ${character.name ?: "Unavailable"}")
                        Column { Text(text = character.desc.joinToString()) }
                    }
                }
                Stats(character = character)

            }

            About(
                character = character,
                modifier = Modifier.fillMaxHeight()
            )
        }
}

@Composable
fun Stats(character: Monster, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
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

@Composable
fun About(character: Monster, modifier: Modifier = Modifier) {

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {

        val textSize = 0.7.em

        @Composable
        fun TextCell(text : String, modifier: Modifier = Modifier) =
            Text(
                text = text,
                fontSize = textSize,
                modifier = modifier
            )

        TextCell("Type : ${character.type ?: "N/A"}")
        character.subtype?.let { TextCell("Subtype : ${character.subtype}") }
        TextCell("Size : ${character.size ?: "N/A"}")
        TextCell("Alignment : ${character.alignment ?: "N/A"}")
        TextCell("AC : ${character.armor_class ?: "N/A"}")
        TextCell("HP : ${character.hit_points ?: "N/A"}")
    }
}