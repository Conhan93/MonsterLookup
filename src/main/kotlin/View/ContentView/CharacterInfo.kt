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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import java.util.Arrays


@Composable()
fun CharacterInfo(
    character : Monster,
    textStyle : TextStyle = MaterialTheme.typography.body2,
    modifier : Modifier = Modifier
) {

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
                    character.challenge_rating?.let { Text(text = "Rating : $it", style = textStyle) }
                    character.xp?.let { Text(text = "XP : $it", style = textStyle) }
                }
            }

            Stats(
                character = character,
                modifier = Modifier.padding(3.dp)
            )

        }
    }
}

@Composable
fun characterName(
    name : String?,
    textStyle : TextStyle = MaterialTheme.typography.body2,
    modifier: Modifier = Modifier
) {
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
            style = textStyle,
            modifier = Modifier.padding(2.dp)
        )
    }
}

@Composable
fun Stats(
    character: Monster,
    textStyle : TextStyle = MaterialTheme.typography.body2,
    modifier: Modifier = Modifier
) {
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
                Text(text = "STR : ${character.strength ?: "N/A"}",style = textStyle)
                Text(text = "CON : ${character.constitution ?: "N/A"}", style = textStyle)
                Text(text = "WIS : ${character.wisdom ?: "N/A"}", style = textStyle)

            }
            Row(
                modifier = Modifier.padding(2.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(text = "DEX : ${character.dexterity ?: "N/A"}", style = textStyle)
                Text(text = "INT : ${character.intelligence ?: "N/A"}", style = textStyle)
                Text(text = "CHA : ${character.intelligence ?: "N/A"}", style = textStyle)
            }
        }
    }
}