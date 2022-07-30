package Model.Monster.Spells

import Model.Base.APIReference
import Model.Monster.Usage
import kotlinx.serialization.Serializable

@Serializable
data class SpellCasting(
    val ability : APIReference = APIReference(),
    val dc : Int? = null,
    val modifier : Int? = null,
    val components_required : List<String> = listOf(),
    val school : String? = null,
    val slots : Map<String, Int> = mapOf(),
    val spells : List<Spell> = listOf(),
    val usage : Usage = Usage()
)
