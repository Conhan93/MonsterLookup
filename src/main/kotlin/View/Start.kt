package View

import Model.Monster.Monster
import Service.MonsterContentService
import State.State
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun Start(
    state : MutableState<State<Monster>?>,
    monsterService : MonsterContentService
) {
    startBackground {
        startBox {
            Search(
                state = state,
                monsterService = monsterService
            )
        }
    }
}

@Composable
fun startBox(
    modifier : Modifier = Modifier,
    content : @Composable BoxScope.() -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxSize(0.5f)
            .then(modifier),
        color = MaterialTheme.colors.primary,
        shape = RoundedCornerShape(15.dp),
        elevation = 50.dp
    ) {
        Box(contentAlignment = Alignment.Center) {
            content()
        }
    }

}

@Composable
fun startBackground(
    modifier: Modifier = Modifier,
    content : @Composable () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .then(modifier),
        color = MaterialTheme.colors.background
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            content()
        }
    }
}