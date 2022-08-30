package Storage

import Model.Monster.Monster
import Model.Spell.Spell

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class LocalStorageTests {

    // set local storage to use in memory storage for testing
    private val storageProperties = StorageProperties("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1", "org.h2.Driver")

    @Test
    fun `Should store spell`() {

        val testSpellFileName = "/acidarrow.txt"

        ILocalStorage.changeStorageProperties(storageProperties)

        val spellJsonString = this::class.java.getResource(testSpellFileName).readText()

        val json = Json { ignoreUnknownKeys = true }
        val testSpell = json.decodeFromString<Spell>(spellJsonString)

        ILocalStorage.store(testSpell)

        val storedSpell = ILocalStorage.getSpellByName(testSpell.name!!)

        assertEquals(testSpell, storedSpell)

    }

    @Test
    fun `Should store monster`() {
        val adultBlackDragonFileName = "/adultblackdragon.txt"
        val dragonJsonText = this::class.java.getResource(adultBlackDragonFileName).readText()

        val json = Json { ignoreUnknownKeys = true }

        val testDragon = json.decodeFromString<Monster>(dragonJsonText)
        ILocalStorage.changeStorageProperties(storageProperties)

        ILocalStorage.store(testDragon)

        val storedDragon = ILocalStorage.getMonsterByName(testDragon.name!!)

        assertEquals(testDragon, storedDragon)
    }

    @Test
    fun `Clearing database should reset the database`() {
        ILocalStorage.changeStorageProperties(storageProperties)

        val adultBlackDragonFileName = "/adultblackdragon.txt"
        val dragonJsonText = this::class.java.getResource(adultBlackDragonFileName).readText()

        val json = Json { ignoreUnknownKeys = true }
        val testDragon = json.decodeFromString<Monster>(dragonJsonText)

        ILocalStorage.store(testDragon)

        ILocalStorage.clear()

        val storedDragon = ILocalStorage.getMonsterByName(testDragon.name!!)

        assertEquals(null, storedDragon)
    }

    @Test
    fun `Should return null on item not in database`() {
        ILocalStorage.changeStorageProperties(storageProperties)

        ILocalStorage.clear()

        val result = ILocalStorage.getMonsterByName("adult-black-dragon")

        assertEquals(null, result)
    }
}