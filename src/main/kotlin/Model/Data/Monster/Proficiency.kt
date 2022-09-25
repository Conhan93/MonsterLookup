package Model.Data.Monster

import Model.Data.Base.APIReference
import  kotlinx.serialization.Serializable

@Serializable
data class Proficiency(
    val value : Int? = null,
    val proficiency : APIReference = APIReference()
)
