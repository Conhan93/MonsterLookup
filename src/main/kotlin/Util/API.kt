package Util

import Model.Monster.Monster
import Model.Spell.Spell
import kotlin.reflect.KClass

object API {
    const val baseURL = "https://www.dnd5eapi.co"
    const val baseAPI = "https://www.dnd5eapi.co/api"
    const val APIMonsterPath = "https://www.dnd5eapi.co/api/monsters/"
    const val APISpellsPath = "https://www.dnd5eapi.co/api/spells/"

    val paths : Map<KClass<*>, String> = mapOf(
        Spell::class to APISpellsPath,
        Monster::class to APIMonsterPath
    )
}