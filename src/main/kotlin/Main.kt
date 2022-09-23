// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
import DI.contentModule
import DI.storageModule
import DI.viewModelModule
import State.State
import Storage.ILocalStorage
import Theme.AppTheme
import View.*
import View.Common.FullscreenPopUpEnabledApp

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
        is State.Content -> ContentView(state.monster!!) { appState = it}
        is State.Error -> Error(state) { appState = it }
        is State.Loading -> Text("Loading animation")
        null -> Start() { appState = it }
    }
}

fun main() {
    startKoin { modules(contentModule, viewModelModule, storageModule) }

    application {

        val isDarkMode = remember { mutableStateOf(false) }

        Window(
            title = "Monster Lookup",
            state = WindowState(
                width = 1300.dp,
                height = 600.dp
            ),
            icon = painterResource("icons8-dungeons-and-dragons-48.png"),
            onCloseRequest = ::exitApplication

        ) {

            MenuBar {
                Menu("File") {
                    Item("Quit", onClick = { exitApplication() })
                    Item("Clear Cache", onClick = {
                        val storage : ILocalStorage = get(ILocalStorage::class.java)
                        storage.clear()
                    })
                }
                Menu("Appearance") {
                    val darkModeItemString = if (isDarkMode.value) "Light Mode" else "Dark Mode"
                    Item(darkModeItemString, onClick = { isDarkMode.value = isDarkMode.value.not() })
                }
            }

            AppTheme(isDarkMode = isDarkMode.value) {
                FullscreenPopUpEnabledApp { App() }
            }
        }
    }
}

