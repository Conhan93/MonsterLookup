package Storage

import Model.Monster.Monster
import Model.Spell.Spell
import TestHelper.Resource.LoadTestResource
import TestHelper.Resource.getTestResource

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

internal class LocalStorageTests {

    // set local storage to use in memory storage for testing
    private val storageProperties = StorageProperties("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1", "org.h2.Driver")


    @BeforeEach
    fun before() {
        ILocalStorage.changeStorageProperties(storageProperties)
    }
    @Test
    fun `Should store spell`() {

        val testSpell = getTestResource(LoadTestResource.acidArrow) as Spell

        ILocalStorage.store(testSpell)

        val storedSpell = ILocalStorage.getSpellByName(testSpell.name!!)

        assertEquals(testSpell, storedSpell)

    }

    @Test
    fun `Should store monster`() {

        val testDragon = getTestResource(LoadTestResource.blackDragon) as Monster

        ILocalStorage.store(testDragon)

        val storedDragon = ILocalStorage.getMonsterByName(testDragon.name!!)

        assertEquals(testDragon, storedDragon)
    }

    @Test
    fun `Clearing database should reset the database`() {

        val testDragon = getTestResource(LoadTestResource.blackDragon) as Monster

        ILocalStorage.store(testDragon)

        ILocalStorage.clear()

        val storedDragon = ILocalStorage.getMonsterByName(testDragon.name!!)

        assertEquals(null, storedDragon)

        val namesInDatabase = ILocalStorage.getMonsterNames()

        assert(namesInDatabase.isEmpty())
    }

    @Test
    fun `Should return null on item not in database`() {

        ILocalStorage.clear()

        val result = ILocalStorage.getMonsterByName("adult-black-dragon")

        assertEquals(null, result)
    }

    @Test
    fun `getByName should return monster`() {

        val testMonster = getTestResource(LoadTestResource.blackDragon) as Monster

        ILocalStorage.store(testMonster)

        val result = ILocalStorage.getByName(testMonster.name!!, Monster::class)

        assertNotNull(result, "getByName unable to find inserted monster")
        assertEquals(testMonster, result)

    }

    @Test
    fun `getByName should return spell`() {

        val testSpell = getTestResource(LoadTestResource.acidArrow) as Spell

        ILocalStorage.store(testSpell)

        val result = ILocalStorage.getByName(testSpell.name!!, Spell::class)

        assertNotNull(result, "getByName unable to find inserted spell")
        assertEquals(testSpell, result)
    }

    @Test
    fun `getByName should return null on invalid class parameter`() {
        val testSpell = getTestResource(LoadTestResource.acidArrow) as Spell

        ILocalStorage.store(testSpell)

        val result = ILocalStorage.getByName(testSpell.name!!, object {}::class)

        assertNull(result)
    }

    @Test
    fun `getByName should return null on no result`() {
        ILocalStorage.clear()

        val testSpell = getTestResource(LoadTestResource.acidArrow) as Spell
        ILocalStorage.store(testSpell)

        val result = ILocalStorage.getByName(testSpell.name!! + "foo", Spell::class)

        assertNull(result)
    }
}