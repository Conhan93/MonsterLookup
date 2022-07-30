package Model.Monster

import kotlinx.serialization.Serializable

@Serializable
data class ActionUsage(
    val type : String? = null,
    val dice : String? = null,
    val min_value : Int? = null,
    val rest_types : List<String> = listOf(),
    val times : Int? = null
)
