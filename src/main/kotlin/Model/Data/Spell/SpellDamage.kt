package Model.Data.Spell

import Model.Data.Base.APIReference
import kotlinx.serialization.Serializable

@Serializable
data class SpellDamage(
    val damage_type : APIReference = APIReference(),
    val damage_at_slot_level : Map<String, String> = mapOf()
)
