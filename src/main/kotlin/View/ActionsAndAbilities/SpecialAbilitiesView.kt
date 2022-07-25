package View

import Model.Monster.Monster
import Model.Monster.Spells.SpecialAbilities
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SpecialAbilitiesView(monster: Monster, modifier: Modifier = Modifier) {

    val specialAbilities = monster.special_abilities

    LazyColumn(
        modifier = Modifier
            .fillMaxHeight()
            .padding(5.dp)
            .then(modifier),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        // header
        item { Text("Special Abilities") }

        items(specialAbilities) {
            SpecialAbilityItem(
                it,
                modifier
                    .padding(5.dp)
            )
        }
    }
}

@Composable
fun SpecialAbilityItem(ability : SpecialAbilities, modifier: Modifier) {

    Column(
        modifier = modifier
            .fillMaxWidth(0.5f)
    ) {
        ability.name?.let {
            Text(it)
        }
        ability.desc?.let {
            Text(it)
        }


        ability.attack_bonus?.let {
            Text("Attack Bonus : $it")
        }


        // TODO display spells
        ability.spellcasting.let {
            it.school?.let { it1 -> Text(it1) }
        }
    }
}