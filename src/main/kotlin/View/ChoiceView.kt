package View

import Model.Base.Choice
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun ChoiceView(choice: Choice, modifier: Modifier) {

    Column(
        modifier = modifier
    ) {

        choice.type?.let {
            Text("Type : $it")
        }

        choice.choose?.let {
            Text("Choose ${choice.choose} From")
        }


        choice.from.forEach {
            it.forEach {

                Column(
                    modifier = modifier
                        .padding(horizontal = 3.dp)
                ) {
                    Text(it.key)

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(5.dp)
                    ) {
                        it.value.name?.let { it1 -> Text(it1) }

                        it.value.count?.let { it2 -> Text("Count : $it2") }
                    }

                    it.value.type?.let {it3 -> Text("Type : $it3") }
                }
            }
        }
    }

}