// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
import Service.MonsterService
import View.*
import androidx.compose.material.MaterialTheme
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
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
                Column(
                    modifier = Modifier
                        .padding(horizontal = 5.dp)
                ) {
                    SpeedView(monster.value.speed)
                    SensesView(monster.value.senses)
                }

                SimpleTextList(
                    label = "Vulnerabilities",
                    items = monster.value.damage_vulnerabilities,
                    modifier = Modifier.padding(vertical = 5.dp)
                )
                SimpleTextList(
                    label = "Resistances",
                    items = monster.value.damage_resistances,
                    modifier = Modifier.padding(vertical = 5.dp)
                )
                SimpleTextList(
                    label = "Immunities",
                    items = monster.value.damage_immunities + monster
                        .value
                        .condition_immunities
                        .mapNotNull { it.name },
                    modifier = Modifier.padding(vertical = 5.dp)
                )
            }
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
            Row(
                modifier = Modifier
                    .fillMaxWidth(1f),
            ) {

                val elementWeight = Modifier.weight(1f, fill = true)

                ActionsView(monster, modifier = elementWeight)
                SpecialAbilitiesView(monster, modifier = elementWeight)
                if(monster.value.reactions.isNotEmpty())
                    ReactionsView(monster, modifier = elementWeight)
            }

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
        onCloseRequest = ::exitApplication

    ) {
        App(monsterService)
    }
}
