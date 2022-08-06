import Model.Spell.Spell
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.junit.jupiter.api.Test

internal class SpellTests {

    val testSpellFileName = "/acidarrow.txt"

    @Test
    fun `Deserialize Spell`() {

        val spellJsontxt = this::class.java.getResource(testSpellFileName).readText()

        org.junit.jupiter.api.assertDoesNotThrow {
            Json.decodeFromString<Spell>(spellJsontxt)
        }
    }
}