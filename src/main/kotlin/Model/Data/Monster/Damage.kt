package Model.Data.Monster

import Model.Data.Base.APIReference
import kotlinx.serialization.Serializable

@Serializable
data class Damage(
    val damage_type : APIReference = APIReference(),
    val damage_dice : String? = null
)
