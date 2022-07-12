package Model.Monster

import Model.Base.APIReference
import  kotlinx.serialization.Serializable

@Serializable
data class Proficiency(
    val value : Int? = null,
    val proficiency : APIReference = APIReference()
)
