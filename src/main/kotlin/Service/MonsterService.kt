package Service

import Model.Monster.Monster
import State.State
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

class MonsterService(
    private val client : HttpClient = HttpClient.newHttpClient()
) {
    private val API_URL : String = "https://www.dnd5eapi.co/api/monsters/"

    fun getMonster(monsterName : String) : State<Monster> {
        val request = HttpRequest.newBuilder()
            .uri(URI(API_URL + formatMonsterName(monsterName)))
            .GET()
            .build()

        val response = client.send(request, HttpResponse.BodyHandlers.ofString())

        val monster  = State.Content(Json.decodeFromString<Monster>(response.body()))

        return monster
    }

    // Changes the format of the search string to match the format of the api
    private fun formatMonsterName(name : String) : String {
        return name
            .replace(' ', '-')
            .trim()
            .lowercase()
    }
}