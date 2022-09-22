package View

import Model.Monster.Monster
import State.State
import View.InfoAndStats.Conditions
import View.InfoAndStats.Sidebar
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ContentView(
    monster: Monster,
    onStateChange : (State) -> Unit,
) {


    Surface {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top
        ) {
            TopBar(
                monster = monster,
                onStateChange = onStateChange
            )
            Divider(
                color = Color.Gray,
                thickness = 1.dp,
                modifier = Modifier
                    .padding(
                        vertical = 10.dp,
                        horizontal = 10.dp
                    )
                    .fillMaxWidth(1f)
            )

            BottomBar(monster)
        }
    }
}

@Composable
private fun TopBar(
    monster : Monster,
    onStateChange : (State) -> Unit,
) {
    val topPadding = Modifier.padding(vertical = 5.dp)
    val topBarHeight = Modifier.height(195.dp)

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp, horizontal = 10.dp)
            .then(topBarHeight),
        color = MaterialTheme.colors.primaryVariant,
        shape = RoundedCornerShape(3.dp),
        elevation = 1.dp
    ) {
        Row {
                Column {
                    Search(onStateChange)
                    CharacterInfo(monster)
                }


            Conditions(
                monster = monster,
                modifier = topPadding.padding(start = 10.dp)
            )
        }
    }
}

@Composable
private fun BottomBar(monster: Monster) {

    @Composable
    fun bottomSurface(
        modifier: Modifier = Modifier,
        content : @Composable () -> Unit
    ) = Surface(
            modifier = modifier,
            color = MaterialTheme.colors.surface,
            shape = RoundedCornerShape(3.dp),
            elevation = 10.dp
        ) {
            content()
        }


    Row(
        modifier = Modifier
            .fillMaxWidth(1f)
    ) {

        val elementModifier = Modifier
            .weight(1f, fill = true)
            .padding(10.dp)

        bottomSurface(elementModifier) {
            ActionsView(monster)
        }

        bottomSurface(elementModifier) {
            SpecialAbilitiesView(monster)
        }

        if (monster.reactions.isNotEmpty())
            bottomSurface(elementModifier) {
                ReactionsView(monster)
            }

        // Sidebar divider
        Divider(
            color = Color.Gray,
            modifier = Modifier
                .fillMaxHeight()
                .padding(5.dp)
                .width(1.dp)

        )
        bottomSurface(Modifier
            .weight(0.6f, fill = true)
            .padding(10.dp)) {
            Sidebar(monster)
        }
    }
}