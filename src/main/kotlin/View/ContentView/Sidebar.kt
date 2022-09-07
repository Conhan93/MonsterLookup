package View.InfoAndStats

import Model.Monster.Monster
import View.Common.SolidTextList

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun Sidebar(monster: Monster, modifier: Modifier = Modifier) {

    val languages = getMonsterLanguages(monster)
    val speeds = getMonsterSpeeds(monster)
    val senses = getMonsterSenses(monster)

    BoxWithConstraints(Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier
                .heightIn(min = maxHeight)
                .padding(5.dp)
                .then(modifier),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            item {
                SidebarList(label = "Senses", items = senses)
                SidebarList(label = "Speeds", items = speeds)
                SidebarList(label = "Languages", items = languages)
                SidebarList(
                    label = "Proficiencies",
                    items = monster
                        .proficiencies
                        .map { "${it.proficiency.name}: ${it.value}" }
                )
            }
        }
    }
}
@Composable
private fun SidebarList(
    label : String,
    labelTextStyle : TextStyle = MaterialTheme.typography.h6.copy(fontWeight = FontWeight.Bold),
    items : List<String>,
    textColour : Color = MaterialTheme.colors.onSecondary,
    backgroundColour : Color = MaterialTheme.colors.secondary
) {
    if(items.isNotEmpty())
        SolidTextList(
            label = { Text(label, style = labelTextStyle) },
            textItems = items,
            textColour = textColour,
            backgroundColour = backgroundColour
        )
}

/**
 * Extracts the different speeds the monster possesses
 *
 * @param monster Monster model
 * @return returns a list of strings containing the monsters
 * different speeds, empty if none available
 */
private fun getMonsterSpeeds(monster: Monster) : List<String> {
    val speeds = mutableListOf<String>()

    with(monster.speed) {
        walk?.let { speeds.add("Walking: $it") }
        burrow?.let { speeds.add("Burrowing: $it") }
        swim?.let { speeds.add("Swimming: $it") }
        climb?.let { speeds.add("Climbing: $it") }
        fly?.let { speeds.add("Flying: $it") }
    }

    return speeds
}

/**
 * Extracts the different senses the monster possesses
 *
 * @param monster Monster model
 * @return returns a list of strings containing the monsters
 * different senses, empty if none available
 */
private fun getMonsterSenses(monster: Monster) : List<String> {
    val senses = mutableListOf<String>()

    with(monster.senses) {
        passive_perception?.let { senses.add("Passive: $it") }
        blindsight?.let { senses.add("Blindsight: $it") }
        darkvision?.let { senses.add("Darkvision: $it") }
        tremorsense?.let { senses.add("Tremorsense: $it") }
        truesight?.let { senses.add("Truesight: $it") }
    }
    return senses
}

/**
 * Extracts the different languages the monster possesses
 *
 * @param monster Monster model
 * @return returns a list of strings containing the monsters
 * different languages, empty if none available
 */
private fun getMonsterLanguages(monster: Monster) : List<String> {
    return if (monster.languages.isNullOrEmpty())
                listOf()
            else
                monster.languages!!.split(",").map(String::trim)
}