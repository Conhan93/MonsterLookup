package View

import Model.Monster.Speed
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em


@Composable
fun SpeedView(speed: Speed, modifier: Modifier = Modifier) {

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
                Text("Speeds")
            }

            item {
                speed.walk?.let { TextCell("Walking: $it") }
                speed.burrow?.let { TextCell("Burrowing: $it") }
                speed.swim?.let { TextCell("Swimming: $it") }
                speed.climb?.let { TextCell("Climbing: $it") }
                speed.fly?.let {TextCell("Flying: $it")}
            }
        }
    }
}