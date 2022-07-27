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
    val simplePrimaryBackground = Modifier.background(
        color = MaterialTheme.colors.primary,
        shape = RoundedCornerShape(3.dp)
    )
    val topRowHeight = 180.dp

    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(5.dp)
        .height(topRowHeight)
        .then(simplePrimaryBackground)
    ) {
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

@Composable
private fun BottomBar(monster: Monster) {

    val simplePrimaryBackground = Modifier.background(
        color = MaterialTheme.colors.primary,
        shape = RoundedCornerShape(3.dp)
    )


    Row(
        modifier = Modifier
            .fillMaxWidth(1f)
    ) {

        val elementWeight = Modifier.weight(1f, fill = true)

        ActionsView(monster, modifier = elementWeight
            .then(simplePrimaryBackground))
        SpecialAbilitiesView(monster, modifier = elementWeight
            .then(simplePrimaryBackground))

        if (monster.reactions.isNotEmpty())
            ReactionsView(monster, modifier = elementWeight
                .then(simplePrimaryBackground))

        // Sidebar divider
        Divider(
            color = Color.Gray,
            modifier = Modifier
                .fillMaxHeight()
                .padding(5.dp)
                .width(1.dp)

        )
        Sidebar(
            monster = monster,
            modifier = simplePrimaryBackground
        )
    }
}