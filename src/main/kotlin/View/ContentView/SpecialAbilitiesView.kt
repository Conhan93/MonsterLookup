package View.ContentView

import Model.Monster.Monster
import Model.Monster.SpecialAbilities
import Model.Spell.Spell

import View.Common.*
import ViewModel.Content.ContentEvent

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*

import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment

import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SpecialAbilitiesView(
    monster: Monster,
    modifier: Modifier = Modifier,
    isAbilityClicked : Boolean,
    onEvent : (ContentEvent) -> Unit,
) {

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
                style = MaterialTheme.typography.h5
            )
        }

        items(specialAbilities) {
            SpecialAbilityItem(
                ability = it,
                isAbilityClicked = isAbilityClicked,
                onEvent = onEvent,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp)
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SpecialAbilityItem(
    ability : SpecialAbilities,
    modifier: Modifier = Modifier,
    isAbilityClicked : Boolean,
    onEvent: (ContentEvent) -> Unit,
) {

    Surface(
        modifier = modifier,
        color = MaterialTheme.colors.primary,
        shape = RoundedCornerShape(4.dp),
        elevation = 2.dp,
        enabled = ability.spellcasting.spells.isNotEmpty(), // only clickable if ability has spells
        onClick = { onEvent(ContentEvent.onClickSpecialAbility(isAbilityClicked.not(), ability)) }
    ) {
        Column(
            modifier = Modifier.padding(5.dp)
        ) {
            ability.name?.let {
                Text(it, style = MaterialTheme.typography.subtitle1)
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
}