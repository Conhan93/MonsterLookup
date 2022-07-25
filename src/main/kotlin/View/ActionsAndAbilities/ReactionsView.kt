package View

import Model.Monster.Monster
import Model.Monster.Reaction
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ReactionsView(monster: Monster, modifier: Modifier = Modifier) {

    val reactions = monster.reactions

    LazyColumn(
        modifier = Modifier
            .then(modifier)
    ) {

        item {
            Text("Reactions")
            Spacer(Modifier.height(5.dp))
        }

        items(reactions) {
            ReactionItem(it)
        }
    }
}

@Composable
private fun ReactionItem(reaction: Reaction, modifier: Modifier = Modifier) {

    Column(

    ) {
        reaction.name?.let { Text(it) }
        reaction.desc?.let { Text(it) }

        ChoiceView(reaction.options)

        reaction.attack_bonus?.let { Text("Attack Bonus : $it") }
    }
}