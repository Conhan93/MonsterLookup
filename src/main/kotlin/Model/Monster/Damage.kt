package Model.Monster

import Model.Base.APIReference
import kotlinx.serialization.Serializable

@Serializable
data class Damage(
    val damage_type : APIReference = APIReference(),
    val damage_dice : String? = null
)
