// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
import Model.Monster.Monster
import Service.MonsterService
import State.State
import Theme.darkColours
import View.*
import androidx.compose.material.MaterialTheme
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application

@Composable
@Preview
fun App(monsterService: MonsterService) {

    var appState = mutableStateOf<State<Monster>?>(null)


    MaterialTheme(
        colors = darkColours
    ) {
        when (val state = appState.value) {
            is State.Content -> DisplayMonster(
                monster = state.data,
                state = appState,
                monsterService = monsterService
            )
            is State.Error -> Error(
                error = state,
                state = appState,
                monsterService = monsterService
                )
            is State.Loading -> Text("Loading animation")
            null -> Start(
                state = appState,
                monsterService = monsterService
            )
        }

    }
}

fun main() = application {

    val monsterService = MonsterService()

    Window(
        title = "Monster Lookup",
        state = WindowState(
            width = 1300.dp,
            height = 600.dp
        ),
        icon = painterResource("icons8-dungeons-and-dragons-48.png"),
        onCloseRequest = ::exitApplication

    ) {
        App(monsterService)
    }
}
