package Service

import Model.Base.APIReference
import Model.Spell.Spell
import State.State
import Util.formatSearchName
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

class SpellContentService(
    private val client : HttpClient = HttpClient.newHttpClient()
) : ContentService  {

    private val API_URL = "https://www.dnd5eapi.co/api/spells/"

    override fun getContent(name : String) : State {
        val request = HttpRequest.newBuilder()
            .uri(URI(API_URL + name.formatSearchName()))
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

    override fun getContent(reference: APIReference): State {
        val url = if (!reference.url.isNullOrEmpty())
                        reference.url
                   else
                       throw IllegalArgumentException("Url is null")

        if (!url.startsWith("/api/spells/"))
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
        return State.Content(json.decodeFromString(response.body()))
    }
}