package View.InfoAndStats

import Model.Monster.Monster
import View.SimpleTextList
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier


@Composable
fun Conditions(monster : MutableState<Monster>, modifier : Modifier = Modifier) {


    Row(
        modifier = Modifier
            .then(modifier),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        SimpleTextList(
            label = "Vulnerabilities",
            items = monster.value.damage_vulnerabilities,
            backgroundColour = MaterialTheme.colors.secondary,
            modifier = Modifier.weight(1f, fill = false)
        )
        SimpleTextList(
            label = "Resistances",
            items = monster.value.damage_resistances,
            backgroundColour = MaterialTheme.colors.secondary,
            modifier = Modifier.weight(1f, fill = false)
        )
        SimpleTextList(
            label = "Immunities",
            items = monster.value.damage_immunities + monster
                .value
                .condition_immunities
                .mapNotNull { it.name },
            backgroundColour = MaterialTheme.colors.secondary,
            modifier = Modifier.weight(1f, fill = false)
        )
    }
}