package Service

import Model.Spell.Spell
import State.State
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.mockito.Mockito
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

internal class SpellContentServiceTest {

    val testSpellFileName = "/acidarrow.txt"

    @Test
    fun `Get spell content returns spell`() {

        val service = SpellContentService()

        val actual = service.getContent("acid-arrow") as State.Content

        val spellJsonString = this::class.java.getResource(testSpellFileName).readText()
        val json = Json { ignoreUnknownKeys = true }

        val expected = json.decodeFromString<Spell>(spellJsonString)

        assertEquals(expected, actual.data)
    }

    @Test
    fun `Should return state with connection error on client send throw`() {
        val mockClient = Mockito.mock(HttpClient::class.java)

        Mockito.`when`(
            mockClient.send(
                Mockito.any(HttpRequest::class.java),
                Mockito.eq(HttpResponse.BodyHandlers.ofString())
            )
        )
            .thenThrow(InterruptedException("foo"))

        val service = SpellContentService(client = mockClient)

        val state = service.getContent("foo")

        assertTrue(state is State.Error && state.error is ConnectionException)
    }
}