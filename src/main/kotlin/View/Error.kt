package View

import Model.Monster.Monster
import Service.MonsterService
import State.State
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun Error(
    error : State.Error,
    state : MutableState<State<Monster>?>,
    monsterService: MonsterService
) {
    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize(0.5f)
                    .background(
                        color = MaterialTheme.colors.primary,
                        shape = RoundedCornerShape(15.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = error.error.localizedMessage,
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .padding(vertical = 10.dp)
                )
                SearchInput(
                    state = state,
                    monsterService = monsterService,
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                )
            }
        }
    }
}