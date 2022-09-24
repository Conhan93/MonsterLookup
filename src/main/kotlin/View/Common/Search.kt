package View

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.unit.dp

import State.State
import Util.formattedToReadable
import ViewModel.Search.SearchEvent
import ViewModel.Search.SearchViewModel

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
import androidx.compose.ui.unit.DpOffset

import org.koin.java.KoinJavaComponent.get


@Composable
fun Search(
    onStateChange : (State) -> Unit,
    viewModel : SearchViewModel = get(SearchViewModel::class.java),
    modifier : Modifier = Modifier
) {

    val padding = Modifier.padding(5.dp)

    fun onSearchResult() {
        viewModel.onEvent(SearchEvent.onSearch { result ->
            if (result.isSuccess) {
                onStateChange(State.Content)
            } else {
                val message = result.exceptionOrNull()?.message
                onStateChange(State.Error(message!!, result.exceptionOrNull()))
            }
        })
    }

    Row(modifier) {
        searchField(
            name = viewModel.name,
            dropDownNames = viewModel.dropDownMenuNames,
            expanded = viewModel.isDropDownMenuExpanded,
            modifier = padding,
            onEvent = viewModel::onEvent,
            onSearch = ::onSearchResult
        )

        Spacer(Modifier.width(10.dp))

        if (!viewModel.isSearching) searchButton(padding, ::onSearchResult)
        else CircularProgressIndicator(padding.padding(top = 3.dp), MaterialTheme.colors.secondaryVariant)
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun searchField(
    name: String,
    dropDownNames : List<String>,
    expanded : Boolean,
    modifier: Modifier = Modifier,
    onEvent: (SearchEvent) -> Unit,
    onSearch: () -> Unit
) {
    var textFieldWidth by remember { mutableStateOf(0) }

    val icon = Icons.Filled.ArrowDropDown

    val textColour = MaterialTheme.colors.primaryVariant

    Column {
        OutlinedTextField(
            name,
            onValueChange = {
                // Update search name
                if (!it.contains("\n"))
                    onEvent(SearchEvent.onSetName(it))
            },
            modifier = modifier
                .onKeyEvent {
                    if (it.key.equals(Key.Enter)) {
                        onSearch()
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
                Icon(
                    icon,
                    "drop down",
                    Modifier.clickable { onEvent(SearchEvent.onDropDownMenuExpand(!expanded)) }
                )
            }
        )
        DropdownMenu(
            expanded = expanded,
            focusable = false,
            onDismissRequest = { onEvent(SearchEvent.onDropDownMenuExpand(false)) },
            modifier = Modifier
                .width(with(LocalDensity.current) { textFieldWidth.dp })
                .heightIn(max = 150.dp)
                .background(MaterialTheme.colors.secondaryVariant),
            offset = DpOffset(x = 5.dp, y = -10.dp),
            content = {
                dropDownNames.forEach {
                    DropdownMenuItem(onClick = {
                        onEvent(SearchEvent.onClickDropDownMenuItem(it.formattedToReadable()))
                        onSearch()
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
    onSearch : () -> Unit,
) {
    Button(
        onClick =  { onSearch() },
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