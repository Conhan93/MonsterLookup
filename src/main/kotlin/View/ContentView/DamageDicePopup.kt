package View.ContentView

import Model.Util.DamageRoll
import Model.Util.HitRoll
import Model.Util.ItemRoll
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


@Composable
fun DamageDicePopUp(
    damageRoll: ItemRoll<DamageRoll>,
    modifier: Modifier = Modifier
) {
    backgroundSurface(modifier) {
        contentBackground {
            LazyColumn(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(10.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                item {
                    Text(
                        text = "DamageRolls",
                        style = MaterialTheme.typography.h5
                    )
                }

                item {
                    Column(
                        modifier = Modifier
                            .padding(5.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(text = damageRoll.itemName, style = MaterialTheme.typography.subtitle1)
                        Text(damageRoll.itemDescr)
                    }
                }

                item {
                    damageRollColumn(damageRoll.rolls, damageRoll.hitRoll!!, modifier)
                }
            }
        }
    }
}

@Composable
private fun damageRollColumn(
    rolls: List<DamageRoll>,
    hitRoll: HitRoll,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = Modifier
            .then(modifier),
        shape = RoundedCornerShape(3.dp),
        color = MaterialTheme.colors.primary,
        elevation = 3.dp
    ) {
        Column(Modifier.fillMaxWidth()) {
            Row(
                Modifier.fillMaxWidth().padding(start = 3.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                val textPadding = Modifier.padding(3.dp)
                Text(
                    text = "Results",
                    style = MaterialTheme.typography.subtitle1,
                    modifier = textPadding
                )
                Text(
                    text = "Hit Roll: ${hitRoll.roll}, total: ${hitRoll.total}",
                    style = MaterialTheme.typography.subtitle1,
                    modifier = textPadding.padding(end = 10.dp)
                )
            }

            Divider(Modifier
                .fillMaxWidth()
                .padding(5.dp)
                .width(1.dp),
                color = MaterialTheme.colors.background
            )
            rolls.forEach {
                Row(
                    modifier = Modifier
                        .padding(5.dp),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(text = it.damageType, style = MaterialTheme.typography.subtitle1)
                    Text(it.damage.toString())
                }
            }
        }
    }
}


@Composable
private fun backgroundSurface(
    modifier: Modifier = Modifier,
    colour : Color = MaterialTheme.colors.background,
    elevation : Dp = 10.dp,
    content : @Composable () -> Unit
) = Surface(
    Modifier
        .then(modifier) // apply alignment from popup
        .padding(horizontal = 20.dp, vertical = 10.dp),
    color = colour,
    shape = RoundedCornerShape(15.dp),
    elevation = elevation
) { content() }

@Composable
private fun contentBackground(
    modifier: Modifier = Modifier,
    colour : Color = MaterialTheme.colors.primaryVariant,
    elevation : Dp = 10.dp,
    content: @Composable () -> Unit
) = Surface(
    modifier = Modifier
        .fillMaxWidth()
        .padding(10.dp)
        .then(modifier),
    color = colour,
    shape = RoundedCornerShape(15.dp),
    elevation = elevation
) { content() }