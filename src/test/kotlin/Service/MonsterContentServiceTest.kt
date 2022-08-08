package Service

import Model.Base.APIReference
import Model.Monster.Monster
import State.State
import kotlinx.serialization.json.Json
import kotlinx.serialization.decodeFromString


import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

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
    fun `Should return state with connection error on client send throw`() {
        val mockClient = mock(HttpClient::class.java)

        `when`(mockClient.send(Mockito.any(HttpRequest::class.java),Mockito.eq(HttpResponse.BodyHandlers.ofString())))
            .thenThrow(InterruptedException("foo"))

        val service = MonsterContentService(client = mockClient)

        val state = service.getContent("adult-black-dragon")

        assertTrue(state is State.Error && state.error.message.equals("Error connecting to server"))

    }
}