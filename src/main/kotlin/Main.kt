// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
import DI.contentModule
import DI.storageModule
import DI.viewModelModule
import View.State.State
import Theme.AppTheme
import View.*
import View.Common.FullscreenPopUpEnabledApp
import View.ContentView.ContentView
import ViewModel.Main.MainEvent
import ViewModel.Main.MainViewModel

import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.MenuBar
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application

import org.koin.core.context.startKoin
import org.koin.java.KoinJavaComponent.get

@Composable
fun App() {

    var appState by remember { mutableStateOf<State?>(null) }

    when (val state = appState) {
        State.Content -> ContentView { appState = it }
        is State.Error -> Error(state) { appState = it }
        is State.Loading -> Text("Loading animation")
        null -> Start() { appState = it }
    }
}

fun main() {
    startKoin { modules(contentModule, viewModelModule, storageModule) }

    val viewModel: MainViewModel = get(MainViewModel::class.java)

    application {

        Window(
            title = "Monster Lookup",
            state = WindowState(
                width = 1300.dp,
                height = 600.dp
            ),
            icon = painterResource("icons8-dungeons-and-dragons-48.png"),
            onCloseRequest = { viewModel.onEvent(MainEvent.ExitAppEvent(::exitApplication)) }

        ) {

            MenuBar {
                Menu("File") {
                    Item("Quit", onClick = { viewModel.onEvent(MainEvent.ExitAppEvent(::exitApplication))})
                    Item("Clear Cache", onClick = {viewModel.onEvent(MainEvent.ClearCacheEvent)})
                }
                Menu("Appearance") {
                    Item(viewModel.darkModeItemString, onClick = { viewModel.onEvent(MainEvent.SwitchLightModeEvent) })
                }
            }

            AppTheme(isDarkMode = viewModel.isDarkMode) {
                FullscreenPopUpEnabledApp { App() }
            }
        }
    }
}

