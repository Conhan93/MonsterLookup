package Model.Monster

import Model.Base.APIReference
import kotlinx.serialization.Serializable

@Serializable
data class DC(
    val dc_type : APIReference = APIReference(),
    val dc_value : Int? = null,
    val success_type : String? = null
)
