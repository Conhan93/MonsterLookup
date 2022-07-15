package View

import Model.Monster.Sense
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em


@Composable
fun SensesView(sense: Sense, modifier: Modifier = Modifier) {

    val textSize = 0.8.em

    @Composable
    fun TextCell(text : String, modifier: Modifier = Modifier) =
        Text(
            text = text,
            fontSize = textSize,
            modifier = modifier
        )


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
                TextCell("Passive : $it")
            }

            sense.blindsight?.let { TextCell("Blindsight: $it") }
            sense.darkvision?.let { TextCell("Darkvision: $it") }
            sense.tremorsense?.let { TextCell("Tremorsense: $it") }
            sense.truesight?.let { TextCell("Truesight: $it") }
        }
    }
}