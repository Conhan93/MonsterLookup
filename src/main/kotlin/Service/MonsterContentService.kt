package Service

import Model.Base.APIReference
import Model.Monster.Monster
import State.State
import Storage.ILocalStorage
import Util.formatSearchName

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

class MonsterContentService(
    private val client : HttpClient = HttpClient.newHttpClient(),
    private val storage : ILocalStorage = ILocalStorage.Companion
) : ContentService {
    private val API_URL : String = "https://www.dnd5eapi.co/api/monsters/"

    override fun getContent(name : String) : State {

        getMonsterFromStorage(name)?.let { return State.Content(monster = it) }

        val request = HttpRequest.newBuilder()
            .uri(URI(API_URL + name.formatSearchName()))
            .GET()
            .build()

        val response =  try {
            client.send(request, HttpResponse.BodyHandlers.ofString())
        } catch (e : Exception) {
            throw ContentServiceException.ConnectionException("Unable to send message", e)
        }

        if(response.statusCode().equals(404))
            throw ContentServiceException.ContentNotFoundException("$name not found")

        val json = Json { ignoreUnknownKeys = true }

        try {
            val monster = json.decodeFromString<Monster>(response.body())
            storage.store(monster)
            return State.Content(json.decodeFromString<Monster>(response.body()))
        } catch (e : Exception) {
            throw ContentServiceException.SerializationException("Error decoding monster", e)
        }
    }
    override fun getContent(reference: APIReference): State {
        if(reference.name != null)
            getMonsterFromStorage(reference.name)?.let { return State.Content(monster = it) }

        val url = if (!reference.url.isNullOrEmpty())
            reference.url
        else
            throw ContentServiceException.BadURLException("No URL")

        if (!url.startsWith("/api/monsters/"))
            throw ContentServiceException.BadURLException("Bad URL $url")

        val request = HttpRequest.newBuilder()
            .uri(URI("https://www.dnd5eapi.co$url"))
            .GET()
            .build()

        val response =  try {
            client.send(request, HttpResponse.BodyHandlers.ofString())
        } catch (e : Exception) {
            throw ContentServiceException.ConnectionException("Unable to send message", e)
        }

        if(response.statusCode().equals(404))
            throw ContentServiceException.ContentNotFoundException("${reference.name} not found")

        val json = Json { ignoreUnknownKeys = true }

        try {
            val monster = json.decodeFromString<Monster>(response.body())
            storage.store(monster)
            return State.Content(json.decodeFromString<Monster>(response.body()))
        } catch (e : Exception) {
            throw ContentServiceException.SerializationException("Error decoding monster", e)
        }
    }

    private fun getMonsterFromStorage(name: String) : Monster? =
        storage.getMonsterByName(name)
}