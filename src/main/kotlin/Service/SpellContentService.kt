package Service

import Model.Base.APIReference
import Model.Spell.Spell
import State.State
import Storage.ILocalStorage
import Util.formatSearchName

import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

class SpellContentService(
    private val client : HttpClient = HttpClient.newHttpClient(),
    private val storage : ILocalStorage = ILocalStorage.Companion
) : ContentService, JsonService, HTTPService  {

    private val API_URL = "https://www.dnd5eapi.co/api/spells/"

    override suspend fun getContentAsync(name : String) : State {

        getSpellFromStorage(name)?.let { return State.Content(spell = it) }

        val request = HttpRequest.newBuilder()
            .uri(URI(API_URL + name.formatSearchName()))
            .GET()
            .build()
        return try {
            getSpellAsync(request)
        } catch (e : ContentServiceException.ContentNotFoundException) {
            throw ContentServiceException.ContentNotFoundException("$name not found", e)
        }
    }

    override suspend fun getContentAsync(reference: APIReference): State {

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

        return try {
            getSpellAsync(request)
        } catch (e : ContentServiceException.ContentNotFoundException) {
            throw ContentServiceException.ContentNotFoundException("${reference.name} not found", e)
        }
    }

    private suspend fun getSpellAsync(request: HttpRequest) : State {
        var response : HttpResponse<String>? = null

        handleRequestAsync(
            request = request,
            client = client,
            onFail = { throw ContentServiceException.ConnectionException("Unable to send message", it) }
        ) {
            if (it.statusCode().equals(404))
                throw ContentServiceException.ContentNotFoundException("Content Not Found")

            response = it

        }

        val spell = decodeFromString<Spell>(response!!.body())

        spell?.let {
            storage.store(it)
            return State.Content(spell = it)
        }

        throw ContentServiceException.SerializationException("Error decoding spell")
    }

    private fun getSpellFromStorage(name: String) : Spell? =
        storage.getSpellByName(name)
}