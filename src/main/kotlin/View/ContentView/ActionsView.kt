package View

import Model.Monster.Action
import Model.Monster.ActionUsage
import Model.Monster.Monster
import ViewModel.Content.ContentEvent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun ActionsView(
    monster: Monster,
    onEvent: (ContentEvent) -> Unit,
    modifier: Modifier = Modifier
) {

    val actions = monster.actions

    LazyColumn(
        modifier = Modifier
            .fillMaxHeight()
            .padding(
                horizontal = 10.dp,
                vertical = 5.dp
            )
            .then(modifier)
        ,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {

        // action view header
        item {
            Text(
                text = "Actions",
                style = MaterialTheme.typography.h5
            )

            Spacer(Modifier.height(5.dp))
        }

        // list of actions
        items(actions) {
            actionItem(
                action = it,
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
fun actionItem(
    action: Action,
    onEvent: (ContentEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        color = MaterialTheme.colors.primary,
        shape = RoundedCornerShape(4.dp),
        elevation = 2.dp,
        enabled = action.damage.isNotEmpty(),
        onClick = { onEvent(ContentEvent.onClickAction(true, action)) }
    ) {
        Column(
            modifier = Modifier.padding(5.dp)
        ) {
            action.name?.let {
                Text(it, style = MaterialTheme.typography.subtitle1)
            }
            action.desc?.let {
                Text(it)
            }

            ActionUsageView(action.usage)
        }
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