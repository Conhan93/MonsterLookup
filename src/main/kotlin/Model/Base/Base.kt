package Model.Base

import kotlinx.serialization.Serializable

@Serializable
open class Base(
    val name : String? = null,
    val index : String? = null,
    val url : String? = null
)