package Model.Data.Monster

import kotlinx.serialization.Serializable

@Serializable
data class Ability(
    val name : String? = null,
    val count : Int? = null,
    val type : String? = null
)
