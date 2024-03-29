package View.ContentView

import Model.Data.Spell.Spell

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text

import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList

import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


@Composable
fun SpellDetail(
    listOfSpells : SnapshotStateList<Spell>,
    modifier : Modifier = Modifier
) {

    backgroundSurface(modifier) {
        contentBackground {
            LazyColumn(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(10.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                item { Text(
                    text = "Spells",
                    style = MaterialTheme.typography.h5
                )
                }
                items(listOfSpells) {
                    SpellView(
                        spell = it,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun SpellView(
    spell: Spell,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = Modifier
            .then(modifier),
        shape = RoundedCornerShape(3.dp),
        color = MaterialTheme.colors.primary,
        elevation = 3.dp
    ) {
        Column(
            modifier = Modifier
                .padding(5.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            spell.name?.let { Text(text = it, style = MaterialTheme.typography.subtitle1) }

            if (spell.desc.isNotEmpty()) {
                Text(spell.desc.joinToString(separator = System.lineSeparator()))
            }
        }
    }
}

@Composable
private fun backgroundSurface(
    modifier: Modifier = Modifier,
    colour : Color = MaterialTheme.colors.background,
    elevation : Dp = 10.dp,
    content : @Composable () -> Unit
) = Surface(
    Modifier
        .then(modifier) // apply alignment from popup
        .padding(horizontal = 20.dp, vertical = 10.dp),
    color = colour,
    shape = RoundedCornerShape(15.dp),
    elevation = elevation
) { content() }

@Composable
private fun contentBackground(
    modifier: Modifier = Modifier,
    colour : Color = MaterialTheme.colors.primaryVariant,
    elevation : Dp = 10.dp,
    content: @Composable () -> Unit
) = Surface(
    modifier = Modifier
        .fillMaxWidth()
        .padding(10.dp)
        .then(modifier),
    color = colour,
    shape = RoundedCornerShape(15.dp),
    elevation = elevation
) { content() }