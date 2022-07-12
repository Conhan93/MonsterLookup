package Model.Monster

import Model.Base.Choice
import kotlinx.serialization.Serializable

@Serializable
data class Reaction(
    val name : String? = null,
    val desc : String? = null,
    val options : Choice = Choice(),
    val attack_bonus : Int? = null,
    val dc : DC = DC(),
    val attacks : List<Attack> = listOf(),
    val damage : List<Damage> = listOf()
)
