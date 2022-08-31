package View

import Model.Monster.Monster
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import java.util.Arrays

@Preview
@Composable()
fun CharacterInfo(character : Monster, modifier : Modifier = Modifier) {

    Row(
        modifier = Modifier
            .width(IntrinsicSize.Max)
            .padding(10.dp)
            .then(modifier),
    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .width(IntrinsicSize.Max),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            characterName(character.name)

            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(3.dp),
                shape = RoundedCornerShape(3.dp),
                color = MaterialTheme.colors.primary,
                elevation = 2.dp
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 3.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    character.challenge_rating?.let { Text("Rating : $it") }
                    character.xp?.let { Text("XP : $it") }
                }
            }

            Stats(
                character = character,
                modifier = Modifier.padding(3.dp)
            )

        }

        About(
            character = character,
            modifier = Modifier.fillMaxHeight()
        )
    }
}

@Composable
fun characterName(name : String?, modifier: Modifier = Modifier) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(3.dp)
            .then(modifier),
        shape = RoundedCornerShape(3.dp),
        color = MaterialTheme.colors.primary,
        elevation = 2.dp
    ) {
        Text(
            text = "Name : ${name ?: "Unavailable"}",
            modifier = Modifier.padding(2.dp)
        )
    }
}

@Composable
fun Stats(character: Monster, modifier: Modifier = Modifier) {

    Surface(
        modifier = Modifier
            .padding(2.dp)
            .then(modifier),
        shape = RoundedCornerShape(3.dp),
        color = MaterialTheme.colors.primary,
        elevation = 2.dp
    ) {
        Column {
            Row(
                modifier = Modifier.padding(2.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text("STR : ${character.strength ?: "N/A"}")
                Text("CON : ${character.constitution ?: "N/A"}")
                Text("WIS : ${character.wisdom ?: "N/A"}")

            }
            Row(
                modifier = Modifier.padding(2.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text("DEX : ${character.dexterity ?: "N/A"}")
                Text("INT : ${character.intelligence ?: "N/A"}")
                Text("CHA : ${character.intelligence ?: "N/A"}")
            }
        }
    }
}

@Composable
fun About(character: Monster, modifier: Modifier = Modifier) {

    Surface(
        modifier = Modifier
            .padding(2.dp)
            .then(modifier),
        shape = RoundedCornerShape(3.dp),
        color = MaterialTheme.colors.primary,
        elevation = 2.dp
    ) {
        Column(
            modifier = Modifier.padding(start = 2.dp),
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
}