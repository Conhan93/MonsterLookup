package Model.Data.Monster

import Model.Data.Base.APIReference
import kotlinx.serialization.Serializable

@Serializable
data class ArmorClass(
    val type: String,
    val value: Int,
    val armor: List<APIReference>
)
