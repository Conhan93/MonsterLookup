package TestHelper.Resource

import Model.Base.Base
import Model.Monster.Monster
import Model.Spell.Spell
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

sealed class LoadTestResource {
    object acidArrow : LoadTestResource()
    object blackDragon : LoadTestResource()
}


fun getTestResource(resource : LoadTestResource) : Base {

    val fileName = when(resource) {
        LoadTestResource.acidArrow -> "/acidarrow.txt"
        LoadTestResource.blackDragon -> "/adultblackdragon.txt"
    }

    val fileText = object {}::class.java.getResource(fileName).readText()

    val json = Json { ignoreUnknownKeys = true }

    return when(resource) {
        LoadTestResource.acidArrow -> json.decodeFromString<Spell>(fileText)
        LoadTestResource.blackDragon -> json.decodeFromString<Monster>(fileText)
    }
}
