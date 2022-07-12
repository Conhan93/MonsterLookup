package Model.Monster

import Model.Base.APIReference
import kotlinx.serialization.Serializable

@Serializable
data class Monster(
    val index : String? = null,
    val name : String? = null,
    val url : String? = null,
    val desc : List<String> = listOf(),
    val charisma : Int? = null,
    val constitution : Int? = null,
    val dexterity : Int? = null,
    val intelligence : Int? = null,
    val strength : Int? = null,
    val wisdom : Int? = null,
    val size : String? = null,
    val type : String? = null,
    val subtype : String? = null,
    val alignment : String? = null,
    val armor_class : Int? = null,
    val hit_points : Int? = null,
    val hit_dice : String? = null,

    val actions : List<Action> = listOf(),
    val legendary_actions : List<Action> = listOf(),

    val challenge_rating : Float? = null,

    val condition_immunities : List<APIReference> = listOf(),
    val damage_immunities : List<String> = listOf(),
    val damage_resistances : List<String> = listOf(),
    val damage_vulnerabilities : List<String> = listOf(),
    val forms : List<APIReference> = listOf(),

    val languages : String? = null,
    val proficiencies : List<Proficiency> = listOf(),
    val reactions : List<Reaction> = listOf(),

    val senses : Sense = Sense(),

    val special_abilities : List<SpecialAbilities> = listOf(),

    val speed : Speed = Speed(),
    val xp : Int? = null
)
