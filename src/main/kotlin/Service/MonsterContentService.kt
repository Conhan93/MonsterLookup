package Service

import Model.Base.APIReference
import Model.Monster.Monster
import State.State
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

class MonsterContentService(
    private val client : HttpClient = HttpClient.newHttpClient()
) : ContentService {
    private val API_URL : String = "https://www.dnd5eapi.co/api/monsters/"

    override fun getContent(name : String) : State {
        val request = HttpRequest.newBuilder()
            .uri(URI(API_URL + formatMonsterName(name)))
            .GET()
            .build()

        val response =  try {
            client.send(request, HttpResponse.BodyHandlers.ofString())
        } catch (e : Exception) {
            return State.Error(ConnectionException("Error connecting to server", e))
        }

        val json = Json { ignoreUnknownKeys = true }
        val monster  = State.Content(json.decodeFromString<Monster>(response.body()))

        return monster
    }
    override fun getContent(reference: APIReference): State {

        val url = if (!reference.url.isNullOrEmpty())
            reference.url
        else
            throw IllegalArgumentException("Url is null")

        if (!url.startsWith("/api/monsters/"))
            throw IllegalArgumentException("Malformed URL")

        val request = HttpRequest.newBuilder()
            .uri(URI("https://www.dnd5eapi.co$url"))
            .GET()
            .build()

        val response =  try {
            client.send(request, HttpResponse.BodyHandlers.ofString())
        } catch (e : Exception) {
            return State.Error(ConnectionException("Error connecting to server", e))
        }

        val json = Json { ignoreUnknownKeys = true }
        return State.Content(json.decodeFromString<Monster>(response.body()))
    }
    // Changes the format of the search string to match the format of the api
    private fun formatMonsterName(name : String) : String {
        return name
            .replace(' ', '-')
            .trim()
            .lowercase()
    }
}