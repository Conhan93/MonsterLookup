package View

import Model.Base.APIReference
import Model.Monster.Monster
import Model.Monster.SpecialAbilities

import View.ActionsAndAbilities.SpellDetail
import View.Common.*

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment

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
        item {
            Text(
                text = "Special Abilities",
                style = MaterialTheme.typography.h6
            )
        }

        items(specialAbilities) {
            SpecialAbilityItem(
                it,
                Modifier
                    .fillMaxWidth()
                    .padding(5.dp)
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SpecialAbilityItem(ability : SpecialAbilities, modifier: Modifier = Modifier) {

    var isClicked by remember { mutableStateOf(false) }

    Surface(
        modifier = modifier,
        color = MaterialTheme.colors.primary,
        shape = RoundedCornerShape(4.dp),
        elevation = 2.dp,
        onClick = { isClicked = isClicked.not() }
    ) {
        Column(
            modifier = Modifier.padding(5.dp)
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

            ability.spellcasting.let {
                it.school?.let { it1 -> Text(it1) }
            }
        }
    }

    // Display popup with details of spell in special ability
    if (isClicked && ability.spellcasting.spells.isNotEmpty()) {
        FullScreenPopup(
            innerBoxSize = 0.7f,
            onDismiss = { isClicked = false }
        ) {
            SpellDetail(
                references = ability
                    .spellcasting
                    .spells
                    .map {
                        APIReference(
                            name = it.name,
                            url = it.url
                        )
                    },
                modifier
                    .align(Alignment.Center)
            )
        }
    }
}