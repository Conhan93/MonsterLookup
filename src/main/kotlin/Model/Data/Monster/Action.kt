package Model.Data.Monster

import Model.Data.Base.Choice
import kotlinx.serialization.Serializable

@Serializable
data class Action(
    val name : String? = null,
    val desc : String? = null,
    val options : Choice = Choice(),
    val attack_bonus : Int? = null,
    val dc : DC = DC(),
    val attacks : List<Attack> = listOf(),
    val damage : List<Damage> = listOf(),
    val usage : ActionUsage = ActionUsage()
)
