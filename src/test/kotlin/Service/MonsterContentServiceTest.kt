package Service

import Model.Base.APIReference
import Model.Monster.Monster
import State.State
import kotlinx.serialization.json.Json
import kotlinx.serialization.decodeFromString


import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.mockito.kotlin.any

import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

internal class MonsterContentServiceTest {

    val adultBlackDragonFileName = "/adultblackdragon.txt"

    @Test
    fun `get monster returns monster`() {

        val service : ContentService = MonsterContentService()

        val actual = service.getContent("adult-black-dragon") as State.Content

        val dragonJsonText = this::class.java.getResource(adultBlackDragonFileName).readText()

        val json = Json { ignoreUnknownKeys = true }

        val expected = json.decodeFromString<Monster>(dragonJsonText)

        kotlin.test.assertEquals(expected, actual.monster)
    }

    @Test
    fun `Get content API Reference returns monster`() {

        val apiReference = APIReference(
            index = "adult-black-dragon",
            name = "Adult Black Dragon",
            url = "/api/monsters/adult-black-dragon"
        )

        val service : ContentService = MonsterContentService()

        val dragonJsonText = this::class.java.getResource(adultBlackDragonFileName).readText()

        val json = Json { ignoreUnknownKeys = true }

        val expected = json.decodeFromString<Monster>(dragonJsonText)

        val actual = service.getContent(apiReference) as State.Content

        assertEquals(expected, actual.monster)
    }

    @Test
    fun `Should throw connection error on client send throw`() {
        val mockClient = mock(HttpClient::class.java)

        `when`(mockClient.send(Mockito.any(HttpRequest::class.java),Mockito.eq(HttpResponse.BodyHandlers.ofString())))
            .thenThrow(InterruptedException("foo"))

        val service = MonsterContentService(client = mockClient)

        assertThrows<ContentServiceException.ConnectionException> {
            service.getContent("adult-black-dragon")
        }
    }

    @Test
    fun `Get content by name should return serialization exception`() {
        val mockClient = mock(HttpClient::class.java)

        val mockResponse = mock(HttpResponse::class.java)

        `when`(mockResponse.statusCode()).thenReturn(200)
        `when`(mockResponse.body()).thenReturn("{\"error\":\"Not found")

        `when`(mockClient.send(Mockito.any(HttpRequest::class.java), Mockito.eq(HttpResponse.BodyHandlers.ofString())))
            .thenReturn(mockResponse as HttpResponse<String>?)

        val service = MonsterContentService(mockClient)

        assertThrows<ContentServiceException.SerializationException> {
            service.getContent("foo")
        }
    }

    @Test
    fun `Get by name should return content not found exception`() {
        val mockClient = mock(HttpClient::class.java)

        val mockResponse = mock(HttpResponse::class.java)

        `when`(mockResponse.statusCode()).thenReturn(404)

        `when`(mockClient.send(Mockito.any(HttpRequest::class.java), Mockito.eq(HttpResponse.BodyHandlers.ofString())))
            .thenReturn(mockResponse as HttpResponse<String>?)

        val service = MonsterContentService(mockClient)

        assertThrows<ContentServiceException.ContentNotFoundException> {
            service.getContent("foo")
        }
    }
    @Test
    fun `Get by reference should return content not found exception`() {
        val mockClient = mock(HttpClient::class.java)

        val mockResponse = mock(HttpResponse::class.java)

        `when`(mockResponse.statusCode()).thenReturn(404)

        `when`(mockClient.send(Mockito.any(HttpRequest::class.java), Mockito.eq(HttpResponse.BodyHandlers.ofString())))
            .thenReturn(mockResponse as HttpResponse<String>?)

        val service = MonsterContentService(mockClient)

        assertThrows<ContentServiceException.ContentNotFoundException> {
            val reference = APIReference(
                index = "foo",
                name = "foo.txt",
                url = "/api/monsters/foo"
            )
            service.getContent(reference)
        }
    }
}