package View.Common

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CutCornerShape

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

/**
 *  Creates a list with a label or header above a solid body text
 *  list, where the body has a solid background with a clipped corner
 *  shape. label is drawn above the body ontop of the underlying
 *  composable.
 *
 * @param label composable that'll form the header or label of the list
 * @param textItems list of strings that'll form the body of the list text
 * @param textColour colour of the text in the body of the lsit
 * @param backgroundColour colour of the background of the body of the list
 * @param modifier modifier applied to a column wrapping the composable
 */
@Composable
fun SolidTextListLazy(
    label : @Composable () -> Unit,
    textItems : List<String>,
    textColour : Color = MaterialTheme.colors.onSurface,
    backgroundColour : Color = MaterialTheme.colors.surface,
    modifier : Modifier = Modifier
) {

    Column(modifier) {
        label()

        Spacer(Modifier.padding(vertical = 5.dp))

        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp),
            color = backgroundColour,
            shape = CutCornerShape(5.dp)
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp),
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                if(textItems.isNotEmpty())
                    items(textItems) {
                        Text(
                            text = it.capitalize(),
                            color = textColour,
                        )
                    }
                else
                    item {
                        Row {
                            Text("No")
                            Spacer(Modifier.padding(horizontal = 3.dp))
                            label()
                        }
                    }
            }
        }
    }
}

@Composable
fun SolidTextList(
    label : @Composable () -> Unit,
    textItems : List<String>,
    textColour : Color = MaterialTheme.colors.onSurface,
    backgroundColour : Color = MaterialTheme.colors.surface,
    modifier : Modifier = Modifier
) {

    Column(modifier) {
        label()

        Spacer(Modifier.padding(vertical = 5.dp))

        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp),
            color = backgroundColour,
            shape = CutCornerShape(5.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp),
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                if (textItems.isEmpty()) {
                    Text("No")
                    Spacer(Modifier.padding(horizontal = 3.dp))
                    label()
                }
                else
                    textItems.forEach {
                        Text(
                            text = it.capitalize(),
                            color = textColour,
                        )
                    }
            }
        }
    }
}