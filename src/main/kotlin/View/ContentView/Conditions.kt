package View.InfoAndStats

import Model.Monster.Monster
import View.Common.SolidTextList
import View.Common.SolidTextListLazy

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable

import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun Conditions(monster : Monster, modifier : Modifier = Modifier) {

    val info = getAboutInfo(monster)

    Row(
        modifier = Modifier
            .then(modifier),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {

        val rowElementWeight = Modifier.weight(1f, fill = false)

        SolidTextListLazy(
            label = { Text("About") },
            textItems = info,
            textColour = MaterialTheme.colors.onSecondary,
            backgroundColour = MaterialTheme.colors.secondary,
            modifier = rowElementWeight
        )
        SolidTextListLazy(
            label = { Text("Vulnerabilities")},
            textItems = monster.damage_vulnerabilities,
            textColour = MaterialTheme.colors.onSecondary,
            backgroundColour = MaterialTheme.colors.secondary,
            modifier = rowElementWeight
        )
        SolidTextListLazy(
            label = { Text("Resistances")},
            textItems = monster.damage_resistances,
            textColour = MaterialTheme.colors.onSecondary,
            backgroundColour = MaterialTheme.colors.secondary,
            modifier = rowElementWeight
        )
        SolidTextListLazy(
            label = { Text("Immunities")},
            textItems = monster.damage_immunities,
            textColour = MaterialTheme.colors.onSecondary,
            backgroundColour = MaterialTheme.colors.secondary,
            modifier = rowElementWeight
        )
    }
}


private fun getAboutInfo(monster: Monster) : List<String> {
    val info = mutableListOf<String>()

    with(monster) {
        type?.let { info.add("Type: $it") }
        subtype?.let { info.add("Subtype: $it") }
        size?.let { info.add("Size: $it") }
        alignment?.let { info.add("Alignment: $it") }
        armor_class?.let { info.add("AC: $it") }
        hit_points?.let { info.add("HP: $it") }
    }

    return info
}