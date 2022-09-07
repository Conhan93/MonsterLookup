package View

import Model.Monster.Monster
import Service.ContentService
import Service.ContentServiceException

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.unit.dp

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

import State.State
import Storage.ILocalStorage
import Util.formattedToReadable

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable


import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color

import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.unit.DpOffset
import java.util.*


@Composable
fun Search(
    state: MutableState<State?>,
    modifier : Modifier = Modifier
) {
    var name = mutableStateOf("")

    val padding = Modifier.padding(5.dp)

    // Coroutine scope for the http request
    val requestScope = CoroutineScope(Dispatchers.IO)

    fun performRequest(name : String) {
        requestScope.launch {
            try {
                val monster = ContentService.getMonsterService().getContentAsync(name)
                monster?.let { state.value = State.Content(monster = it as Monster) }
            } catch (e : ContentServiceException) {
                state.value = State.Error(e.message!! ,e)
            } catch (e : Exception) {
                state.value = State.Error(e.message ?: "Oops" ,e)
            }
        }
    }

    Row(modifier) {
        searchField(
            name = name,
            modifier = padding,
        ) { performRequest(name.value) }

        Spacer(Modifier.width(10.dp))

        searchButton(padding) { performRequest(name.value) }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun searchField(
    name: MutableState<String>,
    modifier: Modifier = Modifier,
    onEnterPressed: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var textFieldWidth by remember { mutableStateOf(0) }
    var isDismissed by remember { mutableStateOf(false) }

    val icon = Icons.Filled.ArrowDropDown

    val names = ILocalStorage
        .getMonsterNames()
        .filter { if(name.value.isNotEmpty()) it.contains(name.value, ignoreCase = true) else true }

    val textColour = MaterialTheme.colors.primaryVariant

    Column {
        OutlinedTextField(
            name.value,
            onValueChange = {
                // Update search name
                if (!it.contains("\n"))
                    name.value = it

                // Expand drop menu when typing
                if(expanded && name.value.isEmpty())
                    expanded = false
                if(name.value.isNotEmpty())
                    expanded = true
            },
            modifier = modifier
                .onKeyEvent {
                    if (it.key.equals(Key.Enter)) {
                        onEnterPressed()
                        return@onKeyEvent true
                    } else
                        true
                }
                .onSizeChanged { textFieldWidth = it.width },
            shape = CutCornerShape(5.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                backgroundColor = MaterialTheme.colors.secondaryVariant,
                focusedBorderColor = MaterialTheme.colors.secondaryVariant,
                textColor = textColour
            ),
            textStyle = MaterialTheme.typography.h6,
            placeholder = { searchText("Enter name of monster") },
            trailingIcon = {
                Icon(icon, "drop down", Modifier.clickable {
                    if(isDismissed.and(!expanded)) {
                        isDismissed = false
                        expanded = true
                    }

                    if(!isDismissed)
                        expanded = expanded.not()

                })
            }
        )
        DropdownMenu(
            expanded = expanded,
            focusable = false,
            onDismissRequest = {
                isDismissed = true
                expanded = false },
            modifier = Modifier
                .width(with(LocalDensity.current) { textFieldWidth.dp })
                .heightIn(max = 150.dp)
                .background(MaterialTheme.colors.secondaryVariant),
            offset = DpOffset(x = 5.dp, y = -10.dp),
            content = {
                names.forEach {
                    DropdownMenuItem(onClick = {
                        name.value = it.formattedToReadable()
                        expanded = false
                        onEnterPressed()
                    }) { searchText(it.formattedToReadable()) }
                }
            }
        )
    }
}

@Composable
private fun searchText(
    text : String,
    textColour : Color = MaterialTheme.colors.primaryVariant,
    style : TextStyle = MaterialTheme.typography.h6,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        color = textColour,
        style = style,
        modifier = modifier
    )
}

@Composable
fun searchButton(
    modifier: Modifier = Modifier,
    searchAction : () -> Unit,
) {
    Button(
        onClick =  { searchAction() },
        colors = ButtonDefaults.buttonColors(
            backgroundColor = MaterialTheme.colors.secondaryVariant
        ),
        modifier = modifier
            .padding(top = 3.dp)
            .height(IntrinsicSize.Min)
            .width(IntrinsicSize.Min)
    ) {
        Icon(
            imageVector = Icons.Filled.Search,
            contentDescription = "search button",
            tint = MaterialTheme.colors.background
        )
    }
}