package View

import Model.Monster.Sense
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
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
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(3.dp)
            .then(modifier),
        shape = RoundedCornerShape(3.dp),
        color = MaterialTheme.colors.primary,
        elevation = 2.dp
    ) {
        LazyColumn(
            modifier = Modifier
                .padding(2.dp)
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
}