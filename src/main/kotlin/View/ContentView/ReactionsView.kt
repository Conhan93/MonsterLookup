package View

import Model.Monster.Monster
import Model.Monster.Reaction
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
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
            Text(
                text = "Reactions",
                style = MaterialTheme.typography.h5
            )
            Spacer(Modifier.height(5.dp))
        }

        items(reactions) {
            ReactionItem(
                reaction = it,
                modifier = Modifier.padding(5.dp)
                )
        }
    }
}

@Composable
private fun ReactionItem(reaction: Reaction, modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier,
        color = MaterialTheme.colors.primary,
        shape = RoundedCornerShape(4.dp),
        elevation = 2.dp
    ) {
        Column(modifier = Modifier.padding(5.dp)) {
            reaction.name?.let { Text(it, style = MaterialTheme.typography.h6) }
            reaction.desc?.let { Text(it) }


            reaction.attack_bonus?.let { Text("Attack Bonus : $it") }
        }
    }
}