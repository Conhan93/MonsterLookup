package Service

import Model.Base.Base
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

interface JsonService

inline fun <reified T : Base> JsonService.decodeFromString(jsonText : String) : T? {
    val decoder = Json { ignoreUnknownKeys = true }

    return try {
        decoder.decodeFromString<T>(jsonText)
    } catch (e : Exception) {
        return null
    }
}