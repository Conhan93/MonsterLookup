package View

import Model.Monster.Speed
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.unit.dp


@Composable
fun SpeedView(speed: Speed, modifier: Modifier) {

    Column(
        modifier = modifier
            .padding(15.dp)
            .background(
                color = Color.Gray
            )
            .verticalScroll(rememberScrollState())
    ) {
        speed.walk?.let {
            Box {
                Text("Walking speed : ${speed.walk}")
            }
        }
        speed.burrow?.let {
            Box {
                Text("Burrowing speed : ${speed.burrow}")
            }
        }
        speed.climb?.let {
            Box {
                Text("Climbing speed : ${speed.climb}")
            }
        }
        speed.fly?.let {
            Box {
                Text("Flying speed : ${speed.fly}")
            }
        }
        speed.swim?.let {
            Box {
                Text("Swimming speed : ${speed.swim}")
            }
        }
    }
}