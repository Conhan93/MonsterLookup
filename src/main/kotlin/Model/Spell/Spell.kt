package Model.Spell

import Model.Base.APIReference
import Model.Base.Base
import Storage.StorageMetaData
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
) : Base()