package View.InfoAndStats

import Model.Monster.Monster
import View.SimpleTextList
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier

@Composable
fun Sidebar(monster : MutableState<Monster>, modifier: Modifier = Modifier) {

    val languages =
        if(monster.value.languages.isNullOrEmpty())
            listOf<String>()
        else
            monster.value.languages!!.split(",")

    Column(
        modifier = modifier
    ) {
        if(languages.isNotEmpty())
            SimpleTextList(
                label = "Languages",
                items = languages
            )
        if(monster.value.proficiencies.isNotEmpty())
            SimpleTextList(
                label = "Proficiencies",
                items = monster.value.proficiencies
                    .map { "${it.proficiency.name}: ${it.value}" }
            )
    }
}