package Model.Service

import Model.Data.Base.APIReference
import Util.API
import Util.formatSearchName

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

import kotlin.reflect.KClass

/**
 * Implements http functionality for content services
 */
interface HTTPService {

    /**
     * @param request the request to be sent
     * @param client to send the request, will create new client
     * if none is provided
     * @param onFail callback to called on exception throw by client
     * send method, with error passed to callback. Can be left null
     * @param onSucceed callback to be called on send success, response
     * will be passed to callback. Can be left null
     */
    suspend fun handleRequestAsync(
        request: HttpRequest,
        client: HttpClient = HttpClient.newHttpClient(),
        onFail : ((Exception) -> Unit)? = null,
        onSucceed : ((HttpResponse<String>) -> Unit)? = null
    ) {
        val response =  try {
            withContext(Dispatchers.IO) {
                client.send(request, HttpResponse.BodyHandlers.ofString())
            }
        } catch (e : Exception) {
            onFail?.let { it(e) }
            return
        }

        onSucceed?.let { it(response) }
    }

    /**
     * Uses the name and the class provided to build a http request
     * to the api
     * @param name of the target
     * @param klass the class of the target
     *
     * @return http request to the API for the target
     */
    fun buildRequest(name: String, klass : KClass<*>) : HttpRequest {
        return HttpRequest
            .newBuilder()
            .uri(URI(API.paths[klass] + name.formatSearchName()))
            .build()
    }

    /**
     * Uses the url in the reference and the class provided to build
     * a http request to the api
     *
     * @param reference containing the url to the target
     * @param klass the class of the target
     *
     * @return http request to the API for the target
     */
    fun buildRequest(reference : APIReference, klass: KClass<*>) : HttpRequest {
        val url = if (!reference.url.isNullOrEmpty())
            reference.url
        else
            throw IllegalArgumentException("No Url")

        if (!url.startsWith((API.paths[klass] ?: "").removePrefix(API.baseURL)))
            throw IllegalArgumentException("Bad URL $url")

        return HttpRequest.newBuilder()
            .uri(URI("${API.baseURL}$url"))
            .GET()
            .build()
    }
}



