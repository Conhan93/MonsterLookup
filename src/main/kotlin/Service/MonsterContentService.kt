package Service

import Model.Base.APIReference
import Model.Monster.Monster
import State.State
import Storage.ILocalStorage
import Util.API
import Util.formatSearchName

import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

class MonsterContentService(
    private val client : HttpClient = HttpClient.newHttpClient(),
    private val storage : ILocalStorage = ILocalStorage.Companion,
    private val API_URL : String = API.APIMonsterPath
) : ContentService, JsonService, HTTPService {


    override suspend fun getContentAsync(name : String) : State {

        getMonsterFromStorage(name)?.let { return State.Content(monster = it) }

        val request = HttpRequest.newBuilder()
            .uri(URI(API_URL + name.formatSearchName()))
            .GET()
            .build()

        return try {
            getMonsterAsync(request)
        } catch (e : ContentServiceException.ContentNotFoundException) {
            throw ContentServiceException.ContentNotFoundException("$name not found", e)
        }
    }
    override suspend fun getContentAsync(reference: APIReference): State {
        if(reference.name != null)
            getMonsterFromStorage(reference.name)?.let { return State.Content(monster = it) }

        val url = if (!reference.url.isNullOrEmpty())
            reference.url
        else
            throw ContentServiceException.BadURLException("No URL")

        if (!url.startsWith("/api/monsters/"))
            throw ContentServiceException.BadURLException("Bad URL $url")

        val request = HttpRequest.newBuilder()
            .uri(URI("${API.baseURL}$url"))
            .GET()
            .build()

        return try {
            getMonsterAsync(request)
        } catch (e : ContentServiceException.ContentNotFoundException) {
            throw ContentServiceException.ContentNotFoundException("$reference.name not found", e)
        }
    }

    private suspend fun getMonsterAsync(request: HttpRequest) : State {
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

        val monster = decodeFromString<Monster>(response!!.body())

        monster?.let {
            storage.store(it)
            return State.Content(monster = it)
        }

        throw ContentServiceException.SerializationException("Error decoding monster")
    }

    private fun getMonsterFromStorage(name: String) : Monster? =
        storage.getMonsterByName(name)
}