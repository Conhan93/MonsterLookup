package Service

import Model.Base.APIReference
import Model.Spell.Spell
import State.State
import Storage.ILocalStorage
import Util.formatSearchName

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

class SpellContentService(
    private val client : HttpClient = HttpClient.newHttpClient(),
    private val storage : ILocalStorage = ILocalStorage.Companion
) : ContentService  {

    private val API_URL = "https://www.dnd5eapi.co/api/spells/"

    override fun getContent(name : String) : State {

        getSpellFromStorage(name)?.let { return State.Content(spell = it) }

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
            val spell = json.decodeFromString<Spell>(response.body())
            storage.store(spell)
            return State.Content(spell = spell)
        } catch (e : Exception) {
            throw ContentServiceException.SerializationException("Error decoding spell", e)
        }
    }

    override fun getContent(reference: APIReference): State {

        if (reference.name != null)
            getSpellFromStorage(reference.name)?.let { return State.Content(spell = it) }

        val url = if (!reference.url.isNullOrEmpty())
                        reference.url
                   else
            throw ContentServiceException.BadURLException("No URL")

        if (!url.startsWith("/api/spells/"))
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
            val spell = json.decodeFromString<Spell>(response.body())
            storage.store(spell)
            return State.Content(spell = spell)
        } catch (e : Exception) {
            throw ContentServiceException.SerializationException("Error decoding spell", e)
        }
    }

    private fun getSpellFromStorage(name: String) : Spell? =
        storage.getSpellByName(name)
}