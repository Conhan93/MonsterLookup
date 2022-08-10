package Service

import Model.Base.APIReference
import Model.Spell.Spell
import State.State
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

internal class SpellContentServiceTest {

    val testSpellFileName = "/acidarrow.txt"

    @Test
    fun `Get spell content returns spell`() {

        val service : ContentService = SpellContentService()

        val actual = service.getContent("acid-arrow") as State.Content

        val spellJsonString = this::class.java.getResource(testSpellFileName).readText()
        val json = Json { ignoreUnknownKeys = true }

        val expected = json.decodeFromString<Spell>(spellJsonString)

        assertEquals(expected, actual.spell)
    }

    @Test
    fun `Get content API Reference returns spell`() {
        val apiReference = APIReference(
            index = "acid-arrow",
            name = "Acid Arrow",
            url = "/api/spells/acid-arrow"
        )

        val service : ContentService = SpellContentService()

        val spellJsonText = this::class.java.getResource(testSpellFileName).readText()

        val json = Json { ignoreUnknownKeys = true }

        val expected = json.decodeFromString<Spell>(spellJsonText)

        val actual = service.getContent(apiReference) as State.Content

        assertEquals(expected, actual.spell)

    }

    @Test
    fun `Should throw connection error on client send throw`() {
        val mockClient = Mockito.mock(HttpClient::class.java)

        Mockito.`when`(
            mockClient.send(
                Mockito.any(HttpRequest::class.java),
                Mockito.eq(HttpResponse.BodyHandlers.ofString())
            )
        )
            .thenThrow(InterruptedException("foo"))

        val service = SpellContentService(client = mockClient)

        assertThrows<ContentServiceException.ConnectionException> {
            service.getContent("foo")
        }
    }
}