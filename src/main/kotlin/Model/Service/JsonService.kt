package Model.Service

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

interface JsonService

inline fun <reified T : Model.Data.Base.Base> decodeFromString(jsonText : String) : T? {
    val decoder = Json { ignoreUnknownKeys = true }

    return try {
        decoder.decodeFromString<T>(jsonText)
    } catch (e : Exception) {
        return null
    }
}