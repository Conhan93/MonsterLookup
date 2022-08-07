package Service

import Model.Monster.Monster
import Model.Spell.Spell
import State.State
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

class SpellService(
    private val client : HttpClient = HttpClient.newHttpClient()
) {

    private val API_URL = "https://www.dnd5eapi.co/api/spells/"

    fun getContent(name : String) : State<Spell> {
        val request = HttpRequest.newBuilder()
            .uri(URI(API_URL + formatName(name)))
            .GET()
            .build()

        val response =  try {
            client.send(request, HttpResponse.BodyHandlers.ofString())
        } catch (e : Exception) {
            return State.Error(ConnectionException("Error connecting to server", e))
        }

        val json = Json { ignoreUnknownKeys = true }
        return State.Content(json.decodeFromString(response.body()))
    }

    private fun formatName(name : String) : String {
        return name
            .replace(' ', '-')
            .trim()
            .lowercase()
    }
}