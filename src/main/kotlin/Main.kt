// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
import Service.MonsterContentService
import State.State
import Theme.AppTheme
import View.*
import View.Common.FullscreenPopUpEnabledApp

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
fun App(monsterService: MonsterContentService) {

    var appState = mutableStateOf<State?>(null)


    AppTheme(isDarkMode = false) {
        when (appState.value) {
            is State.Content -> DisplayMonster(
                state = appState,
                monsterService = monsterService
            )
            is State.Error -> Error(
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

    val monsterService = MonsterContentService()

    Window(
        title = "Monster Lookup",
        state = WindowState(
            width = 1300.dp,
            height = 600.dp
        ),
        icon = painterResource("icons8-dungeons-and-dragons-48.png"),
        onCloseRequest = ::exitApplication

    ) {
        FullscreenPopUpEnabledApp { App(monsterService) }
    }
}
