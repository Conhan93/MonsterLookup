package View.InfoAndStats

import Model.Data.Monster.Monster
import View.Common.SolidTextListLazy

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable

import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
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
        val labelStyle = MaterialTheme.typography.h6.copy(fontWeight = FontWeight.Bold)

        SolidTextListLazy(
            label = { Text(text = "About", style =  labelStyle) },
            textItems = info,
            textColour = MaterialTheme.colors.onSecondary,
            backgroundColour = MaterialTheme.colors.secondary,
            modifier = rowElementWeight
        )
        SolidTextListLazy(
            label = { Text(text = "Vulnerabilities", style = labelStyle)},
            textItems = monster.damage_vulnerabilities,
            textColour = MaterialTheme.colors.onSecondary,
            backgroundColour = MaterialTheme.colors.secondary,
            modifier = rowElementWeight
        )
        SolidTextListLazy(
            label = { Text(text = "Resistances", style = labelStyle)},
            textItems = monster.damage_resistances,
            textColour = MaterialTheme.colors.onSecondary,
            backgroundColour = MaterialTheme.colors.secondary,
            modifier = rowElementWeight
        )
        SolidTextListLazy(
            label = { Text(text = "Immunities", style = labelStyle)},
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