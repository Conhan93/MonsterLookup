package Service

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

internal class MonsterServiceTest {

    val adultBlackDragonFileName = "/adultblackdragon.txt"

    @Test
    fun `get monster returns monster`() {

        val monsterService = MonsterService()

        val monsterActual = monsterService.getMonster("adult-black-dragon") as State.Content

        val dragonJsonText = this::class.java.getResource(adultBlackDragonFileName).readText()

        val json = Json { ignoreUnknownKeys = true }

        val Expected = json.decodeFromString<Monster>(dragonJsonText)

        kotlin.test.assertEquals(Expected, monsterActual.data)
    }

    @Test
    fun `Should return state with connection error on client send throw`() {
        val mockClient = mock(HttpClient::class.java)

        `when`(mockClient.send(Mockito.any(HttpRequest::class.java),Mockito.eq(HttpResponse.BodyHandlers.ofString())))
            .thenThrow(InterruptedException("foo"))

        val service = MonsterService(client = mockClient)

        val state = service.getMonster("adult-black-dragon")

        assertTrue(state is State.Error && state.error.message.equals("Error connecting to server"))

    }
}