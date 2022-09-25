package Model.Data.Monster

import kotlinx.serialization.Serializable

@Serializable
data class Attack(
    val name : String? = null,
    val dc : DC = DC(),
    val damage: Damage = Damage()
)
