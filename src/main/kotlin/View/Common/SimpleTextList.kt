package View

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


@Composable
fun SimpleTextList(label : String, items : List<String>, modifier: Modifier = Modifier) {

    val textBody =
        if(items.isEmpty())
            "No $label"
        else
            items.joinToString(separator = System.lineSeparator())

    OutlinedTextField(
        textBody,
        onValueChange = {},
        modifier = Modifier
            .padding(5.dp)
            .clip(CutCornerShape(5.dp))
            .then(modifier),
        shape = CutCornerShape(5.dp),
        readOnly = true,
        label = { Text(label) },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            backgroundColor = Color.Gray
        ),
    )
}