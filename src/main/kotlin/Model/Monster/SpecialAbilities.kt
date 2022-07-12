package Model.Monster

import kotlinx.serialization.Serializable

@Serializable
data class SpecialAbilities(
    val name : String? = null,
    val desc : String? = null,
    val attack_bonus : Int? = null,
    val damage : List<Damage> = listOf(),
    val dc : DC = DC(),
    val spellCasting: SpellCasting = SpellCasting(),
    val usage: Usage = Usage()

    )
