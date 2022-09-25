package Model.Data.Monster

import kotlinx.serialization.Serializable

@Serializable
data class Sense(
    val passive_perception : Int? = null,
    val blindsight : String? = null,
    val darkvision : String? = null,
    val tremorsense : String? = null,
    val truesight : String? = null
)
