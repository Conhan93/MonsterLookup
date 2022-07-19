// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
import Model.Monster.Monster
import Service.MonsterService
import View.*
import View.InfoAndStats.Sidebar
import androidx.compose.material.MaterialTheme
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application

@Composable
@Preview
fun App(monsterService: MonsterService) {
    var monster = remember { monsterService.monster }


    MaterialTheme {
        when (state.value) {
            State.SUCCESS -> DisplayMonster(monster, monsterService)
            State.START -> Start(monsterService)
            State.ERROR -> Error(monsterService)
        }

    }
}

@Composable
fun DisplayMonster(monster : MutableState<Monster>,monsterService: MonsterService) {

    val topPadding = Modifier.padding(vertical = 5.dp)

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
                    SearchInput(monsterService)
                    CharacterInfo(monster.value)
                }
            }
            Column(
                modifier = Modifier
                    .padding(
                        horizontal = 5.dp,
                        vertical = 5.dp
                    ),
            ) {
                val elementWeight = Modifier.weight(1f, fill = false)

                SpeedView(
                    monster.value.speed,
                    elementWeight
                        .then(topPadding))
                SensesView(monster.value.senses, elementWeight)
            }

            SimpleTextList(
                label = "Vulnerabilities",
                items = monster.value.damage_vulnerabilities,
                modifier = topPadding
            )
            SimpleTextList(
                label = "Resistances",
                items = monster.value.damage_resistances,
                modifier = topPadding
            )
            SimpleTextList(
                label = "Immunities",
                items = monster.value.damage_immunities + monster
                    .value
                    .condition_immunities
                    .mapNotNull { it.name },
                modifier = topPadding
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

            if (monster.value.reactions.isNotEmpty())
                ReactionsView(monster, modifier = elementWeight)

            Sidebar(monster = monster)
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
