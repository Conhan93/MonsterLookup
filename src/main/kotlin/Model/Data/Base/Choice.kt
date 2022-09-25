package Model.Data.Base

import Model.Data.Monster.Ability
import kotlinx.serialization.Serializable

@Serializable
data class Choice(
    val choose : Int? = null,
    val type : String? = null,
    val from : List<Map<String, Ability>> = listOf()
)