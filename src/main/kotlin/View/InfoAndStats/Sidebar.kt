package View.InfoAndStats

import Model.Monster.Monster
import View.SimpleTextList
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun Sidebar(monster : Monster, modifier: Modifier = Modifier) {

    val languages =
        if(monster.languages.isNullOrEmpty())
            listOf()
        else
            monster.languages!!.split(",")

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .padding(5.dp)
            .then(modifier)
    ) {
        if(languages.isNotEmpty())
            SimpleTextList(
                label = "Languages",
                items = languages,
                backgroundColour = MaterialTheme.colors.secondary
            )
        if(monster.proficiencies.isNotEmpty())
            SimpleTextList(
                label = "Proficiencies",
                items = monster.proficiencies
                    .map { "${it.proficiency.name}: ${it.value}" },
                backgroundColour = MaterialTheme.colors.secondary
            )
    }
}