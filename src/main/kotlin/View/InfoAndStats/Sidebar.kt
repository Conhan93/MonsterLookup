package View.InfoAndStats

import Model.Monster.Monster
import View.Common.SolidTextList

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun Sidebar(monster : Monster, modifier: Modifier = Modifier) {

    val languages =
        if(monster.languages.isNullOrEmpty())
            listOf()
        else
            monster.languages!!.split(",").map(String::trim)

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .padding(5.dp)
            .then(modifier),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        if(languages.isNotEmpty())
            SolidTextList(
                label = { Text("Languages") },
                textItems = languages,
                textColour = MaterialTheme.colors.onSecondary,
                backgroundColour = MaterialTheme.colors.secondary
            )
        if(monster.proficiencies.isNotEmpty())
            SolidTextList(
                label = { Text("Proficiencies") },
                textColour = MaterialTheme.colors.onSecondary,
                textItems = monster.proficiencies
                    .map { "${it.proficiency.name}: ${it.value}" },
                backgroundColour = MaterialTheme.colors.secondary
            )
    }
}