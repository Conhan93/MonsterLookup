package View

import Model.Monster.Monster
import Service.MonsterService
import State.State
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun Start(
    state : MutableState<State<Monster>?>,
    monsterService : MonsterService
) {
    Box(
        modifier = Modifier
            .fillMaxSize(1f)
            .background(
                color = Color.LightGray
            ),
        contentAlignment = Alignment.Center
    ) {
        SearchInput(
            state = state,
            monsterService = monsterService
        )
    }

}