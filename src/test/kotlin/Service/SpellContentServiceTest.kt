package Service

import Model.Base.APIReference
import Model.Spell.Spell
import State.State
import Storage.ILocalStorage
import kotlinx.coroutines.*

import kotlinx.coroutines.test.runTest
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.assertThrows

import org.mockito.Mockito

import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
@OptIn(ExperimentalCoroutinesApi::class)
internal class SpellContentServiceTest {

    val testSpellFileName = "/acidarrow.txt"

    val storage = Mockito.mock(ILocalStorage::class.java)

    @BeforeEach
    fun setupMockStorage() {
        Mockito.`when`(storage.getMonsterByName(Mockito.anyString()))
            .thenReturn(null)
    }

    @Test
    fun `Get spell content returns spell`() = runTest {

        val service : ContentService = SpellContentService(storage = storage)



        val spellJsonString = this::class.java.getResource(testSpellFileName).readText()
        val json = Json { ignoreUnknownKeys = true }

        val expected = json.decodeFromString<Spell>(spellJsonString)

        val actual = service.getContentAsync("acid-arrow") as State.Content
        assertEquals(expected, actual.spell)
    }


    @Test
    fun `Get content API Reference returns spell`() = runTest {
        val apiReference = APIReference(
            index = "acid-arrow",
            name = "Acid Arrow",
            url = "/api/spells/acid-arrow"
        )

        val service : ContentService = SpellContentService(storage = storage)

        val spellJsonText = this::class.java.getResource(testSpellFileName).readText()

        val json = Json { ignoreUnknownKeys = true }

        val expected = json.decodeFromString<Spell>(spellJsonText)

        val result = service.getContentAsync(apiReference)

        val content = result as State.Content
        assertEquals(expected, content.spell)
    }

    @Test
    fun `Should throw connection error on client send throw`() = runTest {
        val mockClient = Mockito.mock(HttpClient::class.java)

        Mockito.`when`(
            mockClient.send(
                Mockito.any(HttpRequest::class.java),
                Mockito.eq(HttpResponse.BodyHandlers.ofString())
            )
        )
            .thenThrow(InterruptedException("foo"))

        val service = SpellContentService(client = mockClient, storage = storage)

        assertThrows<ContentServiceException.ConnectionException> {
            service.getContentAsync("foo")
        }
    }
    @Test
    fun `Get content by name should return serialization exception`() = runTest {
        val mockClient = Mockito.mock(HttpClient::class.java)

        val mockResponse = Mockito.mock(HttpResponse::class.java)

        Mockito.`when`(mockResponse.statusCode()).thenReturn(200)
        Mockito.`when`(mockResponse.body()).thenReturn("{\"error\":\"Not found")

        Mockito.`when`(
            mockClient.send(
                Mockito.any(HttpRequest::class.java),
                Mockito.eq(HttpResponse.BodyHandlers.ofString())
            )
        )
            .thenReturn(mockResponse as HttpResponse<String>?)

        val service = SpellContentService(mockClient, storage = storage)

        assertThrows<ContentServiceException.SerializationException> {
            service.getContentAsync("foo")
        }
    }

    @Test
    fun `Get by name should return content not found exception`() = runTest {
        val mockClient = Mockito.mock(HttpClient::class.java)

        val mockResponse = Mockito.mock(HttpResponse::class.java)

        Mockito.`when`(mockResponse.statusCode()).thenReturn(404)

        Mockito.`when`(
            mockClient.send(
                Mockito.any(HttpRequest::class.java),
                Mockito.eq(HttpResponse.BodyHandlers.ofString())
            )
        )
            .thenReturn(mockResponse as HttpResponse<String>?)

        val service = SpellContentService(mockClient, storage = storage)

        assertThrows<ContentServiceException.ContentNotFoundException> {
            service.getContentAsync("foo")
        }
    }

    @Test
    fun `Get by reference should return content not found exception`() = runTest {
        val mockClient = Mockito.mock(HttpClient::class.java)

        val mockResponse = Mockito.mock(HttpResponse::class.java)

        Mockito.`when`(mockResponse.statusCode()).thenReturn(404)

        Mockito.`when`(
            mockClient.send(
                Mockito.any(HttpRequest::class.java),
                Mockito.eq(HttpResponse.BodyHandlers.ofString())
            )
        )
            .thenReturn(mockResponse as HttpResponse<String>?)

        val service = SpellContentService(mockClient, storage = storage)

        assertThrows<ContentServiceException.ContentNotFoundException> {
            val reference = APIReference(
                index = "foo",
                name = "foo.txt",
                url = "/api/spells/foo"
            )
            service.getContentAsync(reference)
        }
    }

    @Test
    fun `Get by reference throws on null url`() = runTest {
        val mockClient = Mockito.mock(HttpClient::class.java)
        val service = SpellContentService(mockClient, storage = storage)

        assertThrows<ContentServiceException.BadURLException> {
            val reference = APIReference(
                index = "foo",
                name = "foo.txt",
            )
            service.getContentAsync(reference)
        }
    }

    @Test
    fun `Get by reference throws on bad url`() = runTest {
        val mockClient = Mockito.mock(HttpClient::class.java)
        val service = SpellContentService(mockClient, storage = storage)
        assertThrows<ContentServiceException.BadURLException> {
            val reference = APIReference(
                index = "foo",
                name = "foo.txt",
                url = "/bar/barbar/foo"
            )
            service.getContentAsync(reference)
        }
    }
}