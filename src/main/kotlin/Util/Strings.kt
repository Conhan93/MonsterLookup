package Util

/**
 *  Formats the string into a format expected by the api
 *
 *  Ex. Adult black dragon = adult-black-dragon
 */
fun String.formatSearchName() : String {
    return this.replace(' ', '-')
        .trim()
        .lowercase()
}