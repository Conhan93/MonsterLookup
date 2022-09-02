package Model.Spell

import Model.Base.APIReference
import Model.Base.Base
import kotlinx.serialization.Serializable

@Serializable
data class Spell(
    val _id : String? = null,
    val desc : List<String> = listOf(),

    val higher_level : List<String> = listOf(),
    val range : String? = null,
    val components : List<String> = listOf(),
    val material : String? = null,
    val ritual : Boolean? = null,
    val duration : String? = null,
    val concentration : Boolean? = null,
    val casting_time : String? = null,
    val level : Int? = null,
    val attack_type : String? = null,

    val damage: SpellDamage = SpellDamage(),
    val school : APIReference = APIReference(),
    val classes : List<APIReference> = listOf(),
    val subclasses : List<APIReference> = listOf(),
) : Base() {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Spell

        if (desc != other.desc) return false
        if (higher_level != other.higher_level) return false
        if (range != other.range) return false
        if (components != other.components) return false
        if (material != other.material) return false
        if (ritual != other.ritual) return false
        if (duration != other.duration) return false
        if (concentration != other.concentration) return false
        if (casting_time != other.casting_time) return false
        if (level != other.level) return false
        if (attack_type != other.attack_type) return false
        if (damage != other.damage) return false
        if (school != other.school) return false
        if (classes != other.classes) return false
        if (subclasses != other.subclasses) return false

        return true
    }

    override fun hashCode(): Int {
        var result = desc.hashCode()
        result = 31 * result + higher_level.hashCode()
        result = 31 * result + (range?.hashCode() ?: 0)
        result = 31 * result + components.hashCode()
        result = 31 * result + (material?.hashCode() ?: 0)
        result = 31 * result + (ritual?.hashCode() ?: 0)
        result = 31 * result + (duration?.hashCode() ?: 0)
        result = 31 * result + (concentration?.hashCode() ?: 0)
        result = 31 * result + (casting_time?.hashCode() ?: 0)
        result = 31 * result + (level ?: 0)
        result = 31 * result + (attack_type?.hashCode() ?: 0)
        result = 31 * result + damage.hashCode()
        result = 31 * result + school.hashCode()
        result = 31 * result + classes.hashCode()
        result = 31 * result + subclasses.hashCode()
        return result
    }
}