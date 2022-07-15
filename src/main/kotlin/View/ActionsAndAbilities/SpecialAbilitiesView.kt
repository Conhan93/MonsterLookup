package View

import Model.Monster.Monster
import Model.Monster.SpecialAbilities
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SpecialAbilitiesView(monster: MutableState<Monster>, modifier: Modifier = Modifier) {

    val specialAbilities = monster.value.special_abilities

    LazyColumn(
        modifier = modifier,
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
            //Spacer(Modifier.height(5.dp))
        }
        ability.desc?.let {
            Text(it)
            //Spacer(Modifier.height(5.dp))
        }

        // TODO display options

        ability.attack_bonus?.let {
            Text("Attack Bonus : $it")
            //Spacer(Modifier.height(5.dp))
        }

        if (!ability.damage.isEmpty()) {
            // TODO display damage
        }

        // TODO display dc


        ability.spellCasting.let {
            it.school?.let { it1 -> Text(it1) }
        }

        // TODO display usage item
    }
}