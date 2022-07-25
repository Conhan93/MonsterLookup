package View

import Model.Monster.Monster
import Service.MonsterService
import State.State
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun Error(
    error : State.Error,
    state : MutableState<State<Monster>?>,
    monsterService: MonsterService
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = Color.LightGray
            ),
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        //Text(state.message ?: "N/A")
        Text(error.error.localizedMessage)
        SearchInput(
            state = state,
            monsterService = monsterService
        )
    }
}