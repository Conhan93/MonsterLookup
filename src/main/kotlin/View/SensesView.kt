package View

import Model.Monster.Sense
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun SensesView(sense: Sense, modifier: Modifier) {

    LazyColumn(
        modifier = Modifier
            .then(modifier)
    ) {

        item {
            Text("Senses")
            Spacer(Modifier.height(5.dp))
        }
        item {
            sense.passive_perception?.let {
                Text("Passive : $it")
            }

            sense.blindsight?.let { Text(it) }
            sense.darkvision?.let { Text(it) }
            sense.tremorsense?.let { Text(it) }
            sense.truesight?.let { Text(it) }
        }
    }
}