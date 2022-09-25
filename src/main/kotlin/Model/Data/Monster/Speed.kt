package Model.Data.Monster

import kotlinx.serialization.Serializable

@Serializable
data class Speed(
    val walk : String? = null,
    val burrow : String? = null,
    val climb : String? = null,
    val fly : String? = null,
    val swim : String? = null
)
