package Service

import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

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
    fun handleRequest(
        request: HttpRequest,
        client: HttpClient = HttpClient.newHttpClient(),
        onFail : ((Exception) -> Unit)? = null,
        onSucceed : ((HttpResponse<String>) -> Unit)? = null
    ) {
        val response =  try {
            client.send(request, HttpResponse.BodyHandlers.ofString())
        } catch (e : Exception) {
            onFail?.let { it(e) }
            return
        }

        onSucceed?.let { it(response) }
    }
}