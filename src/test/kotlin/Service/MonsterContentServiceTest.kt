package Service

import Model.Base.APIReference
import Model.Monster.Monster
import State.State
import Storage.ILocalStorage
import kotlinx.coroutines.*
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import kotlinx.serialization.decodeFromString


import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

@OptIn(ExperimentalCoroutinesApi::class)
internal class MonsterContentServiceTest {

    val adultBlackDragonFileName = "/adultblackdragon.txt"

    val storage = mock(ILocalStorage::class.java)

    @BeforeEach
    fun setupMockStorage() {
        `when`(storage.getMonsterByName(Mockito.anyString()))
            .thenReturn(null)
    }

    @Test
    fun `get monster returns monster`() = runTest {

        val service : ContentService = MonsterContentService(storage = storage)



        val dragonJsonText = this::class.java.getResource(adultBlackDragonFileName).readText()

        val json = Json { ignoreUnknownKeys = true }

        val expected = json.decodeFromString<Monster>(dragonJsonText)

        val actual = service.getContentAsync("adult-black-dragon")
        assertEquals(expected, actual)

    }

    @Test
    fun `Get content API Reference returns monster`() = runTest {

        val apiReference = APIReference(
            index = "adult-black-dragon",
            name = "Adult Black Dragon",
            url = "/api/monsters/adult-black-dragon"
        )

        val service : ContentService = MonsterContentService(storage = storage)

        val dragonJsonText = this::class.java.getResource(adultBlackDragonFileName).readText()

        val json = Json { ignoreUnknownKeys = true }

        val expected = json.decodeFromString<Monster>(dragonJsonText)
        val actual = service.getContentAsync(apiReference)

        assertEquals(expected, actual)
    }

    @Test
    fun `Should throw connection error on client send throw`() = runTest {
        val mockClient = mock(HttpClient::class.java)

        `when`(mockClient.send(Mockito.any(HttpRequest::class.java),Mockito.eq(HttpResponse.BodyHandlers.ofString())))
            .thenThrow(InterruptedException("foo"))

        val service = MonsterContentService(client = mockClient, storage = storage)

        assertThrows<ContentServiceException.ConnectionException> {
            service.getContentAsync("adult-black-dragon")
        }
    }

    @Test
    fun `Get content by name should return serialization exception`() = runTest {
        val mockClient = mock(HttpClient::class.java)

        val mockResponse = mock(HttpResponse::class.java)

        `when`(mockResponse.statusCode()).thenReturn(200)
        `when`(mockResponse.body()).thenReturn("{\"error\":\"Not found")

        `when`(mockClient.send(Mockito.any(HttpRequest::class.java), Mockito.eq(HttpResponse.BodyHandlers.ofString())))
            .thenReturn(mockResponse as HttpResponse<String>?)

        val service = MonsterContentService(mockClient, storage = storage)

        assertThrows<ContentServiceException.SerializationException> {
            service.getContentAsync("foo")
        }
    }

    @Test
    fun `Get by name should return content not found exception`() = runTest {
        val mockClient = mock(HttpClient::class.java)

        val mockResponse = mock(HttpResponse::class.java)

        `when`(mockResponse.statusCode()).thenReturn(404)

        `when`(mockClient.send(Mockito.any(HttpRequest::class.java), Mockito.eq(HttpResponse.BodyHandlers.ofString())))
            .thenReturn(mockResponse as HttpResponse<String>?)

        val service = MonsterContentService(mockClient, storage = storage)

        assertThrows<ContentServiceException.ContentNotFoundException> {
            service.getContentAsync("foo")
        }
    }
    @Test
    fun `Get by reference should return content not found exception`() = runTest {
        val mockClient = mock(HttpClient::class.java)

        val mockResponse = mock(HttpResponse::class.java)

        `when`(mockResponse.statusCode()).thenReturn(404)

        `when`(mockClient.send(Mockito.any(HttpRequest::class.java), Mockito.eq(HttpResponse.BodyHandlers.ofString())))
            .thenReturn(mockResponse as HttpResponse<String>?)

        val service = MonsterContentService(mockClient, storage = storage)
        assertThrows<ContentServiceException.ContentNotFoundException> {
            val reference = APIReference(
                index = "foo",
                name = "foo.txt",
                url = "/api/monsters/foo"
            )
            service.getContentAsync(reference)
        }
    }

    @Test
    fun `Get by reference throws on null url`() = runTest {
        val mockClient = mock(HttpClient::class.java)
        val service = MonsterContentService(mockClient, storage = storage)
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
        val mockClient = mock(HttpClient::class.java)
        val service = MonsterContentService(mockClient, storage = storage)
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