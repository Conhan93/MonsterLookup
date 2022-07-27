package View

import Model.Monster.Monster
import Service.MonsterService
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
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun DisplayMonster(
    monster : Monster,
    state: MutableState<State<Monster>?>,
    monsterService: MonsterService
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
                state = state,
                monsterService = monsterService
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
    state: MutableState<State<Monster>?>,
    monsterService: MonsterService
) {
    val topPadding = Modifier.padding(vertical = 5.dp)

    Surface(
        modifier = Modifier
            .height(180.dp)
            .fillMaxWidth()
            .padding(5.dp),
        color = MaterialTheme.colors.primaryVariant,
        shape = RoundedCornerShape(3.dp),
        elevation = 1.dp
    ) {
        Row {
            Box {
                Column {
                    SearchInput(
                        state = state,
                        monsterService = monsterService
                    )
                    CharacterInfo(monster)
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(
                        horizontal = 5.dp,
                        vertical = 5.dp
                    ),
                verticalArrangement = Arrangement.SpaceAround
            ) {
                val elementWeight = Modifier.weight(1f, fill = false)

                SpeedView(
                    monster.speed,
                    elementWeight
                        .then(topPadding))
                SensesView(monster.senses, elementWeight)
            }

            Conditions(
                monster = monster,
                modifier = topPadding
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
            color = MaterialTheme.colors.primaryVariant,
            shape = RoundedCornerShape(3.dp),
            elevation = 1.dp
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