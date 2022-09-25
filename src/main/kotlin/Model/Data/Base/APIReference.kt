package Model.Data.Base

import kotlinx.serialization.Serializable

@Serializable
data class APIReference(
    val index : String? = null,
    val name : String? = null,
    val url : String? = null
)