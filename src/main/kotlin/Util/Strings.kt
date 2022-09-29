package Util

import androidx.compose.ui.text.capitalize
import java.util.*

/**
 *  Formats the string into a format expected by the api
 *
 *  Ex. Adult black dragon = adult-black-dragon
 */
fun String.formatSearchName() : String {
    return this
        .replace(' ', '-')
        .trim()
        .lowercase()
}

/**
 * Formats the string from the format expected by the api
 * into a more readable capitalised one.
 */
fun String.formattedToReadable() : String
    = this
        .replace('-', ' ')
        .replaceFirstChar {
            if (it.isLowerCase())
                it.titlecase(Locale.getDefault())
            else
                it.toString()
        }


/**
 * Returns a capitalised copy of the string
 */
fun String.capitalise(): String {
    return this.replaceFirstChar {
        if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
    }
}