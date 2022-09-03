package Service

import Model.Base.APIReference
import Model.Base.Base
import Model.Spell.Spell
import State.State
import Storage.ILocalStorage
import Util.API
import Util.formatSearchName

import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

class SpellContentService(
    private val client : HttpClient = HttpClient.newHttpClient(),
    private val storage : ILocalStorage = ILocalStorage.Companion,
    private val API_URL : String = API.APISpellsPath
) : ContentService, JsonService, HTTPService  {

    override suspend fun getContentAsync(name: String): Base? {

        getSpellFromStorage(name)?.let { return it }

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

    override suspend fun getContentAsync(reference: APIReference): Base? {

        if (reference.name != null)
            getSpellFromStorage(reference.name)?.let { return it }

        val url = if (!reference.url.isNullOrEmpty())
                        reference.url
                   else
            throw ContentServiceException.BadURLException("No URL")

        if (!url.startsWith("/api/spells/"))
            throw ContentServiceException.BadURLException("Bad URL $url")

        val request = HttpRequest.newBuilder()
            .uri(URI("${API.baseURL}$url"))
            .GET()
            .build()

        return try {
            getSpellAsync(request)
        } catch (e : ContentServiceException.ContentNotFoundException) {
            throw ContentServiceException.ContentNotFoundException("${reference.name} not found", e)
        }
    }

    private suspend fun getSpellAsync(request: HttpRequest) : Base {
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
            ?: throw ContentServiceException.SerializationException("Error decoding spell")


        storage.store(spell)

        return spell
    }

    private fun getSpellFromStorage(name: String) : Spell? =
        storage.getSpellByName(name)
}