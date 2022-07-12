package Model.Base

import Model.Monster.Ability
import kotlinx.serialization.Serializable

@Serializable
data class Choice(
    val choose : Int? = null,
    val type : String? = null,
    val from : List<Map<String, Ability>> = listOf()
    //val from : List<Map<String, APIReference>> = listOf()
)