package Model.Monster

import kotlinx.serialization.Serializable

@Serializable
data class Spell(
    val name : String? = null,
    val level : Int? = null,
    val url : String? = null,
    val usage : Usage = Usage()
)
