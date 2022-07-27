package View

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp


@Composable
fun SimpleTextList(
    label : String,
    items : List<String>,
    backgroundColour : Color,
    modifier: Modifier = Modifier
) {

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
        label = { listLabel(label) },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            backgroundColor = backgroundColour
        ),
    )
}

@Composable
private fun listLabel(label : String) {
    Text(
        text = label,
        style = TextStyle(
            color = MaterialTheme.colors.background,
            fontWeight = FontWeight.Bold,
            fontStyle = FontStyle.Italic
        )
    )
}