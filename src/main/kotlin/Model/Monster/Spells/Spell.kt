package Model.Monster.Spells

import Model.Monster.Usage
import kotlinx.serialization.Serializable

@Serializable
data class Spell(
    val name : String? = null,
    val level : Int? = null,
    val notes : String? = null,
    val url : String? = null,
    val usage : Usage = Usage()
    //val usage : SpellUsage = SpellUsage()
)
