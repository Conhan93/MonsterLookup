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


    Row(
        modifier = Modifier
            .then(modifier),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {

        val rowElementWeight = Modifier.weight(1f, fill = false)

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