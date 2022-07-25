package View

import Model.Monster.Action
import Model.Monster.ActionUsage
import Model.Monster.Monster
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


@Composable
fun ActionsView(monster: Monster, modifier: Modifier = Modifier) {

    val actions = monster.actions



    LazyColumn(
        modifier = Modifier
            .fillMaxHeight()
            .padding(horizontal = 10.dp)
            .then(modifier)
        ,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {

        // action view header
        item {
            Text(
                "Actions",
                modifier = Modifier
                    .absolutePadding(left = 15.dp)
            )

            Spacer(Modifier.height(5.dp))
        }

        // list of actions
        items(actions) {
            actionItem(
                it,
                Modifier
                    .padding(5.dp)
                    .then(modifier)
            )
        }
    }
}


@Composable
fun actionItem(action: Action, modifier: Modifier) {

    Column(
        modifier = Modifier
            .fillMaxWidth(0.5f)
            .then(modifier)
    ) {
        action.name?.let {
            Text(it)
        }
        action.desc?.let {
            Text(it)
        }

        ChoiceView(action.options)

        action.attack_bonus?.let {
            Text("Attack Bonus : $it")
        }

        ActionUsageView(action.usage)
    }
}

@Composable
fun ActionUsageView(usage : ActionUsage) {

    if(!usage.type.isNullOrEmpty() && !usage.dice.isNullOrEmpty() && usage.min_value != null)
        Column {
            Text(usage.type)
            Text("Dice : ${usage.dice}")
            Text("Minimum value : ${usage.min_value}")
        }
}