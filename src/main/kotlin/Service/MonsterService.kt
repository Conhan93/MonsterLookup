package Service

import Model.Monster.Monster
import androidx.compose.runtime.mutableStateOf
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

class MonsterService {

    var monster = mutableStateOf(Monster())

    private val API_URL = "https://www.dnd5eapi.co/api/monsters/"

    private val client = HttpClient.newHttpClient()

    fun getMonster(monsterName : String) : Monster {
        val request = HttpRequest.newBuilder()
            .uri(URI(API_URL + monsterName))
            .GET()
            .build()

        val response = client.send(request, HttpResponse.BodyHandlers.ofString())

        val monster = Json.decodeFromString<Monster>(response.body())

        return monster
    }
}