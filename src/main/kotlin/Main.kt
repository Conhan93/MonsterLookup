// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
import Service.MonsterService
import View.*
import androidx.compose.material.MaterialTheme
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

@Composable
@Preview
fun App(monsterService: MonsterService) {
    //var text by remember { mutableStateOf("Hello, World!") }

    var monster = remember { monsterService.monster }


    MaterialTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.DarkGray),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top
        ) {
            Row {
                Box() {
                    Column {
                        SearchInput(
                            monsterService,
                            Modifier.background(Color.DarkGray)
                        )
                        CharacterInfo(
                            monster.value,
                            Modifier.background(Color.DarkGray)
                        )
                    }
                }
                SpeedView(
                    monster.value.speed,
                    modifier = Modifier.background(Color.DarkGray)
                )
            }
            Row {
                ActionsView(monster, modifier = Modifier.background(Color.DarkGray))
                SpecialAbilitiesView(monster, modifier = Modifier.background(Color.DarkGray))
            }

        }

    }
}

fun main() = application {

    val monsterService = MonsterService()

    Window(onCloseRequest = ::exitApplication) {
        App(monsterService)
    }
}
