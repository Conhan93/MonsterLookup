package View

import Model.Monster.Action
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
fun ActionsView(monster: MutableState<Monster>, modifier: Modifier) {

    val actions = monster.value.actions



    LazyColumn(
        modifier = modifier
            .padding(horizontal = 10.dp)
        ,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {

        // action view header
        item {
            Text(
                "Actions",
                modifier = Modifier
                    .absolutePadding(left = 15.dp)
                    .background(Brush.horizontalGradient(
                        colors = listOf(
                            Color.DarkGray,
                            Color.Gray
                        )
                    ))
            )

            Spacer(Modifier.height(5.dp))
        }

        // list of actions
        items(actions) {
            actionItem(
                it,
                modifier
                    .padding(5.dp)
            )
        }
    }
}


@Composable
fun actionItem(action: Action, modifier: Modifier) {

    Column(
        modifier = modifier
            .fillMaxWidth(0.5f)
    ) {
        action.name?.let {
            Text(it)
            //Spacer(Modifier.height(5.dp))
        }
        action.desc?.let {
            Text(it)
            //Spacer(Modifier.height(5.dp))
        }

        // TODO display options

        action.attack_bonus?.let {
            Text("Attack Bonus : $it")
            //Spacer(Modifier.height(5.dp))
        }

        // TODO display dc

        if (!action.attacks.isEmpty()) {
            // TODO display attacks
        }
        if(!action.damage.isEmpty()) {
            // TODO display attack damages
        }

        // TODO display usage item
    }
}