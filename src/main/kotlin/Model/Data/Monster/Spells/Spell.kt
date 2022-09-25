package Model.Data.Monster.Spells

import Model.Data.Monster.Usage
import kotlinx.serialization.Serializable

@Serializable
data class Spell(
    val name : String? = null,
    val level : Int? = null,
    val notes : String? = null,
    val url : String? = null,
    val usage : Usage = Usage()
)
