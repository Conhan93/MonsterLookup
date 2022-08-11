package View

import Model.Monster.Monster
import Service.MonsterContentService
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
    state: MutableState<State?>,
    monsterService: MonsterContentService
) {

    val content = state.value as State.Content
    val monster = content.monster!!

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
    state: MutableState<State?>,
    monsterService: MonsterContentService
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
                    Search(
                        state = state,
                        monsterService = monsterService
                    )
                    CharacterInfo(monster)
                }


                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(140.dp)
                        .padding(
                            start = 5.dp,
                            end = 5.dp,
                            bottom = 10.dp
                        ),
                    verticalArrangement = Arrangement.SpaceAround
                ) {
                    val elementWeight = Modifier.weight(1f, fill = true)

                    SpeedView(
                        monster.speed,
                        elementWeight
                            .then(topPadding)
                    )
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