package Model.Monster

import kotlinx.serialization.Serializable

@Serializable
data class Usage(
    val type : String? = null,
    val rest_types : List<String> = listOf(),
    val times : Int? = null
)
